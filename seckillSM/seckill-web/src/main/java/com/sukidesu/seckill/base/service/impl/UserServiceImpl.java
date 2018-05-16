package com.sukidesu.seckill.base.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sukidesu.common.common.Constants.Constants;
import com.sukidesu.seckill.base.common.constants.RedisConstants;
import com.sukidesu.seckill.base.common.errorenums.SystemErrorEnum;
import com.sukidesu.seckill.base.common.exception.BizException;
import com.sukidesu.seckill.base.domain.Role;
import com.sukidesu.seckill.base.domain.User;
import com.sukidesu.seckill.base.domain.UserRole;
import com.sukidesu.seckill.base.enums.UserStateEnum;
import com.sukidesu.seckill.base.mapper.UserMapper;
import com.sukidesu.seckill.base.mapper.UserRoleMapper;
import com.sukidesu.seckill.base.service.RoleService;
import com.sukidesu.seckill.base.service.UserService;
import com.sukidesu.seckill.base.shiro.AuthcToken;
import com.sukidesu.seckill.base.shiro.ShiroUtil;
import com.sukidesu.seckill.base.utils.IdGenerator;
import com.sukidesu.seckill.base.utils.PasswordUtil;
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
import java.util.Date;
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
            String pwd = PasswordUtil.createUserPwd(new String(token.getPassword()));
            if(!Constants.TRAVELER.equals(loginUser.getAccount())){
                if(!pwd.equals(loginUser.getPassword())){
                    throw new IncorrectCredentialsException();
                }
            }
            loginUser.setRoles(roleService.findByUserId(Long.valueOf(loginUser.getUserId())));
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
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
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
