package com.sukidesu.seckill.base.mapper;

import com.sukidesu.seckill.base.domain.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单管理 Mapper接口
 */
@Mapper
public interface MenuMapper  {

    int insert(@Param("domain") Menu domain);

    int insertList(@Param("domains") List<Menu> domains);

    List<Menu> select(@Param("domain") Menu domain);

    List<Menu> selectWithSort(@Param("domain") Menu domain);

    int update(@Param("domain") Menu domain);

    List<Menu> findMenuByUserId(@Param("userId") String userId);

    List<Menu> findMenuForAdmin();

    List<String> findPermCodesByUserId(@Param("userId") String userId);

    int removeMenuById(@Param("id") Long id);

}