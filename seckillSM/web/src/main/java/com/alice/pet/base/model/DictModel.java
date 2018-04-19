package com.alice.pet.base.model;

import com.alice.pet.base.domain.Dict;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 字典表
 */
@Data
@NoArgsConstructor
public class DictModel implements Serializable {

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

    public DictModel(Dict domain) {
        this.id = domain.getId();
        this.type = domain.getType();
        this.name = domain.getName();
        this.value = domain.getValue();
        this.description = domain.getDescription();
        this.sort = domain.getSort();
        this.createBy = domain.getCreateBy();
        this.updateBy = domain.getUpdateBy();
        this.remarks = domain.getRemarks();
        this.delFlag = domain.getDelFlag();
        this.createTime = domain.getCreateTime();
        this.updateTime = domain.getUpdateTime();
    }

    public static Dict convertDomain(DictModel model) {
        Dict domain = new Dict();
        domain.setId(model.getId());
        domain.setType(model.getType());
        domain.setName(model.getName());
        domain.setValue(model.getValue());
        domain.setDescription(model.getDescription());
        domain.setSort(model.getSort());
        domain.setCreateBy(model.getCreateBy());
        domain.setUpdateBy(model.getUpdateBy());
        domain.setRemarks(model.getRemarks());
        domain.setDelFlag(model.getDelFlag());
        domain.setCreateTime(model.getCreateTime());
        domain.setUpdateTime(model.getUpdateTime());
        return domain;
    }

}