package com.sukidesu.seckill.base.service;

import com.sukidesu.seckill.base.domain.Menu;
import com.sukidesu.seckill.base.model.TreeListModel;
import com.sukidesu.seckill.base.model.TreeModel;

import java.util.List;

/**
 * @author xudong.cheng
 * @date 2018/1/17 下午4:10
 */
public interface MenuService {

    List<Menu> findMenuByUserId(String userId);

    List<Menu> findMenuForAdmin();

    List<String> findPermCodes(String userId);

    List<Menu> findList(String name);

    List<TreeModel> findTree();

    List<TreeListModel> findTreeList();

    Menu get(String menuId);

    int add(Menu menu);

    int remove(Menu menu);

    int update(Menu menu);
}
