package com.secKill.exception;


/**
 * 重复秒杀异常
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class RepeatSeckillException extends SeckillException{

	public RepeatSeckillException(String message) {
		super(message);
	}

	public RepeatSeckillException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
