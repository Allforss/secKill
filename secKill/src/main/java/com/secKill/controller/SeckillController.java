package com.secKill.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.secKill.dto.Exposer;
import com.secKill.dto.SeckillExecution;
import com.secKill.dto.SeckillResult;
import com.secKill.entity.SecKill;
import com.secKill.enums.SeckillStateEnum;
import com.secKill.exception.RepeatSeckillException;
import com.secKill.exception.SeckillCloseException;
import com.secKill.service.SeckillService;


/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("seckill") //URL：/模块/资源/{id}/细分/seckill/list
public class SeckillController {

	private static final Logger logger = LoggerFactory.getLogger(SeckillController.class);
	
	@Autowired
	private SeckillService seckillService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String init(){
		return "redirect:seckill/list";
	}
	
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public String list(Model model){
		//list.jsp + model = ModelAndView
		List<SecKill> list = seckillService.getSeckillList();
		model.addAttribute("list",list);
		return "list"; //WEB-INF/jsp/list.jsp
	}
	
	@RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
	public String detail(@PathVariable("seckillId") Long seckillId, Model model){
		if(null == seckillId){
			return "redirect:/seckill/list";
		}
		SecKill seckill = seckillService.getById(seckillId);
		if(null == seckill){
			return "forward:/seckill/list";
		}
		model.addAttribute("seckill",seckill);
		return "detail";
	}
	
	/**
	 * 使用ajax
	 * @param seckillId
	 */
	@RequestMapping(value="/{seckillId}/exposer", 
			method = RequestMethod.POST,
			produces = {"application/json; charset=utf-8"})
	@ResponseBody
	public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId){
		SeckillResult<Exposer> result = null;
		try {
			Exposer exposer = seckillService.exportSeckillUrl(seckillId);
			result = new SeckillResult<Exposer>(true, exposer);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("e{}", e.getMessage());
			result = new SeckillResult<Exposer>(false, e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value = "/{seckillId}/{md5}/execution",
			method = RequestMethod.POST,
			produces = {"application/json; charset=utf-8"})
	@ResponseBody
	public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
			@PathVariable("md5") String md5,
			@CookieValue(value = "userPhone" , required = false) Long userPhone ){
		if(null == userPhone){
			return new SeckillResult<>(false, "未注册或未登录");
		}
		//SeckillResult<SeckillExecution>  result = null;
		try {
			SeckillExecution  execution = seckillService.executeSeckill(seckillId, userPhone, md5);
			return new SeckillResult<SeckillExecution>(true, execution);
		} catch (RepeatSeckillException e) {
			SeckillExecution execution = new SeckillExecution(seckillId, SeckillStateEnum.REPEAT_KILL);
			return  new SeckillResult<SeckillExecution>(true, execution);
		} catch (SeckillCloseException e) {
			SeckillExecution execution = new SeckillExecution(seckillId, SeckillStateEnum.END);
			return new SeckillResult<SeckillExecution>(true, execution);
		} catch (Exception e) {
			SeckillExecution execution = new SeckillExecution(seckillId, SeckillStateEnum.INNER_ERROR);
			return new SeckillResult<SeckillExecution>(true, execution);
		}
	}
	
	@RequestMapping(value ="/time/now", method = RequestMethod.GET,
			produces = {"application/json; charset=utf-8"})
	@ResponseBody
	public SeckillResult<Long> time(){
		Date now = new Date();
		System.out.println("-------------------now:"+now.toString());
		return new SeckillResult<Long>(true, now.getTime());
	}
	
}









