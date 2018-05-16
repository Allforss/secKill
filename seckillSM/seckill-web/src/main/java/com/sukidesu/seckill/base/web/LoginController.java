package com.sukidesu.seckill.base.web;

import com.sukidesu.common.common.Constants.Constants;
import com.sukidesu.seckill.base.common.annotation.Log;
import com.sukidesu.seckill.base.common.constants.RedisConstants;
import com.sukidesu.seckill.base.common.exception.BizException;
import com.sukidesu.seckill.base.domain.Menu;
import com.sukidesu.seckill.base.domain.User;
import com.sukidesu.seckill.base.enums.LoginUserTypeEnum;
import com.sukidesu.seckill.base.model.UserModel;
import com.sukidesu.seckill.base.service.MenuService;
import com.sukidesu.seckill.base.service.UserService;
import com.sukidesu.seckill.base.shiro.AuthcToken;
import com.sukidesu.seckill.base.shiro.ShiroUtil;
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
        UserModel userModel = null;
        if(null == currentUser){//如果未登录则默认为游客号
            AuthcToken token = new AuthcToken(Constants.TRAVELER, "123", null, LoginUserTypeEnum.sso);
            userService.login(token);
            currentUser = ShiroUtil.getCurrentUser();
            userModel = new UserModel(currentUser);
        }else{
            userModel = new UserModel(currentUser);
        }
        model.addAttribute("currentUser", userModel);

        Session session = ShiroUtil.getCurrentSession();

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
