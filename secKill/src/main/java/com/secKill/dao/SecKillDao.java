package com.secKill.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.secKill.entity.SecKill;

/**
 * 秒杀商品库存相关接口
 * @author Administrator
 *
 */
public interface SecKillDao {

	/**
	 * 根据商品ID减少库存
	 * @return
	 */
	public int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);
	
	/**
	 * 更具商品ID查询商品信息
	 * @param seckillId
	 * @return
	 */
	public SecKill queryById(@Param("seckillId") long seckillId);
	
	/**
	 * 更具偏移量查询所有商品信息
	 * @return
	 */
	public List<SecKill> queryAll(@Param("offset") int offset, @Param("limit") int limit);
	
	/**
	 * 使用存储过程执行秒杀
	 * @param params
	 */
	void killByProcedure(Map<String, Object> params);
	
	
}
