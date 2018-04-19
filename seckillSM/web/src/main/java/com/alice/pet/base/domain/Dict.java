package com.alice.pet.base.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 字典表
 */
@Data
@NoArgsConstructor
public class Dict implements Serializable {

    /**
     * 编号
     */
    private Long id;
    /**
     * 类型
     */
    private String type;
    /**
     * 标签名
     */
    private String name;
    /**
     * 数据值
     */
    private String value;
    /**
     * 描述
     */
    private String description;
    /**
     * 排序（升序）
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
     * 备注信息
     */
    private String remarks;
    /**
     * 删除标记
     */
    private Integer delFlag;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + this.id + "," +
                "\"type\":" + this.type + "," +
                "\"name\":" + this.name + "," +
                "\"value\":" + this.value + "," +
                "\"description\":" + this.description + "," +
                "\"sort\":" + this.sort + "," +
                "\"createBy\":" + this.createBy + "," +
                "\"updateBy\":" + this.updateBy + "," +
                "\"remarks\":" + this.remarks + "," +
                "\"delFlag\":" + this.delFlag + "," +
                "\"createTime\":" + this.createTime + "," +
                "\"updateTime\":" + this.updateTime + "," +
                "}";
    }

}