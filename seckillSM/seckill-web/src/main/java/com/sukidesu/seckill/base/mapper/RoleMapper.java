package com.sukidesu.seckill.base.mapper;

import com.sukidesu.seckill.base.domain.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色表 Mapper接口
 */
@Mapper
public interface RoleMapper  {

    int insert(@Param("domain") Role domain);

    int insertList(@Param("domains") List<Role> domains);

    List<Role> select(@Param("domain") Role domain);

    int update(@Param("domain") Role domain);

    List<Role> findByUserId(@Param("userId") long userId);

}