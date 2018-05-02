package com.sukidesu.server.dto;


import com.sukidesu.server.common.enums.SeckillStateEnum;
import com.sukidesu.server.domain.SeckillOrder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 封装秒杀执行后的结果
 * @author Administrator
 *
 */
@Data
@NoArgsConstructor
public class SeckillExecution {

	//商品id
	private long goodsId;
	
	//状态
	private int state;

	//状态信息
	private String stateInfo;
	
	//秒杀成功对象
	private SeckillOrder seckillOrder;

	public SeckillExecution(long goodsId, SeckillStateEnum stateEnum,
			SeckillOrder seckillOrder) {
		super();
		this.goodsId = goodsId;
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.seckillOrder = seckillOrder;
	}

	public SeckillExecution(long goodsId, SeckillStateEnum stateEnum) {
		super();
		this.goodsId = goodsId;
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

}
