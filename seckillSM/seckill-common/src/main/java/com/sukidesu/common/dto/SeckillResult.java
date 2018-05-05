package com.sukidesu.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author weixian.yan
 * 所有ajax请求的返回类型，封装json结果
 * @param <T>
 */
@Data
@NoArgsConstructor
public class SeckillResult<T> {

	private boolean success;
	
	private T data;
	
	private String error;

	public SeckillResult(boolean success, T data) {
		super();
		this.success = success;
		this.data = data;
	}

	public SeckillResult(boolean success, String error) {
		super();
		this.success = success;
		this.error = error;
	}

	
}
