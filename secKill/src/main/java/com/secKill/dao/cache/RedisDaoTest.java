package com.secKill.dao.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.secKill.dao.SecKillDao;
import com.secKill.entity.SecKill;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest {
	
	private long id = 1001;
	
	@Autowired
	private RedisDao redisdao;
	
	@Autowired
	private SecKillDao secKilldao;

	@Test
	public void test() {
		SecKill secKill = redisdao.getSecKill(id);
		if(null == secKill){
			secKill = secKilldao.queryById(id);
			if(null != secKill){
				String result = redisdao.putSeckill(secKill);
				System.out.println("result:"+result);
			}
		}
		secKill = redisdao.getSecKill(id);
		System.out.println(secKill);
	}

}
