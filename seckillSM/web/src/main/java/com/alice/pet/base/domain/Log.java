package com.alice.pet.base.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统日志
 */
@Data
@NoArgsConstructor
public class Log implements Serializable {

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

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + this.id + "," +
                "\"userId\":" + this.userId + "," +
                "\"userName\":" + this.userName + "," +
                "\"operation\":" + this.operation + "," +
                "\"time\":" + this.time + "," +
                "\"method\":" + this.method + "," +
                "\"params\":" + this.params + "," +
                "\"ip\":" + this.ip + "," +
                "\"createTime\":" + this.createTime + "," +
                "}";
    }

}