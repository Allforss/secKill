package com.sukidesu.seckill.base.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sukidesu.seckill.base.domain.Dict;
import com.sukidesu.seckill.base.mapper.DictMapper;
import com.sukidesu.seckill.base.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author xudong.cheng
 * @date 2018/1/22 上午10:34
 */
@Service
public class DictServiceImpl implements DictService {

    @Autowired
    private DictMapper dictMapper;

    @Override
    public Page<Dict> findPage(int offset, int limit, Dict dict) {
        return PageHelper.offsetPage(offset, limit)
                .setOrderBy("FstrType ASC, FstrValue ASC")
                .doSelectPage(() -> dictMapper.select(dict));
    }

    @Override
    public List<Dict> findAllType() {
        List<Dict> allType = dictMapper.findAllType();
        if (CollectionUtils.isEmpty(allType)) {
            return Collections.emptyList();
        }
        return allType;
    }
}
