package com.sukidesu.common.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.sukidesu.common.common.Constants.Constants;
import com.sukidesu.common.common.utils.CustomJsonDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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
    @JsonFormat(pattern = Constants.DateFormat.YYYY_MM_DD_HH24_MI_SS,timezone = Constants.TimeZone.ASIA_SHANGHAI )
    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    @DateTimeFormat(pattern = Constants.DateFormat.YYYY_MM_DD_HH24_MI_SS)
    private Date startTime;

    /*秒杀结束时间*/
    @JsonFormat(pattern = Constants.DateFormat.YYYY_MM_DD_HH24_MI_SS,timezone = Constants.TimeZone.ASIA_SHANGHAI )
    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    @DateTimeFormat(pattern = Constants.DateFormat.YYYY_MM_DD_HH24_MI_SS)
    private Date endTime;

    /*创建时间*/
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DateFormat.YYYY_MM_DD_HH24_MI_SS)
    private LocalDateTime createTime;

    /*更新时间*/
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DateFormat.YYYY_MM_DD_HH24_MI_SS)
    private LocalDateTime updateTime;

    public SeckillGoods(String name){
        this.name = name;
    }

}
