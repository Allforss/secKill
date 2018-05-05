package com.sukidesu.common.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.joda.deser.LocalDateDeserializer;
import com.sukidesu.common.common.utils.CustomJsonDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author weixian.yan
 * @created on 19:20 2018/4/25
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeckillGoods {

    /*商品id*/
    private Long goodsId;

    /*商品名*/
    private String name;

    /*库存*/
    private Long number;

    /*价格，单位：分*/
    private Long price;

    /*商品描述*/
    private String description;

    /*秒杀开始时间*/
    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private Date startTime;

    /*秒杀结束时间*/
    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private Date endTime;

    /*创建时间*/
    private LocalDateTime createTime;

    /*更新时间*/
    private LocalDateTime updateTime;

    public SeckillGoods(String name){
        this.name = name;
    }

}
