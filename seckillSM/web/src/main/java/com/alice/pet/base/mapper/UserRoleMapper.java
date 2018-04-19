package com.alice.pet.base.mapper;

import com.alice.pet.base.domain.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户与角色对应关系 Mapper接口
 */
@Mapper
public interface UserRoleMapper  {

    int insert(@Param("domain") UserRole domain);

    int insertList(@Param("domains") List<UserRole> domains);

    List<UserRole> select(@Param("domain") UserRole domain);

    int update(@Param("domain") UserRole domain);

}