package com.alice.pet.base.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 菜单管理
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Menu implements Serializable {

    private Long id;
    /**
     * 编号
     */
    private String menuId;
    /**
     * 父菜单ID，一级菜单为0
     */
    private String parentId;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单URL
     */
    private String url;
    /**
     * 授权(多个用逗号分隔，如：user:list,user:create)
     */
    private String perms;
    /**
     * 类型   0：目录   1：菜单   2：按钮
     */
    private Integer type;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 删除标记 0:未删除，1:已删除
     */
    private Integer delFlag;

    private Menu parent;

    private List<Menu> sub;

    public Menu(String menuId) {
        this.menuId = menuId;
    }

    public static final Menu ROOT = Menu.builder().menuId("0").id(0L).name("根节点").build();

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + this.id + "," +
                "\"menuId\":" + this.menuId + "," +
                "\"parentId\":" + this.parentId + "," +
                "\"name\":" + this.name + "," +
                "\"url\":" + this.url + "," +
                "\"perms\":" + this.perms + "," +
                "\"type\":" + this.type + "," +
                "\"icon\":" + this.icon + "," +
                "\"sort\":" + this.sort + "," +
                "\"createBy\":" + this.createBy + "," +
                "\"updateBy\":" + this.updateBy + "," +
                "\"createTime\":" + this.createTime + "," +
                "\"updateTime\":" + this.updateTime + "," +
                "\"delFlag\":" + this.delFlag + "," +
                "}";
    }

}