package com.alice.pet.base.web;

import com.alice.pet.base.common.annotation.Log;
import com.alice.pet.base.common.constants.RedisConstants;
import com.alice.pet.base.common.exception.BizException;
import com.alice.pet.base.domain.Menu;
import com.alice.pet.base.domain.User;
import com.alice.pet.base.enums.LoginUserTypeEnum;
import com.alice.pet.base.model.UserModel;
import com.alice.pet.base.service.MenuService;
import com.alice.pet.base.service.UserService;
import com.alice.pet.base.shiro.AuthcToken;
import com.alice.pet.base.shiro.ShiroUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;



/**
 * @author
 * @date 2018/1/16 下午7:44
 */
@Controller
@RequestMapping("/")
public class LoginController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    @Log("默认页面")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String toIndex(){
        LOGGER.info("welcome");
        return "redirect:/login";
    }

    @Log("后台登录页面")
    @RequestMapping(value = "adminLogin", method = RequestMethod.GET)
    public String toAdminLogin(Model model) {
        if (SecurityUtils.getSubject().isAuthenticated()) {
            return "redirect:/index";
        }
        model.addAttribute("show", false);
        return "sys/adminLogin";
    }

    @Log("统一登录页面")
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String toLogin(Model model) {
        if (SecurityUtils.getSubject().isAuthenticated()) {
            return "redirect:/index";
        }
        model.addAttribute("show", false);
        return "sys/login";
    }

    @Log("登录")
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(String account, String password, String captcha, LoginUserTypeEnum loginUserType, Model model) {
        try {
            AuthcToken token = new AuthcToken(account, password, captcha, loginUserType);
            userService.login(token);
        } catch (BizException e) {
            model.addAttribute("show", true);
            model.addAttribute("msg", e.getDesc());
            return "sys/adminLogin";
        }
        return "redirect:/index";
    }

    @Log("主页")
    @RequestMapping("index")
    public String index(Model model) {
        User currentUser = ShiroUtil.getCurrentUser();
        model.addAttribute("currentUser", new UserModel(currentUser));
        Session session = ShiroUtil.getCurrentSession();
        @SuppressWarnings("unchecked")
        List<Menu> menus = (List<Menu>) session.getAttribute(RedisConstants.CURRENT_USER_MENU_KEY);
        if (menus == null) {
            if (currentUser.getRoleCodes().contains("admin")) {
                menus = menuService.findMenuForAdmin();
            } else {
                menus = menuService.findMenuByUserId(currentUser.getUserId());
            }
            session.setAttribute(RedisConstants.CURRENT_USER_MENU_KEY, menus);
        }
        model.addAttribute("menus", menus);
        return "sys/index";
    }

    @Log("首页")
    @RequestMapping("home")
    public String home() {
        return "home";
    }

    @Log("登出")
    @RequestMapping("logout")
    public String logout() {
        LoginUserTypeEnum loginUserType = (LoginUserTypeEnum) ShiroUtil.getSessionAttr(RedisConstants.CURRENT_USER_TYPE);
        SecurityUtils.getSubject().logout();
        if (LoginUserTypeEnum.manager.equals(loginUserType)) {
            return "redirect:/adminLogin";
        }
        return "redirect:/login";
    }

}
