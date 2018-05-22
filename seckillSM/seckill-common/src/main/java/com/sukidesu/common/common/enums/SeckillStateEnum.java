package com.sukidesu.common.common.enums;

/**
 * 使用枚举表示state常量数据
 * @author weixian.yan
 *
 */
public enum SeckillStateEnum {
	SUCCESS(1,"秒杀成功"),
	END(0,"秒杀结束"),
	REPEAT_KILL(-1,"重复秒杀"),
	INNER_ERROR(-2,"系统异常"),
	DATA_REWRITE(-3,"数据被篡改"),
	NOT_ALLOWED(-4,"不符合秒杀条件"),
	REQUEST_TIMEOUT(-5,"请求超时"),
	;
	private int state;
	
	private String stateInfo;

	SeckillStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}
	
	public static SeckillStateEnum stateOf(int index){
		for(SeckillStateEnum state : values()){
			if(state.getState() == index ){
				return state;
			}
		}
		return null;
	}
	
}
