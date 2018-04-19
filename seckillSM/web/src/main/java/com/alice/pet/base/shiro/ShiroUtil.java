package com.alice.pet.base.shiro;

import com.alice.pet.base.domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

/**
 * @author xudong.cheng
 * @date 2017/12/3 23:39
 */
public class ShiroUtil {

    public static final String USER_INFO_KEY = "login:user:info";

    public static User getCurrentUser() {
        return (User) getCurrentSession().getAttribute(USER_INFO_KEY);
    }

    public static void updateCurrentUser(User user) {
        getCurrentSession().setAttribute(USER_INFO_KEY, user);
    }

    public static Session getCurrentSession() {
        Session session = SecurityUtils.getSubject().getSession();
        if (session == null) {
//            throw new BizException(ErrorCodeEnum.unLogin);
        }
        return session;
    }

    public static Object getSessionAttr(Object key) {
        return getCurrentSession().getAttribute(key);
    }

    public static void setSessionAttr(Object key, Object value) {
        getCurrentSession().setAttribute(key, value);
    }

    public static void removeSessionAttr(Object key) {
        getCurrentSession().removeAttribute(key);
    }

}
