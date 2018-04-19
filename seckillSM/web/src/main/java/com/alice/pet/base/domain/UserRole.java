package com.alice.pet.base.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户与角色对应关系
 */
@Data
@NoArgsConstructor
public class UserRole implements Serializable {

    private Long id;
    /**
     * 用户编号
     */
    private String userId;
    /**
     * 角色编号
     */
    private String roleId;

    public UserRole(String userId, String roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + this.id + "," +
                "\"userId\":" + this.userId + "," +
                "\"roleId\":" + this.roleId + "," +
                "}";
    }

}