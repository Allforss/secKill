package com.alice.pet.base.service;

import com.github.pagehelper.Page;
import com.alice.pet.base.domain.Dict;

import java.util.List;

/**
 * @author xudong.cheng
 * @date 2018/1/22 上午10:33
 */
public interface DictService {

    Page<Dict> findPage(int offset, int limit, Dict dict);

    List<Dict> findAllType();

}
