package com.sukidesu.seckill.base.mapper;

import com.sukidesu.seckill.base.domain.Dict;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 字典表 Mapper接口
 */
@Mapper
public interface DictMapper  {

    int insert(@Param("domain") Dict domain);

    int insertList(@Param("domains") List<Dict> domains);

    int update(@Param("domain") Dict domain);

    List<Dict> select(@Param("domain") Dict domain);

    List<Dict> findAllType();

}