package com.alice.pet.base.model;

import com.alice.pet.base.domain.RoleMenu;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 角色与菜单对应关系
 */
@Data
@NoArgsConstructor
public class RoleMenuModel implements Serializable {

    /**
     * 
     */
    private Long id;
    /**
     * 角色编号
     */
    private String roleId;
    /**
     * 菜单编号
     */
    private String menuId;

    public RoleMenuModel(RoleMenu domain) {
        this.id = domain.getId();
        this.roleId = domain.getRoleId();
        this.menuId = domain.getMenuId();
    }

    public static RoleMenu convertDomain(RoleMenuModel model) {
        RoleMenu domain = new RoleMenu();
        domain.setId(model.getId());
        domain.setRoleId(model.getRoleId());
        domain.setMenuId(model.getMenuId());
        return domain;
    }

}