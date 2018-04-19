package com.alice.pet.base.mapper;

import com.alice.pet.base.domain.Role;
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