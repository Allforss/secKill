package com.secKill.service.imp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.secKill.dao.SecKillDao;
import com.secKill.dao.SuccessKilledDao;
import com.secKill.dao.cache.RedisDao;
import com.secKill.dto.Exposer;
import com.secKill.dto.SeckillExecution;
import com.secKill.entity.SecKill;
import com.secKill.entity.SuccessKilled;
import com.secKill.enums.SeckillStateEnum;
import com.secKill.exception.RepeatSeckillException;
import com.secKill.exception.SeckillCloseException;
import com.secKill.exception.SeckillException;
import com.secKill.service.SeckillService;
import com.secKill.util.MD5Util;
import com.sun.org.apache.regexp.internal.recompile;

/**
 * 秒杀相关业务实现类
 * 
 * @author Administrator
 *
 */
@Service
public class SeckillServiceImp implements SeckillService {

	// 注入service依赖
	@Autowired
	private SecKillDao seckillDao;

	@Autowired
	private RedisDao redisdao;

	@Autowired
	private SuccessKilledDao successKilledDao;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<SecKill> getSeckillList() {
		return seckillDao.queryAll(0, 100);
	}

	@Override
	public SecKill getById(long seckillId) {
		return seckillDao.queryById(seckillId);
	}

	@Override
	public Exposer exportSeckillUrl(long seckillId) {
		SecKill secKill = redisdao.getSecKill(seckillId);
		// 使用Redis缓存优化,
		if (null == secKill) {
			secKill = seckillDao.queryById(seckillId);
			if (null == secKill) {
				return new Exposer(false, seckillId);
			} else {
				redisdao.putSeckill(secKill);
			}
		}
		Date nowTime = new Date();
		Date startTime = secKill.getStartTime();
		Date endTime = secKill.getEndTime();
		if (nowTime.getTime() < startTime.getTime()
				|| nowTime.getTime() > endTime.getTime()) {
			return new Exposer(false, seckillId, nowTime.getTime(),
					startTime.getTime(), endTime.getTime());
		}
		// 生成md5
		String md5 = MD5Util.getMD5(seckillId);
		return new Exposer(true, md5, seckillId);
	}

	/**
	 * 使用注解控制事物方法的优点： 1：开发团队达成一致约定，明确标注事物方法的编程风格。
	 * 2：保证事物方法的执行时间尽可能短，不要穿插其他的网络操作（RPC/HTTP请求）或者剥离到事物方法外
	 * 3：不是所有的方法都需要事物，如只有一条修改操作，只读操作不需要事物
	 */
	@Override
	@Transactional
	public SeckillExecution executeSeckill(long seckillId, long userPhone,
			String md5) throws RepeatSeckillException, SeckillCloseException,
			SeckillException {
		if (md5 == null || !md5.equals(MD5Util.getMD5(seckillId))) {
			throw new SeckillException("seckill data rewrite");
		}
		// 执行秒杀逻辑：减库存+增加秒杀成功记录
		Date nowTime = new Date();

		try {
			int insertCount = successKilledDao.insertSuccessKilled(seckillId,
					userPhone);
			if (insertCount <= 0) {
				// 重复秒杀
				throw new RepeatSeckillException("seckill repeated");
			} else {
				// 热点商品竞争
				int rowCount = seckillDao.reduceNumber(seckillId, nowTime);
				if (rowCount <= 0) {// 没有更新记录，秒杀结束，事物回滚
					throw new SeckillCloseException("seckill is closed");
				} else {
					// 秒杀成功 commit
					SuccessKilled successKilled = successKilledDao
							.queryByIdAndPhoneWithSecKill(seckillId, userPhone);
					return new SeckillExecution(seckillId,
							SeckillStateEnum.SUCCESS, successKilled);
				}
			}
		} catch (RepeatSeckillException e1) {
			throw e1;
		} catch (SeckillCloseException e2) {
			throw e2;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			// 所有编译异常转化为运行异常
			throw new SeckillException("seckill inner error");
		}
	}

	@Override
	public SeckillExecution executeSeckillProcedure(long seckillId,
			long userPhone, String md5) throws RepeatSeckillException,
			SeckillCloseException, SeckillException {
		if (md5 == null || !md5.equals(MD5Util.getMD5(seckillId))) {
			throw new SeckillException("seckill data rewrite");
		}
		Date nowTime = new Date();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("seckillId", seckillId);
		params.put("userPhone", userPhone);
		params.put("killTime", nowTime);
		params.put("result", null);
		try {
			seckillDao.killByProcedure(params);
			// 获取result
			int result = MapUtils.getInteger(params, "result", -2);
			if (result == 1) {
				SuccessKilled sk = successKilledDao
						.queryByIdAndPhoneWithSecKill(seckillId, userPhone);
				return new SeckillExecution(seckillId,
						SeckillStateEnum.SUCCESS, sk);
			} else {
				return new SeckillExecution(seckillId,
						SeckillStateEnum.stateOf(result));
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			return new SeckillExecution(seckillId, SeckillStateEnum.INNER_ERROR);
		}
	}

}
