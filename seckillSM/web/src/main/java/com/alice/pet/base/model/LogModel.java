package com.alice.pet.base.model;

import com.alice.pet.base.domain.Log;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统日志
 */
@Data
@NoArgsConstructor
public class LogModel implements Serializable {

    /**
     * 
     */
    private Long id;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户操作
     */
    private String operation;
    /**
     * 响应时间
     */
    private Integer time;
    /**
     * 请求方法
     */
    private String method;
    /**
     * 请求参数
     */
    private String params;
    /**
     * IP地址
     */
    private String ip;
    /**
     * 创建时间
     */
    private Date createTime;

    public LogModel(Log domain) {
        this.id = domain.getId();
        this.userId = domain.getUserId();
        this.userName = domain.getUserName();
        this.operation = domain.getOperation();
        this.time = domain.getTime();
        this.method = domain.getMethod();
        this.params = domain.getParams();
        this.ip = domain.getIp();
        this.createTime = domain.getCreateTime();
    }

    public static Log convertDomain(LogModel model) {
        Log domain = new Log();
        domain.setId(model.getId());
        domain.setUserId(model.getUserId());
        domain.setUserName(model.getUserName());
        domain.setOperation(model.getOperation());
        domain.setTime(model.getTime());
        domain.setMethod(model.getMethod());
        domain.setParams(model.getParams());
        domain.setIp(model.getIp());
        domain.setCreateTime(model.getCreateTime());
        return domain;
    }

}