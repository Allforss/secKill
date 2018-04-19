package com.alice.pet.base.model;

import com.alibaba.fastjson.JSON;
import com.alice.pet.base.domain.Menu;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * tree TODO <br>
 *
 * @author kangxu2 2017-1-7
 */
@Data
public class TreeModel implements Serializable {
    private static final long serialVersionUID = 810353752604089089L;
    /**
     * 节点ID
     */
    private String id;
    /**
     * 显示节点文本
     */
    private String text;
    /**
     * 节点状态，open closed
     */
    private Map<String, Object> state;
    /**
     * 节点是否被选中 true false
     */
    private boolean checked = false;
    /**
     * 节点属性
     */
    private Map<String, Object> attributes;

    /**
     * 节点的子节点
     */
    private List<TreeModel> children = new ArrayList<>();

    /**
     * 父ID
     */
    private String parentId;
    /**
     * 是否有父节点
     */
    private boolean hasParent = false;
    /**
     * 是否有子节点
     */
    private boolean hasChildren = false;


    public TreeModel(String id, String text, Map<String, Object> state, boolean checked, Map<String, Object> attributes,
                     List<TreeModel> children, boolean isParent, boolean isChildren, String parentID) {
        super();
        this.id = id;
        this.text = text;
        this.state = state;
        this.checked = checked;
        this.attributes = attributes;
        this.children = children;
        this.hasParent = isParent;
        this.hasChildren = isChildren;
        this.parentId = parentID;
    }

    public TreeModel(Menu menu) {
        this.id = menu.getMenuId();
        this.text = menu.getName();
        this.parentId = menu.getParentId();
        if (!CollectionUtils.isEmpty(menu.getSub())) {
            this.hasChildren = true;
            this.children = menu.getSub().stream().map(TreeModel::new).collect(Collectors.toList());
        }
    }

    public TreeModel() {
        super();
    }

    public static TreeModel getRoot() {
        TreeModel root = new TreeModel();
        root.setId("0");
        root.setText("根节点");
        root.setHasChildren(true);
        root.setChildren(Collections.emptyList());
        return root;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}