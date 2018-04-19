package com.alice.pet.base.model;

import lombok.Data;

import java.util.Date;

/**
 * 在线用户信息
 */
@Data
public class OnlineModel {

    /**
     * sessionId
     */
    private String sessionId;

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 账号
     */
    private String account;

    /**
     * 用户主机地址
     */
    private String host;

    /**
     * 用户登录时系统IP
     */
    private String systemHost;

    /**
     * 用户浏览器类型
     */
    private String userAgent;

    /**
     * session创建时间
     */
    private Date startTimestamp;

    /**
     * session最后访问时间
     */
    private Date lastAccessTime;

    /**
     * 超时时间
     */
    private Long timeout;

}
