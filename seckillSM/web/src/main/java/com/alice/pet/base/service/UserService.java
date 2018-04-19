package com.alice.pet.base.service;

import com.github.pagehelper.Page;
import com.alice.pet.base.domain.User;
import com.alice.pet.base.shiro.AuthcToken;

import java.util.List;

/**
 * @author xudong.cheng
 * @date 2018/1/17 下午1:05
 */
public interface UserService {

    /**
     * 登录
     *
     * @param token 认证信息
     */
    void login(AuthcToken token);

    String addWithRoles(User user);

    User getByAccount(String account);

    User getBasicForLogin(String account);

    List<User> findList(User user);

    Page<User> findPage(int offset, int limit, String name);
}
