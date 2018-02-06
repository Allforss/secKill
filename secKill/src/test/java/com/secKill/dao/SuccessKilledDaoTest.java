package com.secKill.dao;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.secKill.entity.SuccessKilled;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {
	
	@Resource
	private SuccessKilledDao skd;

	@Test
	public void testInsertSuccessKilled() {
		int flag = skd.insertSuccessKilled(1001, 18765392876l);
		System.out.println("flag:"+flag);
	}
	
	@Test
	public void testQueryByIdAndPhoneWithSecKill() {
		long seckillId = 1001;
		long userPhone = 18765392876l;
		SuccessKilled sk = skd.queryByIdAndPhoneWithSecKill(seckillId, userPhone);
		System.out.println(sk.toString());
	}


}
