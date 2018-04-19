package com.alice.pet.base.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 角色表
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable {

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

    private List<String> menuIds;

    private List<Menu> menus;

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + this.id + "," +
                "\"roleId\":" + this.roleId + "," +
                "\"name\":" + this.name + "," +
                "\"code\":" + this.code + "," +
                "\"description\":" + this.description + "," +
                "\"createBy\":" + this.createBy + "," +
                "\"updateBy\":" + this.updateBy + "," +
                "\"createTime\":" + this.createTime + "," +
                "\"updateTime\":" + this.updateTime + "," +
                "\"delFlag\":" + this.delFlag + "," +
                "}";
    }

}