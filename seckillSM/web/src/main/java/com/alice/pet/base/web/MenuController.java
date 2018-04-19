package com.alice.pet.base.web;

import com.alice.pet.base.common.annotation.Log;
import com.alice.pet.base.domain.Menu;
import com.alice.pet.base.domain.User;
import com.alice.pet.base.model.MenuModel;
import com.alice.pet.base.model.MessageBean;
import com.alice.pet.base.service.MenuService;
import com.alice.pet.base.shiro.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;

/**
 * @author xudong.cheng
 * @date 2018/1/17 下午4:39
 */
@Controller
@RequestMapping("/sys/menu")
public class MenuController extends BaseController {

    @Autowired
    private MenuService menuService;

    @Log("菜单资源管理页面")
    @RequiresPermissions("sys:menu:list")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String toList() {
        return "sys/menu/list";
    }

    @Log("菜单资源管理列表")
    @RequiresPermissions("sys:menu:list")
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public MessageBean findList(String search) {
        return this.process(() -> menuService.findList(search).stream().map(MenuModel::new).collect(Collectors.toList()));
    }

    @Log("获取用户已授权菜单资源")
    @ResponseBody
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public MessageBean findByUserId(@PathVariable("userId") String userId) {
        return this.process(() -> menuService.findMenuByUserId(userId).stream().map(MenuModel::new).collect(Collectors.toList()));
    }

    @Log("菜单资源添加页面")
    @RequiresPermissions("sys:menu:add")
    @RequestMapping(value = "add/{pid}", method = RequestMethod.GET)
    public String toAdd(@PathVariable("pid") String pid, Model model) {
        Menu menu;
        if ("0".equals(pid)) {
            menu = new Menu("0");
            menu.setType(null);
            menu.setName("根节点");
        } else {
            menu = menuService.get(pid);
        }
        model.addAttribute("parent", menu);
        return "sys/menu/add";
    }

    @Log("菜单资源添加")
    @ResponseBody
    @RequiresPermissions("sys:menu:add")
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public MessageBean add(Menu menu) {
        return this.process(() -> menuService.add(menu));
    }

    @Log("菜单资源删除")
    @ResponseBody
    @RequiresPermissions("sys:menu:remove")
    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public MessageBean remove(Menu menu) {
        return this.process(() -> menuService.remove(menu));
    }

    @Log("菜单资源修改页面")
    @RequiresPermissions("sys:menu:edit")
    @RequestMapping(value = "edit/{menuId}", method = RequestMethod.GET)
    public String toEdit(@PathVariable("menuId") String menuId, Model model) {
        Menu menu = menuService.get(menuId);
        if ("0".equals(menu.getParentId())) {
            menu.setParent(Menu.ROOT);
        } else {
            menu.setParent(menuService.get(menu.getParentId()));
        }
        model.addAttribute("menu", menu);
        return "sys/menu/edit";
    }

    @Log("菜单资源修改")
    @ResponseBody
    @RequiresPermissions("sys:menu:edit")
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public MessageBean edit(Menu menu) {
        User currentUser = ShiroUtil.getCurrentUser();
        menu.setUpdateBy(currentUser.getUserId());
        return this.process(() -> menuService.update(menu));
    }

    @Log("菜单资源管理列表")
    @ResponseBody
    @RequestMapping(value = "tree", method = RequestMethod.GET)
    public MessageBean findTree() {
        return this.process(() -> menuService.findTree());
    }

    @Log("菜单资源管理列表")
    @ResponseBody
    @RequestMapping(value = "treeList", method = RequestMethod.GET)
    public MessageBean findTreeList() {
        return this.process(() -> menuService.findTreeList());
    }
}
