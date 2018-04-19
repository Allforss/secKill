package com.alice.pet.base.model;

import com.alice.pet.base.domain.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色表
 */
@Data
@NoArgsConstructor
public class RoleModel implements Serializable {

    /**
     * 
     */
    private Long id;
    /**
     * 编号
     */
    private String roleId;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 数据值
     */
    private String code;
    /**
     * 描述
     */
    private String description;
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
     * 更新时间
     */
    private Date updateTime;
    /**
     * 删除标记 0:未删除，1:已删除
     */
    private Integer delFlag;

    public RoleModel(Role domain) {
        this.id = domain.getId();
        this.roleId = domain.getRoleId();
        this.name = domain.getName();
        this.code = domain.getCode();
        this.description = domain.getDescription();
        this.createBy = domain.getCreateBy();
        this.updateBy = domain.getUpdateBy();
        this.createTime = domain.getCreateTime();
        this.updateTime = domain.getUpdateTime();
        this.delFlag = domain.getDelFlag();
    }

    public static Role convertDomain(RoleModel model) {
        Role domain = new Role();
        domain.setId(model.getId());
        domain.setRoleId(model.getRoleId());
        domain.setName(model.getName());
        domain.setCode(model.getCode());
        domain.setDescription(model.getDescription());
        domain.setCreateBy(model.getCreateBy());
        domain.setUpdateBy(model.getUpdateBy());
        domain.setCreateTime(model.getCreateTime());
        domain.setUpdateTime(model.getUpdateTime());
        domain.setDelFlag(model.getDelFlag());
        return domain;
    }

}