package com.alice.pet.business.mapper;


import com.alice.pet.business.domain.PetOwner;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PetOwnerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PetOwner record);

    int insertSelective(PetOwner record);

    PetOwner selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PetOwner record);

    int updateByPrimaryKey(PetOwner record);

    List<PetOwner> selectAll();

    int deleteBatch(Integer[] ids);
}