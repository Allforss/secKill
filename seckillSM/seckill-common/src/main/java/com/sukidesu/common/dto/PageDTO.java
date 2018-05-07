package com.sukidesu.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author weixian.yan
 * @created on 18:24 2018/5/2
 * @description: 条件查询分页DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO<T> {

    private T model;

    private int offset;

    private int limit;

    private String orderBy;
}

