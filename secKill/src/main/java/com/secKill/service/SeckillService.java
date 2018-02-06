package com.secKill.service;

import java.util.List;

import com.secKill.dto.Exposer;
import com.secKill.dto.SeckillExecution;
import com.secKill.entity.SecKill;
import com.secKill.exception.RepeatSeckillException;
import com.secKill.exception.SeckillCloseException;
import com.secKill.exception.SeckillException;

/**
 * 业务接口：站在使用者的角度设计接口 
 * 三个方面：方法定义粒度、参数、返回类型（return类型/异常）
 * 
 * @author Administrator
 *
 */
public interface SeckillService {

	/**
	 * 查询所有秒杀记录
	 * 
	 * @return
	 */
	public List<SecKill> getSeckillList();

	/**
	 * 查询单个记录
	 * 
	 * @param seckillId
	 * @return
	 */
	public SecKill getById(long seckillId);

	/**
	 * 秒杀开启时输出秒杀接口地址， 否则输出系统时间和秒杀开启时间
	 * @param seckillId
	 * @return
	 */
	public Exposer exportSeckillUrl(long seckillId);

	/**
	 * 执行秒杀操作
	 * @param seckillID
	 * @param userPhone
	 * @param md5
	 * @return
	 */
	public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
			throws RepeatSeckillException,SeckillCloseException,SeckillException;
	
	
	/**
	 * 使用存储过程执行秒杀操作
	 * @param seckillID
	 * @param userPhone
	 * @param md5
	 * @return
	 */
	public SeckillExecution executeSeckillProcedure(long seckillId, long userPhone, String md5)
			throws RepeatSeckillException,SeckillCloseException,SeckillException;

}
