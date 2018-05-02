package com.sukidesu.common.common.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

/**
 * @author weixian.yan
 * @created on 14:52 2018/5/2
 * @description:
 */
@Component
public class IdGenerator {

    private static final int RANDOM_LENGTH = 5;

    public String generateOrderId(){
        return System.currentTimeMillis() + RandomStringUtils.randomNumeric(RANDOM_LENGTH);
    }
}
