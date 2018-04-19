package com.alice.pet.base.model;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.alice.pet.base.domain.Menu;
import lombok.Data;

import java.io.Serializable;

/**
 * tree TODO <br>
 *
 * @author kangxu2 2017-1-7
 */
@Data
public class TreeListModel implements Serializable {
    private static final long serialVersionUID = 810353752604089089L;
    /**
     * 节点ID
     */
    private String id;

    /**
     * 父ID
     */
    @JsonProperty("pId")
    private String pId;

    /**
     * 显示节点文本
     */
    private String name;

    private boolean halfCheck = false;

    /**
     * 节点是否被选中 true false
     */
    private boolean checked = false;

    /**
     * 节点是否被选中 true false
     */
    private boolean open = false;

    public TreeListModel(Menu menu) {
        this.id = menu.getMenuId();
        this.name = menu.getName();
        this.pId = menu.getParentId();
    }

    public TreeListModel() {
        super();
    }

    public static TreeListModel getRoot() {
        TreeListModel root = new TreeListModel();
        root.setId("0");
        root.setName("根节点");
        return root;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}