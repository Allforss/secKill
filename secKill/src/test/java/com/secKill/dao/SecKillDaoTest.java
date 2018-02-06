package com.secKill.dao;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.secKill.entity.SecKill;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SecKillDaoTest {

	@Resource
	private SecKillDao seckillDao;
	
	@Test
	public void testReduceNumber() {
		int flag = seckillDao.reduceNumber(1001, new Date());
		System.out.println(flag);
	}

	@Test
	public void testQueryById() {
		long seckillId = 1001;
		SecKill secKill = seckillDao.queryById(seckillId);
		System.out.println("secKill:"+secKill.toString());
	}

	@Test
	public void testQueryAll() {
		List<SecKill> list = seckillDao.queryAll(0, 5);
		System.out.println(list.toString());
	}

}
