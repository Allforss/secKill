package com.alice.pet.base.service;

import com.github.pagehelper.Page;
import com.alice.pet.base.domain.Role;

import java.util.List;

/**
 * @author xudong.cheng
 * @date 2018/1/18 下午5:23
 */
public interface RoleService {

    Role get(Role role);

    Role getWithPerms(Role role);

    List<Role> findByUserId(long userId);

    List<Role> findList(Role role);

    Page<Role> findPage(int offset, int limit, String name);

    int insertWithPerms(Role role);

}
