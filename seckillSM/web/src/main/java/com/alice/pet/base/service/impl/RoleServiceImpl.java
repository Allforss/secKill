package com.alice.pet.base.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.alice.pet.base.common.errorenums.SystemErrorEnum;
import com.alice.pet.base.common.exception.BizException;
import com.alice.pet.base.domain.Role;
import com.alice.pet.base.domain.RoleMenu;
import com.alice.pet.base.mapper.RoleMapper;
import com.alice.pet.base.mapper.RoleMenuMapper;
import com.alice.pet.base.service.RoleService;
import com.alice.pet.base.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xudong.cheng
 * @date 2018/1/18 下午5:25
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public Role get(Role role) {
        List<Role> roles = roleMapper.select(role);
        if (CollectionUtils.isEmpty(roles)) {
            return null;
        }
        if (roles.size() > 1) {
            throw new BizException(SystemErrorEnum.SYSTEM_ERROR.code, SystemErrorEnum.SYSTEM_ERROR.desc);
        }
        return roles.get(0);
    }

    @Override
    public Role getWithPerms(Role role) {
        List<Role> roles = roleMapper.select(role);
        if (CollectionUtils.isEmpty(roles)) {
            return null;
        }
        if (roles.size() > 1) {
            throw new BizException(SystemErrorEnum.SYSTEM_ERROR.code, SystemErrorEnum.SYSTEM_ERROR.desc);
        }
        role = roles.get(0);
        List<RoleMenu> roleMenuList = roleMenuMapper.select(RoleMenu.builder().roleId(role.getRoleId()).build());
        if (!CollectionUtils.isEmpty(roles)) {
            role.setMenuIds(roleMenuList.stream().map(RoleMenu::getMenuId).collect(Collectors.toList()));
        }
        return role;
    }

    @Override
    public List<Role> findByUserId(long userId) {
        List<Role> roles = roleMapper.findByUserId(userId);
        if (CollectionUtils.isEmpty(roles)) {
            return Collections.emptyList();
        }
        return roles;
    }

    @Override
    public List<Role> findList(Role role) {
        List<Role> roles = roleMapper.select(role);
        if (CollectionUtils.isEmpty(roles)) {
            return Collections.emptyList();
        }
        return roles;
    }

    @Override
    public Page<Role> findPage(int offset, int limit, String name) {
        Role role = new Role();
        role.setName(name);
        return PageHelper.offsetPage(offset, limit)
                .setOrderBy("FstrUpdateTime DESC")
                .doSelectPage(() -> roleMapper.select(role));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertWithPerms(Role role) {
        String roleId = IdGenerator.id();
        role.setRoleId(roleId);
        int i = roleMapper.insert(role);
        if (!CollectionUtils.isEmpty(role.getMenuIds())) {
            roleMenuMapper.insertList(role.getMenuIds().stream().map(menuId -> new RoleMenu(roleId, menuId)).collect(Collectors.toList()));
        }
        return i;
    }
}
