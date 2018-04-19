package com.alice.pet.business.service.lmpl;

import com.alice.pet.business.domain.PetOwner;
import com.alice.pet.business.mapper.PetOwnerMapper;
import com.alice.pet.business.service.PetOwnerService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("PetOwnerService")
@Slf4j
public class PetOwnerServicelmpl implements PetOwnerService {

    @Resource
    private PetOwnerMapper petOwnerMapper;

    @Override
    public Boolean update(PetOwner info) {
        return 0!=petOwnerMapper.updateByPrimaryKeySelective(info);
    }

    @Override
    public Boolean add(PetOwner info) {
        return 0!=petOwnerMapper.insertSelective(info);
    }

    @Override
    public Page<PetOwner> searchAll(int offset, int limit) {
        log.info("入参:{},出参:{}", offset, limit);
        return PageHelper.offsetPage(offset, limit)
                .doSelectPage(() -> petOwnerMapper.selectAll());
    }

    @Override
    public Boolean delete(Integer id) {
        return 0!=petOwnerMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Boolean deleteBatch(Integer[] ids) {
        return 0!=petOwnerMapper.deleteBatch(ids);
    }

    @Override
    public PetOwner searchById(Integer id) {
        return petOwnerMapper.selectByPrimaryKey(id);
    }
}
