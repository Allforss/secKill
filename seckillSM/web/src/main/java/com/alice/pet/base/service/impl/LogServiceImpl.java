package com.alice.pet.base.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.alice.pet.base.domain.Log;
import com.alice.pet.base.mapper.LogMapper;
import com.alice.pet.base.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xudong.cheng
 * @date 2018/1/22 下午4:34
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;

    @Override
    public Page<Log> findPage(int offset, int limit, Log log) {
        return PageHelper.offsetPage(offset, limit)
                .setOrderBy("FstrCreateTime DESC")
                .doSelectPage(() -> logMapper.selectList(log));
    }

}
