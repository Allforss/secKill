package com.sukidesu.seckill.base.service;

import com.github.pagehelper.Page;
import com.sukidesu.seckill.base.domain.Log;

/**
 * @author xudong.cheng
 * @date 2018/1/22 下午4:32
 */
public interface LogService {

    Page<Log> findPage(int offset, int limit, Log log);

}
