package com.alice.pet.base.web;

import com.alice.pet.base.common.annotation.Log;
import com.alice.pet.base.common.constants.RedisConstants;
import com.alice.pet.base.domain.User;
import com.alice.pet.base.model.MessageBean;
import com.alice.pet.base.model.OnlineModel;
import com.alice.pet.base.shiro.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author xudong.cheng
 * @date 2018/1/20 下午11:45
 */
@Controller
@RequestMapping("/sys/online")
public class OnlineController extends BaseController {

    @Autowired
    private SessionDAO sessionDAO;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Log("在线用户页面")
    @RequiresPermissions("sys:online:list")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String toOnline() {
        return "sys/online/list";
    }

    @Log("在线用户列表")
    @ResponseBody
    @RequiresPermissions("sys:online:list")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public MessageBean findList() {
        return this.process(() -> {
            Collection<Session> activeSessions = sessionDAO.getActiveSessions();
            return activeSessions.stream().map(session -> {
                User user = (User) session.getAttribute(ShiroUtil.USER_INFO_KEY);
                OnlineModel model = new OnlineModel();
                model.setSessionId(session.getId().toString());
                model.setId(user.getId());
                model.setName(user.getName());
                model.setAccount(user.getAccount());
                model.setHost(session.getHost());
                model.setSystemHost(session.getHost());
                model.setStartTimestamp(session.getStartTimestamp());
                model.setLastAccessTime(session.getLastAccessTime());
                model.setTimeout(redisTemplate.getExpire(RedisConstants.SPRING_SESSION_MY + session.getId()));
                return model;
            }).collect(Collectors.toList());
        });
    }

    @Log("强制下线用户")
    @ResponseBody
    @RequiresPermissions("sys:online:forceLogout")
    @RequestMapping(value = "forceLogout", method = RequestMethod.POST)
    public MessageBean forceLogout(String sessionId) {
        return this.process(() -> {
            redisTemplate.delete(RedisConstants.SPRING_SESSION_MY + sessionId);
            return null;
        });
    }

    @Log("在线用户姓名")
    @ResponseBody
    @RequiresPermissions("sys:online:username")
    @RequestMapping(value = "username", method = RequestMethod.GET)
    public MessageBean onlineUserName() {
        return this.process(() -> sessionDAO.getActiveSessions()
                .stream().map(session -> ((User) session.getAttribute(ShiroUtil.USER_INFO_KEY)).getName())
                .collect(Collectors.toList()));
    }

}
