package com.sukidesu.seckill.base.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sukidesu.seckill.base.domain.Log;
import com.sukidesu.seckill.base.mapper.LogMapper;
import com.sukidesu.seckill.base.service.LogService;
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
