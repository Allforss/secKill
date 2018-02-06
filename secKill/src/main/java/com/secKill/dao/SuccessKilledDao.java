package com.secKill.dao;

import org.apache.ibatis.annotations.Param;

import com.secKill.entity.SuccessKilled;

/**
 * 用户秒杀信息相关接口
 * @author Administrator
 *
 */
public interface SuccessKilledDao {

	/**
	 * 通过商品id和用户电话号码查询商品信息，并返回商品实体
	 * @param seckillId
	 * @param userPhone
	 * @return
	 */
	public SuccessKilled queryByIdAndPhoneWithSecKill(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);
	
	/**
	 * 插入用户秒杀信息
	 * @param seckillId
	 * @param userPhone
	 * @return
	 */
	public int insertSuccessKilled(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);
	
	
	
}
