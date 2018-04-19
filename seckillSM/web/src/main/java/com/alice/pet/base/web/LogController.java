package com.alice.pet.base.web;

import com.github.pagehelper.Page;
import com.alice.pet.base.common.annotation.Log;
import com.alice.pet.base.common.constants.RedisConstants;
import com.alice.pet.base.common.page.PageList;
import com.alice.pet.base.model.LogModel;
import com.alice.pet.base.model.MessageBean;
import com.alice.pet.base.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;

/**
 * @author xudong.cheng
 * @date 2018/1/22 下午4:30
 */
@Controller
@RequestMapping("/sys/log")
public class LogController extends BaseController {

    @Autowired
    private LogService logService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Log("系统日志页面")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String toList() {
        return "sys/log/list";
    }

    @Log("系统日志列表")
    @ResponseBody
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public MessageBean list(int offset, int limit, com.alice.pet.base.domain.Log log) {
        return this.process(() -> {
            Page<com.alice.pet.base.domain.Log> page = logService.findPage(offset, limit, log);
            return new PageList(page.getTotal(), page.getResult().stream().map(LogModel::new).collect(Collectors.toList()));
        });
    }

    @ResponseBody
    @RequestMapping(value = "operate", method = RequestMethod.GET)
    public MessageBean operate() {
        return this.process(() -> redisTemplate.opsForSet().members(RedisConstants.LOG_OPERATE_SET_KEY));
    }

}
