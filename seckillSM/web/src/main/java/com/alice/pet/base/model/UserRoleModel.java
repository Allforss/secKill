package com.alice.pet.base.model;

import com.alice.pet.base.domain.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户与角色对应关系
 */
@Data
@NoArgsConstructor
public class UserRoleModel implements Serializable {

    /**
     * 
     */
    private Long id;
    /**
     * 用户编号
     */
    private String userId;
    /**
     * 角色编号
     */
    private String roleId;

    public UserRoleModel(UserRole domain) {
        this.id = domain.getId();
        this.userId = domain.getUserId();
        this.roleId = domain.getRoleId();
    }

    public static UserRole convertDomain(UserRoleModel model) {
        UserRole domain = new UserRole();
        domain.setId(model.getId());
        domain.setUserId(model.getUserId());
        domain.setRoleId(model.getRoleId());
        return domain;
    }

}