package com.sukidesu.common.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 暴露秒杀地址DTO
 * @author weixian.yan
 *
 */
@NoArgsConstructor
@Data
public class Exposer {

	private boolean exposed;
	
	private String md5;
	
	private long goodsId;
	
	private long now;
	
	private long start;
	
	private long end;

	public Exposer(boolean exposed, String md5, long goodsId) {
		super();
		this.exposed = exposed;
		this.md5 = md5;
		this.goodsId = goodsId;
	}

	public Exposer(boolean exposed, long goodsId, long now, long start, long end) {
		super();
		this.exposed = exposed;
		this.goodsId = goodsId;
		this.now = now;
		this.start = start;
		this.end = end;
	}

	public Exposer(boolean exposed,String md5, long goodsId, long now, long start, long end) {
		super();
		this.md5 = md5;
		this.exposed = exposed;
		this.goodsId = goodsId;
		this.now = now;
		this.start = start;
		this.end = end;
	}

	public Exposer(boolean exposed, long goodsId) {
		super();
		this.exposed = exposed;
		this.goodsId = goodsId;
	}
	
	
}
