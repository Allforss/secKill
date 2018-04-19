package com.alice.pet.business.web;

import com.alibaba.fastjson.JSON;
import com.alice.pet.base.common.page.PageList;
import com.alice.pet.base.model.MessageBean;
import com.alice.pet.base.web.BaseController;
import com.alice.pet.business.domain.PetInfo;
import com.alice.pet.business.service.PetInfoService;
import com.github.pagehelper.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/petInfo")
@Slf4j
public class PetInfoController extends BaseController {

    @Resource
    private PetInfoService service;

    @GetMapping
    public String page() {
        return "business/petInfo/list";
    }

    @GetMapping("/list")
    @ResponseBody
    public MessageBean list(int offset, int limit) {
        log.info("入参:{},出参:{}", offset, limit);
        return this.process(() -> {
            Page<PetInfo> petInfos = service.searchAll(offset, limit);
            return new PageList(petInfos.getTotal(), petInfos.getResult().stream().collect(Collectors.toList()));
        });
    }

    @GetMapping("/add")
    public String add() {
        return "business/petInfo/add";
    }

    @PostMapping("/add")
    @ResponseBody
    public MessageBean add(PetInfo petInfo) {
        log.info("入参：{}", JSON.toJSON(petInfo));
        return this.process(() -> service.add(petInfo));
    }

    @PostMapping("/remove")
    @ResponseBody
    public MessageBean remove(Integer id) {
        log.info("入参：{}", id);
        return this.process(() -> service.delete(id));
    }

    @PostMapping("/batchRemove")
    @ResponseBody
    public MessageBean batchRemove(@RequestBody Integer[] ids){
        log.info("入参：{}",JSON.toJSON(ids));
        return this.process(()->service.deleteBatch(ids));
    }

    @GetMapping("/edit/{id}")
    public String toEdit(@PathVariable("id") Integer id,Model model){
        PetInfo petInfo = service.searchById(id);
        model.addAttribute("pet",petInfo);
        return "business/petInfo/edit";
    }

    @PostMapping("/update")
    @ResponseBody
    public MessageBean edit(PetInfo petInfo){
        return this.process(()->service.update(petInfo));
    }

}
