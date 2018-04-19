package com.alice.pet.base.model;

import com.alice.pet.base.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 */
@Data
@NoArgsConstructor
public class UserModel implements Serializable {

    /**
     * 自增id
     */
    private Long id;
    /**
     * 编号
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

    public UserModel(User domain) {
        this.id = domain.getId();
        this.userId = domain.getUserId();
        this.name = domain.getName();
        this.account = domain.getAccount();
//        this.password = domain.getPassword();
        this.email = domain.getEmail();
        this.userPhone = domain.getUserPhone();
        this.address = domain.getAddress();
        this.state = domain.getState();
        this.createTime = domain.getCreateTime();
        this.updateTime = domain.getUpdateTime();
    }

    public static User convertDomain(UserModel model) {
        User domain = new User();
        domain.setId(model.getId());
        domain.setUserId(model.getUserId());
        domain.setName(model.getName());
        domain.setAccount(model.getAccount());
        domain.setPassword(model.getPassword());
        domain.setEmail(model.getEmail());
        domain.setUserPhone(model.getUserPhone());
        domain.setAddress(model.getAddress());
        domain.setState(model.getState());
        domain.setCreateTime(model.getCreateTime());
        domain.setUpdateTime(model.getUpdateTime());
        return domain;
    }

}