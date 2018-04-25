package com.sukidesu.seckill.base.web;

import com.github.pagehelper.Page;
import com.sukidesu.seckill.base.common.annotation.Log;
import com.sukidesu.seckill.base.common.page.PageList;
import com.sukidesu.seckill.base.domain.Role;
import com.sukidesu.seckill.base.domain.User;
import com.sukidesu.seckill.base.model.MessageBean;
import com.sukidesu.seckill.base.model.UserModel;
import com.sukidesu.seckill.base.service.RoleService;
import com.sukidesu.seckill.base.service.UserService;
import com.sukidesu.seckill.base.shiro.ShiroUtil;
import com.sukidesu.seckill.base.utils.PasswordUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

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
    @RequestMapping(value = "exist", method = RequestMethod.GET)
    public boolean exist(User user) {
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

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String register(Model model) {
        Role role = new Role();
        role.setDelFlag(0);
        model.addAttribute("roles", roleService.findList(role));
        return "sys/user/add";
    }

    @Log("添加用户")
    @ResponseBody
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public MessageBean add(User user) {
        user.setPassword(PasswordUtil.createUserPwd(user.getPassword()));
        return this.process(() -> {
//            User currentUser = ShiroUtil.getCurrentUser();
            LOGGER.info("user={}",user);
            return userService.addWithRoles(user);
        });
    }

    @Log("修改用户页面")
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String toEdit() {
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
