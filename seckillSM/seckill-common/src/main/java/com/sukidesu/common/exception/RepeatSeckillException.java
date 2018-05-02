package com.sukidesu.common.exception;


/**
 * 重复秒杀异常
 * @author weixian.yan
 *
 */
public class RepeatSeckillException extends SeckillException{

	public RepeatSeckillException(String message) {
		super(message);
	}

	public RepeatSeckillException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
