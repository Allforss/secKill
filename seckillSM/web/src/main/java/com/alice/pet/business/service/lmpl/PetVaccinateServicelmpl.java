package com.alice.pet.business.service.lmpl;

import com.alice.pet.business.domain.PetVaccination;
import com.alice.pet.business.mapper.PetVaccinationMapper;
import com.alice.pet.business.service.PetVaccinateService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("PetVaccinateService")
@Slf4j
public class PetVaccinateServicelmpl implements PetVaccinateService {

    @Resource
    private PetVaccinationMapper petVaccinationMapper;

    @Override
    public Boolean update(PetVaccination info) {
        return 0!=petVaccinationMapper.updateByPrimaryKeySelective(info);
    }

    @Override
    public Boolean add(PetVaccination info) {
        return 0!=petVaccinationMapper.insertSelective(info);
    }

    @Override
    public Page<PetVaccination> searchAll(int offset, int limit) {
        log.info("入参:{},出参:{}", offset, limit);
        return PageHelper.offsetPage(offset, limit)
                .doSelectPage(() -> petVaccinationMapper.selectAll());
    }

    @Override
    public Boolean delete(Integer id) {
        return 0!=petVaccinationMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Boolean deleteBatch(Integer[] ids) {
        return 0!=petVaccinationMapper.deleteBatch(ids);
    }

    @Override
    public PetVaccination searchById(Integer id) {
        return petVaccinationMapper.selectByPrimaryKey(id);
    }
}
