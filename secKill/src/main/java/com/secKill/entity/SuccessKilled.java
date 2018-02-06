package com.secKill.entity;

import java.util.Date;


/**
 * 用户秒杀明细实体
 * @author Administrator
 *
 */
public class SuccessKilled {

	private long seckillId;
	private long userPhone;
	private short state;
	private Date createTime;
	
	private SecKill secKill;

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public long getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(long userPhone) {
		this.userPhone = userPhone;
	}

	public short getState() {
		return state;
	}

	public void setState(short state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public SecKill getSecKill() {
		return secKill;
	}

	public void setSecKill(SecKill secKill) {
		this.secKill = secKill;
	}

	@Override
	public String toString() {
		return "SuccessKilled [seckillId=" + seckillId + ", userPhone="
				+ userPhone + ", state=" + state + ", createTime=" + createTime
				+ ", secKill=" + secKill + "]";
	}
	
	
}
