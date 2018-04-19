package com.alice.pet.base.web;

import com.github.pagehelper.Page;
import com.alice.pet.base.common.annotation.Log;
import com.alice.pet.base.common.page.PageList;
import com.alice.pet.base.domain.Role;
import com.alice.pet.base.domain.User;
import com.alice.pet.base.model.MessageBean;
import com.alice.pet.base.model.UserModel;
import com.alice.pet.base.service.RoleService;
import com.alice.pet.base.service.UserService;
import com.alice.pet.base.shiro.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;

/**
 * @author xudong.cheng
 * @date 2018/1/18 下午2:57
 */
@Controller
@RequestMapping("/sys/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Log("用户管理页面")
    @RequiresPermissions("sys:user:list")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String toList() {
        return "sys/user/list";
    }

    @Log("用户是否存在")
    @ResponseBody
    @RequestMapping(value = "exit", method = RequestMethod.GET)
    public boolean exit(User user) {
        return !userService.findList(user).stream().findAny().isPresent();
    }

    @Log("添加用户页面")
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String toAdd(Model model) {
        Role role = new Role();
        role.setDelFlag(0);
        model.addAttribute("roles", roleService.findList(role));
        return "sys/user/add";
    }

    @Log("添加用户")
    @ResponseBody
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public MessageBean add(User user) {
        return this.process(() -> {
            User currentUser = ShiroUtil.getCurrentUser();
            return userService.addWithRoles(user);
        });
    }

    @Log("修改用户页面")
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String toEdit(Model model) {
        return "sys/user/edit";
    }

    @Log("修改用户")
    @ResponseBody
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public MessageBean edit(User user) {
        return null;
    }

    @Log("用户管理列表")
    @RequiresPermissions("sys:user:list")
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public MessageBean findList(int offset, int limit, String search) {
        return this.process(() -> {
            Page<User> page = userService.findPage(offset, limit, search);
            return new PageList(page.getTotal(), page.getResult().stream().map(UserModel::new).collect(Collectors.toList()));
        });
    }

}
