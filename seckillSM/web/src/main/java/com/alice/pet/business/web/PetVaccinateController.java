package com.alice.pet.business.web;

import com.alibaba.fastjson.JSON;
import com.alice.pet.base.common.page.PageList;
import com.alice.pet.base.model.MessageBean;
import com.alice.pet.base.web.BaseController;
import com.alice.pet.business.domain.PetVaccination;
import com.alice.pet.business.service.PetVaccinateService;
import com.github.pagehelper.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/petVaccinate")
@Slf4j
public class PetVaccinateController extends BaseController {

    @Resource
    private PetVaccinateService service;

    @GetMapping
    public String page() {
        return "business/petVaccination/list";
    }

    @GetMapping("/list")
    @ResponseBody
    public MessageBean list(int offset, int limit) {
        log.info("入参:{},出参:{}", offset, limit);
        return this.process(() -> {
            Page<PetVaccination> petVaccinations = service.searchAll(offset, limit);
            return new PageList(petVaccinations.getTotal(), petVaccinations.getResult().stream().collect(Collectors.toList()));
        });
    }

    @GetMapping("/add")
    public String add() {
        return "business/petVaccination/add";
    }

    @PostMapping("/add")
    @ResponseBody
    public MessageBean add(PetVaccination petVaccination) {
        log.info("入参：{}", JSON.toJSON(petVaccination));
        return this.process(() -> service.add(petVaccination));
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
        PetVaccination petVaccination = service.searchById(id);
        model.addAttribute("vaccination",petVaccination);
        return "business/petVaccination/edit";
    }

    @PostMapping("/update")
    @ResponseBody
    public MessageBean edit(PetVaccination petVaccination){
        return this.process(()->service.update(petVaccination));
    }

}
