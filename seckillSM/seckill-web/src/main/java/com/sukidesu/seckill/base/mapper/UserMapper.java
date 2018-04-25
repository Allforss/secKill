package com.sukidesu.seckill.base.mapper;

import com.sukidesu.seckill.base.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户表 Mapper接口
 */
@Mapper
public interface UserMapper {

    int insert(@Param("domain") User domain);

    int insertList(@Param("domains") List<User> domains);

    List<User> select(@Param("domain") User domain);

    User getBasicForLogin(@Param("account") String account);

    int update(@Param("domain") User domain);

}