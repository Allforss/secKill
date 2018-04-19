package com.alice.pet.business.mapper;


import com.alice.pet.business.domain.PetInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PetInfoMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(PetInfo record);

    int insertSelective(PetInfo record);

    PetInfo selectByPrimaryKey(Integer id);

    List<PetInfo> selectAll();

    int updateByPrimaryKeySelective(PetInfo record);

    int updateByPrimaryKey(PetInfo record);

    int deleteBatch(Integer[] ids);
}