package com.alice.pet.base.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 角色与菜单对应关系
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleMenu implements Serializable {

    private Long id;
    /**
     * 角色编号
     */
    private String roleId;
    /**
     * 菜单编号
     */
    private String menuId;

    public RoleMenu(String roleId, String menuId) {
        this.roleId = roleId;
        this.menuId = menuId;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + this.id + "," +
                "\"roleId\":" + this.roleId + "," +
                "\"menuId\":" + this.menuId + "," +
                "}";
    }

}