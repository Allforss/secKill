package com.alice.pet.base.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常处理控制器
 *
 * @author xudong.cheng
 */
@Slf4j
@Controller
public class ErrorController {

    /**
     * 404异常
     *
     * @return
     */
    @RequestMapping("/error/404")
    public String error404() {
        return "error/404";
    }

    /**
     * 403异常
     *
     * @return
     */
    @RequestMapping("/error/403")
    public String error403() {
        return "error/403";
    }

    /**
     * 500异常
     *
     * @return
     */
    @RequestMapping("/error/500")
    public String error500(HttpServletRequest request, Model model) {
        return "error/500";
    }
}
