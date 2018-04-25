package com.sukidesu.seckill.base.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 用户表
 */
@Data
@NoArgsConstructor
public class User implements Serializable {

    /**
     * 自增id
     */
    private Long id;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 姓名
     */
    private String name;
    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    private String password;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 电话号码
     */
    private String userPhone;
    /**
     * 地址
     */
    private String address;
    /**
     * 状态 0:禁用，1:正常
     */
    private Integer state;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    private Set<String> roleIds;

    private Set<String> roleCodes;

    private List<Role> roles;

    public User(String account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", address='" + address + '\'' +
                ", state=" + state +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", roleIds=" + roleIds +
                ", roleCodes=" + roleCodes +
                ", roles=" + roles +
                '}';
    }
}