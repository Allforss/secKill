package com.alice.pet.base.web;

import com.github.pagehelper.Page;
import com.alice.pet.base.common.annotation.Log;
import com.alice.pet.base.common.page.PageList;
import com.alice.pet.base.domain.Dict;
import com.alice.pet.base.model.DictModel;
import com.alice.pet.base.model.MessageBean;
import com.alice.pet.base.service.DictService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;

/**
 * @author xudong.cheng
 * @date 2018/1/22 上午10:32
 */
@Controller
@RequestMapping("sys/dict")
public class DictController extends BaseController {

    @Autowired
    private DictService dictService;

    @Log("数据字典页面")
    @RequiresPermissions("sys:dict:list")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String toList() {
        return "sys/dict/list";
    }

    @Log("数据字典列表")
    @ResponseBody
    @RequiresPermissions("sys:dict:list")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public MessageBean list(int offset, int limit, Dict dict) {
        return this.process(() -> {
            Page<Dict> page = dictService.findPage(offset, limit, dict);
            return new PageList(page.getTotal(), page.getResult().stream().map(DictModel::new).collect(Collectors.toList()));
        });
    }

    @Log("数据字典类型")
    @ResponseBody
    @RequiresPermissions("sys:dict:list")
    @RequestMapping(value = "type", method = RequestMethod.GET)
    public MessageBean findType() {
        return this.process(() -> dictService.findAllType().stream().map(DictModel::new).collect(Collectors.toList()));
    }

}
