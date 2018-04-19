package com.alice.pet.base.model;

import com.alice.pet.base.domain.Menu;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 菜单管理
 */
@Data
@NoArgsConstructor
public class MenuModel implements Serializable {

    /**
     * 
     */
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

    public MenuModel(Menu domain) {
        this.id = domain.getId();
        this.menuId = domain.getMenuId();
        this.parentId = domain.getParentId();
        this.name = domain.getName();
        this.url = domain.getUrl();
        this.perms = domain.getPerms();
        this.type = domain.getType();
        this.icon = domain.getIcon();
        this.sort = domain.getSort();
        this.createBy = domain.getCreateBy();
        this.updateBy = domain.getUpdateBy();
        this.createTime = domain.getCreateTime();
        this.updateTime = domain.getUpdateTime();
        this.delFlag = domain.getDelFlag();
    }

    public static Menu convertDomain(MenuModel model) {
        Menu domain = new Menu();
        domain.setId(model.getId());
        domain.setMenuId(model.getMenuId());
        domain.setParentId(model.getParentId());
        domain.setName(model.getName());
        domain.setUrl(model.getUrl());
        domain.setPerms(model.getPerms());
        domain.setType(model.getType());
        domain.setIcon(model.getIcon());
        domain.setSort(model.getSort());
        domain.setCreateBy(model.getCreateBy());
        domain.setUpdateBy(model.getUpdateBy());
        domain.setCreateTime(model.getCreateTime());
        domain.setUpdateTime(model.getUpdateTime());
        domain.setDelFlag(model.getDelFlag());
        return domain;
    }

}