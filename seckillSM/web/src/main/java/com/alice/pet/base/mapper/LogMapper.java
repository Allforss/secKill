package com.alice.pet.base.mapper;

import com.alice.pet.base.domain.Log;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统日志 Mapper接口
 */
@Mapper
public interface LogMapper  {

    int insert(@Param("domain") Log domain);

    int insertList(@Param("domains") List<Log> domains);

    List<Log> select(@Param("domain") Log domain);

    int update(@Param("domain") Log domain);

    List<Log> selectList(@Param("domain") Log domain);

}