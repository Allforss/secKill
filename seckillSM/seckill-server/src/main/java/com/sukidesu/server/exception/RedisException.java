package com.sukidesu.server.exception;


/**
 * @author weixian.yan
 * @created on 14:33 2018/4/29
 * @description: reids相关异常
 */
public class RedisException extends RuntimeException {

    public RedisException(String message, Throwable cause){
        super(message, cause);
    }

    public RedisException(String message){
        super(message);
    }

}
