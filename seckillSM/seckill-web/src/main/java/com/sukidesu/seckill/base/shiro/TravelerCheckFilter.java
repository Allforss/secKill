package com.sukidesu.seckill.base.shiro;

import com.sukidesu.common.common.Constants.Constants;
import com.sukidesu.seckill.base.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author weixian.yan
 * @created on 17:05 2018/5/12
 * @description:
 */
@Slf4j
public class TravelerCheckFilter implements HandlerInterceptor{

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute(Constants.USER_INFO_KEY);
        String url = request.getRequestURI();
//        log.info("Controller url={}",url);
//        log.info("currentUser={}",currentUser);
        if(null != currentUser && Constants.TRAVELER.equalsIgnoreCase(currentUser.getAccount())){
            if(Constants.TRAVELER_URL.contains(url)){
                response.sendRedirect("/logout");
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
