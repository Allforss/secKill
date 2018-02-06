package com.secKill.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.secKill.dto.Exposer;
import com.secKill.dto.SeckillExecution;
import com.secKill.entity.SecKill;
import com.secKill.exception.RepeatSeckillException;
import com.secKill.exception.SeckillCloseException;
import com.secKill.exception.SeckillException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml",
	"classpath:spring/spring-service.xml"})
public class SeckillServiceTest {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SeckillService seckillService;
	
	@Test
	public void testGetSeckillList() {
		List<SecKill> list = seckillService.getSeckillList();
		logger.info("list={}",list);
	}

	@Test
	public void testGetById() {
		long seckillId = 1001;
		SecKill secKill = seckillService.getById(seckillId);
		logger.info("seckill={}",secKill);
	}

	@Test
	public void testExportSeckillUrl() {
		long seckillId = 1001;
		Exposer exposer = seckillService.exportSeckillUrl(seckillId);
		logger.info("exposer={}",exposer);
		//[exposed=true, 
		//md5=9b3ff2ee5e30d16d28fdac81c2bace5b, 
		//seckillId=1001, 
		//now=0, start=0, end=0]

	}
	
	@Test
	public void testSecKillLogic(){
		long seckillId = 1001;
		long userPhone = 13698790987l;
		Exposer exposer = seckillService.exportSeckillUrl(seckillId);
		if(exposer.isExposed()){
			try {
				logger.info("exposer={}",exposer);
				SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, userPhone, exposer.getMd5());
				logger.info("sk = {}",seckillExecution);
				
			} catch (RepeatSeckillException e) {
				logger.info(e.getMessage());
			} catch (SeckillCloseException e) {
				logger.info(e.getMessage());
			} catch (SeckillException e) {
				logger.info(e.getMessage());
			}
			
		} else{
			logger.warn("exposer={}", exposer);
		}
	}

	@Test
	public void testExecuteSeckill() {
		long seckillId = 1001;
		long userPhone = 13698790987l;
		String md5 = "9b3ff2ee5e30d16d28fdac81c2bace5b";
		SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, userPhone, md5);
		logger.info("sk = {}",seckillExecution);
		//sk = SeckillExecution 
		//[seckillId=1001, state=1, stateInfo=秒杀成功, successKilled=SuccessKilled 
		//[seckillId=1001, userPhone=13698790987, state=0, createTime=Wed Dec 20 16:41:06 CST 2017, secKill=SecKill [seckillId=1001, name=500元秒杀红米note, number=198, startTime=Wed Dec 20 16:41:06 CST 2017, endTime=Tue Mar 06 00:00:00 CST 2018, createTime=Mon Dec 18 19:56:19 CST 2017]]]

	}
	
	@Test
	public void testExecuteSeckillPro(){
		long seckillId = 1001;
		long userPhone = 13698790987l;
		String md5 = "9b3ff2ee5e30d16d28fdac81c2bace5b";
		SeckillExecution seckillExecution = seckillService.executeSeckillProcedure(seckillId, userPhone, md5);
		logger.info("sk = {}",seckillExecution);
		
	}

}
