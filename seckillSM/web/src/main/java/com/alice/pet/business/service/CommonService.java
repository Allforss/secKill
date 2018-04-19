package com.alice.pet.business.service;

import com.github.pagehelper.Page;

public interface CommonService<T> {

    Boolean update(T info);

    Boolean add(T info);

    Page<T> searchAll(int offset, int limit);

    Boolean delete(Integer id);

    Boolean deleteBatch(Integer[] ids);

    T searchById(Integer id);

}
