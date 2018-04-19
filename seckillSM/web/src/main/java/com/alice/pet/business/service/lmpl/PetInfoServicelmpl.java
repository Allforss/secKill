package com.alice.pet.business.service.lmpl;

import com.alice.pet.business.domain.PetInfo;
import com.alice.pet.business.mapper.PetInfoMapper;
import com.alice.pet.business.service.PetInfoService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("PetInfoService")
@Slf4j
public class PetInfoServicelmpl implements PetInfoService {


    @Resource
    private PetInfoMapper petInfoMapper;

    @Override
    public Boolean update(PetInfo info) {
        return 0 != petInfoMapper.updateByPrimaryKeySelective(info);
    }

    @Override
    public Boolean add(PetInfo info) {
        return 0 != petInfoMapper.insertSelective(info);
    }

    @Override
    public Page<PetInfo> searchAll(int offset, int limit) {
        log.info("入参:{},出参:{}", offset, limit);
        return PageHelper.offsetPage(offset, limit)
                .doSelectPage(() -> petInfoMapper.selectAll());
    }

    @Override
    public Boolean delete(Integer id) {
        return 0 != petInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Boolean deleteBatch(Integer[] ids) {
        return 0!=petInfoMapper.deleteBatch(ids);
    }

    @Override
    public PetInfo searchById(Integer id) {
        return petInfoMapper.selectByPrimaryKey(id);
    }
}
