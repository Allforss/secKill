package com.alice.pet.base.web;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.alice.pet.base.common.annotation.Log;
import com.alice.pet.base.common.page.PageList;
import com.alice.pet.base.domain.Role;
import com.alice.pet.base.domain.User;
import com.alice.pet.base.model.MessageBean;
import com.alice.pet.base.model.RoleModel;
import com.alice.pet.base.service.RoleService;
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
 * @date 2018/1/19 下午2:08
 */
@Controller
@RequestMapping("/sys/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @Log("角色管理页面")
    @RequiresPermissions("sys:role:list")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String toList() {
        return "sys/role/list";
    }

    @Log("角色管理列表")
    @SuppressWarnings("unchecked")
    @RequiresPermissions("sys:role:list")
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public MessageBean findList(int offset, int limit, String search) {
        return this.process(() -> {
            Page<Role> page = roleService.findPage(offset, limit, search);
            return new PageList(page.getTotal(), page.getResult().stream().map(RoleModel::new).collect(Collectors.toList()));
        });
    }

    @Log("添加角色页面")
    @RequiresPermissions("sys:role:add")
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String toAdd() {
        return "sys/role/add";
    }

    @Log("添加角色")
    @ResponseBody
    @RequiresPermissions("sys:role:add")
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public MessageBean add(Role role) {
        return this.process(() -> {
            User currentUser = ShiroUtil.getCurrentUser();
            role.setCreateBy(currentUser.getUserId());
            return roleService.insertWithPerms(role);
        });
    }

    @Log("修改角色页面")
    @RequiresPermissions("sys:role:edit")
    @RequestMapping(value = "edit/{roleId}", method = RequestMethod.GET)
    public String toEdit(@PathVariable("roleId") String roleId, Model model) {
        Role role = roleService.getWithPerms(Role.builder().roleId(roleId).build());
        model.addAttribute("role", role);
        model.addAttribute("menuIds", JSON.toJSONString(role.getMenuIds()));
        return "sys/role/edit";
    }

    @Log("修改角色")
    @ResponseBody
    @RequiresPermissions("sys:role:edit")
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public MessageBean edit(Role role) {
        return this.process(() -> {
            return null;
        });
    }

}
