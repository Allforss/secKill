package com.alice.pet.base.service;

import com.github.pagehelper.Page;
import com.alice.pet.base.domain.Log;

/**
 * @author xudong.cheng
 * @date 2018/1/22 下午4:32
 */
public interface LogService {

    Page<Log> findPage(int offset, int limit, Log log);

}
