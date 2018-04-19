package com.alice.pet.business.web;

import com.alibaba.fastjson.JSON;
import com.alice.pet.base.common.page.PageList;
import com.alice.pet.base.model.MessageBean;
import com.alice.pet.base.web.BaseController;
import com.alice.pet.business.domain.PetOwner;
import com.alice.pet.business.service.PetOwnerService;
import com.github.pagehelper.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/petOwner")
@Slf4j
public class PetOwnerController extends BaseController {

    @Resource
    private PetOwnerService service;

    @GetMapping
    public String page() {
        return "business/petOwner/list";
    }

    @GetMapping("/list")
    @ResponseBody
    public MessageBean list(int offset, int limit) {
        log.info("入参:{},出参:{}", offset, limit);
        return this.process(() -> {
            Page<PetOwner> petOwners = service.searchAll(offset, limit);
            return new PageList(petOwners.getTotal(), petOwners.getResult().stream().collect(Collectors.toList()));
        });
    }

    @GetMapping("/add")
    public String add() {
        return "business/petOwner/add";
    }

    @PostMapping("/add")
    @ResponseBody
    public MessageBean add(PetOwner petOwner) {
        log.info("入参：{}", JSON.toJSON(petOwner));
        return this.process(() -> service.add(petOwner));
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
        PetOwner petOwner = service.searchById(id);
        model.addAttribute("owner",petOwner);
        return "business/petOwner/edit";
    }

    @PostMapping("/update")
    @ResponseBody
    public MessageBean edit(PetOwner petOwner){
        return this.process(()->service.update(petOwner));
    }

}
