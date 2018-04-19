package com.alice.pet.base.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.alice.pet.base.common.constants.RedisConstants;
import com.alice.pet.base.common.errorenums.SystemErrorEnum;
import com.alice.pet.base.common.exception.BizException;
import com.alice.pet.base.domain.Role;
import com.alice.pet.base.domain.User;
import com.alice.pet.base.domain.UserRole;
import com.alice.pet.base.enums.UserStateEnum;
import com.alice.pet.base.mapper.UserMapper;
import com.alice.pet.base.mapper.UserRoleMapper;
import com.alice.pet.base.service.RoleService;
import com.alice.pet.base.service.UserService;
import com.alice.pet.base.shiro.AuthcToken;
import com.alice.pet.base.shiro.ShiroUtil;
import com.alice.pet.base.utils.IdGenerator;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xudong.cheng
 * @date 2018/1/17 下午1:06
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleService roleService;

    /**
     * 登录
     *
     * @param token 认证信息
     */
    @Override
    public void login(AuthcToken token) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            User loginUser = this.getByAccount(token.getPrincipal().toString());
            loginUser.setRoles(roleService.findByUserId(loginUser.getId()));
            loginUser.setRoleCodes(loginUser.getRoles().stream().map(Role::getCode).filter(code -> !StringUtils.isEmpty(code)).collect(Collectors.toSet()));
            ShiroUtil.updateCurrentUser(loginUser);
            ShiroUtil.setSessionAttr(RedisConstants.CURRENT_USER_TYPE, token.getType());
        } catch (UnknownAccountException e) {
            throw new BizException(SystemErrorEnum.AUTHC_ERROR.code, "该用户不存在");
        } catch (IncorrectCredentialsException e) {
            throw new BizException(SystemErrorEnum.AUTHC_ERROR.code, "密码不正确");
        } catch (LockedAccountException e) {
            throw new BizException(SystemErrorEnum.AUTHC_ERROR.code, "该用户已锁定");
        } catch (DisabledAccountException e) {
            throw new BizException(SystemErrorEnum.AUTHC_ERROR.code, "该用户已禁用");
        } catch (AuthenticationException e) {
            throw new BizException(SystemErrorEnum.AUTHC_ERROR.code, e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addWithRoles(User user) {
        String userId = IdGenerator.id();
        user.setUserId(userId);
        user.setState(UserStateEnum.ENABLE.state);
        userMapper.insert(user);
        if (!CollectionUtils.isEmpty(user.getRoleIds())) {
            List<UserRole> userRoles = new ArrayList<>();
            for (String roleId : user.getRoleIds()) {
                userRoles.add(new UserRole(userId, roleId));
            }
            userRoleMapper.insertList(userRoles);
        }
        return userId;
    }

    @Override
    public User getByAccount(String account) {
        List<User> users = userMapper.select(new User(account));
        if (CollectionUtils.isEmpty(users)) {
            return null;
        }
        return users.get(0);
    }

    @Override
    public User getBasicForLogin(String account) {
        List<User> users = userMapper.select(new User(account));
        if (CollectionUtils.isEmpty(users)) {
            return null;
        }
        return users.get(0);
    }

    @Override
    public List<User> findList(User user) {
        List<User> users = userMapper.select(user);
        if (CollectionUtils.isEmpty(users)) {
            return Collections.emptyList();
        }
        return users;
    }

    @Override
    public Page<User> findPage(int offset, int limit, String name) {
        User user = new User();
        user.setName(name);
        return PageHelper.offsetPage(offset, limit)
                .setOrderBy("FstrUpdateTime DESC")
                .doSelectPage(() -> userMapper.select(user));
    }


}
