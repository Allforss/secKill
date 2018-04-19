package com.alice.pet.base.service.impl;

import com.alice.pet.base.common.errorenums.SystemErrorEnum;
import com.alice.pet.base.common.exception.BizException;
import com.alice.pet.base.domain.Menu;
import com.alice.pet.base.mapper.MenuMapper;
import com.alice.pet.base.model.TreeListModel;
import com.alice.pet.base.model.TreeModel;
import com.alice.pet.base.service.MenuService;
import com.alice.pet.base.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xudong.cheng
 * @date 2018/1/17 下午4:11
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> findMenuByUserId(String id) {
        List<Menu> menus = menuMapper.findMenuByUserId(id);
        if (CollectionUtils.isEmpty(menus)) {
            return Collections.emptyList();
        }
        return walkTree(menus);
    }

    @Override
    public List<Menu> findMenuForAdmin() {
        List<Menu> menus = menuMapper.findMenuForAdmin();
        if (CollectionUtils.isEmpty(menus)) {
            return Collections.emptyList();
        }
        return walkTree(menus);
    }

    @Override
    public List<String> findPermCodes(String userId) {
        List<String> perms = menuMapper.findPermCodesByUserId(userId);
        if (CollectionUtils.isEmpty(perms)) {
            return Collections.emptyList();
        }
        return perms;
    }

    @Override
    public List<Menu> findList(String name) {
        Menu menu = new Menu();
        menu.setName(name);
        List<Menu> menus = menuMapper.selectWithSort(menu);
        if (CollectionUtils.isEmpty(menus)) {
            return Collections.emptyList();
        }
        return menus;
    }

    @Override
    public List<TreeModel> findTree() {
        List<Menu> menus = menuMapper.selectWithSort(null);
        if (CollectionUtils.isEmpty(menus)) {
            return Collections.emptyList();
        }
        return buildTreeModel(menus);
    }

    @Override
    public List<TreeListModel> findTreeList() {
        List<Menu> menus = menuMapper.selectWithSort(null);
        if (CollectionUtils.isEmpty(menus)) {
            return Collections.emptyList();
        }
        return menus.stream().sorted(Comparator.comparingInt(Menu::getSort)).map(TreeListModel::new).collect(Collectors.toList());
    }

    @Override
    public Menu get(String menuId) {
        List<Menu> menuList = menuMapper.select(new Menu(menuId));
        if (CollectionUtils.isEmpty(menuList)) {
            return null;
        }
        if (menuList.size() > 1) {
            throw new BizException(SystemErrorEnum.SYSTEM_ERROR.code, SystemErrorEnum.SYSTEM_ERROR.desc);
        }
        return menuList.get(0);
    }

    @Override
    public int add(Menu menu) {
        String menuId = IdGenerator.id();
        menu.setMenuId(menuId);
        return menuMapper.insert(menu);
    }

    @Override
    public int remove(Menu menu) {
        Long id = menu.getId();
        return menuMapper.removeMenuById(id);
    }

    @Override
    public int update(Menu menu) {
        return menuMapper.update(menu);
    }

    public List<Menu> walkTree(List<Menu> menus) {
        Menu root = new Menu("0");
        Map<String, Menu> map = new HashMap<>(menus.size());
        map.put(root.getMenuId(), root);
        for (Menu menu : menus) {
            map.put(menu.getMenuId(), menu);
        }
        for (Menu menu : menus) {
            if (map.containsKey(menu.getParentId())) {
                Menu parent = map.get(menu.getParentId());
                if (parent.getSub() == null) {
                    parent.setSub(new ArrayList<>());
                }
                parent.getSub().add(menu);
            }
        }
        map.values().forEach(perm -> {
            List<Menu> children = perm.getSub();
            if (!CollectionUtils.isEmpty(children)) {
                perm.setSub(children.stream().sorted(Comparator.comparing(Menu::getSort)).collect(Collectors.toList()));
            }
        });
        return root.getSub();
    }

    public List<TreeModel> convertTreeModel(List<Menu> menus) {
        List<TreeModel> treeModels = menus.stream().map(TreeModel::new).collect(Collectors.toList());
        TreeModel root = TreeModel.getRoot();
        Map<String, TreeModel> map = new HashMap<>(menus.size());
        map.put(root.getId(), root);
        for (TreeModel model : treeModels) {
            map.put(model.getId(), model);
        }
        for (TreeModel model : treeModels) {
            if (map.containsKey(model.getParentId())) {
                TreeModel parent = map.get(model.getParentId());
                if (parent.getChildren() == null) {
                    parent.setChildren(new ArrayList<>());
                }
                parent.getChildren().add(model);
            }
        }
        return treeModels;
    }

    public List<TreeModel> buildTreeModel(List<Menu> menus) {
        Map<String, List<TreeModel>> map = menus.stream().map(TreeModel::new).collect(Collectors.groupingBy(TreeModel::getParentId, Collectors.toList()));
        TreeModel root = TreeModel.getRoot();
        walkTreeChildren(root, map);
        return root.getChildren();
    }

    private void walkTreeChildren(TreeModel root, Map<String, List<TreeModel>> map) {
        List<TreeModel> treeModels = map.get(root.getId());
        if (CollectionUtils.isEmpty(treeModels)) {
            return;
        }
        root.setHasChildren(true);
        treeModels.forEach(model -> {
            model.setHasParent(true);
            walkTreeChildren(model, map);
        });
        root.setChildren(treeModels);
    }
}
