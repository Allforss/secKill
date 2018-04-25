package com.sukidesu.seckill.base.mapper;

import com.sukidesu.seckill.base.domain.RoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色与菜单对应关系 Mapper接口
 */
@Mapper
public interface RoleMenuMapper  {

    int insert(@Param("domain") RoleMenu domain);

    int insertList(@Param("domains") List<RoleMenu> domains);

    List<RoleMenu> select(@Param("domain") RoleMenu domain);

    int update(@Param("domain") RoleMenu domain);

}