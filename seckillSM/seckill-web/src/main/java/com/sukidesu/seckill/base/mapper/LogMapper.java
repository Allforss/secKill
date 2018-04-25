package com.sukidesu.seckill.base.mapper;


import com.sukidesu.seckill.base.domain.Log;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 系统日志 Mapper接口
 */
@Mapper
@Component("logMapper")
public interface LogMapper  {

    int insert(@Param("domain") Log domain);

    int insertList(@Param("domains") List<Log> domains);

    List<Log> select(@Param("domain") Log domain);

    int update(@Param("domain") Log domain);

    List<Log> selectList(@Param("domain") Log domain);

}