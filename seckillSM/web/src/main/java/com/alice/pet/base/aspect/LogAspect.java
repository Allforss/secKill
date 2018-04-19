package com.alice.pet.base.aspect;

import com.alibaba.fastjson.JSON;
import com.alice.pet.base.common.constants.RedisConstants;
import com.alice.pet.base.domain.Log;
import com.alice.pet.base.domain.User;
import com.alice.pet.base.mapper.LogMapper;
import com.alice.pet.base.shiro.ShiroUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 记录操作日志
 *
 * @author xudong.cheng
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    @Autowired
    private LogMapper logMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

    @Pointcut("@annotation(com.alice.pet.base.common.annotation.Log)")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        // 执行方法
        Object result = point.proceed();
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        saveLog(point, time);
        return result;
    }

    private void saveLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Log sysLog = new Log();
        com.alice.pet.base.common.annotation.Log logAnno = method.getAnnotation(com.alice.pet.base.common.annotation.Log.class);
        if (logAnno != null) {
            // 注解上的描述
            sysLog.setOperation(logAnno.value());
            redisTemplate.opsForSet().add(RedisConstants.LOG_OPERATE_SET_KEY, logAnno.value());
        }
        // 请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");
        // 请求的参数
        Object[] args = joinPoint.getArgs();
        try {
            String params = JSON.toJSONString(args);
            if (params.length() > 5000) {
                params = params.substring(0, 4999);
            }
            sysLog.setParams(params);
        } catch (Exception e) {
            log.error("LogAspect params toJson error", e);
        }

        // 获取request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 设置IP地址
        sysLog.setIp(getIpAddr(request));
        // 用户名
        User currUser = ShiroUtil.getCurrentUser();
        if (null == currUser) {
            if (null != sysLog.getParams()) {
                sysLog.setUserId("0");
                sysLog.setUserName("");
            } else {
                sysLog.setUserId("0");
                sysLog.setUserName("");
            }
        } else {
            sysLog.setUserId(currUser.getUserId());
            sysLog.setUserName(currUser.getName());
        }
        sysLog.setTime((int) time);
        // 保存系统日志
        CompletableFuture.runAsync(() -> logMapper.insert(sysLog), executorService).whenCompleteAsync((v, t) -> {
            if (t != null) {
                log.error("log error", t);
            }
        });
    }

    /**
     * 获取IP地址
     * <p>
     * 使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
     * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {

        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

}
