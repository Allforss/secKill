package com.sukidesu.seckill.base.utils;

import org.apache.commons.lang.RandomStringUtils;

/**
 * @author weixian.yan
 * @date 2018/4/22 下午7:28
 */
public class IdGenerator {

    private IdGenerator() {
    }

    public static String id() {
        return System.currentTimeMillis() + RandomStringUtils.randomNumeric(5);
    }

    public static void main(String[] args) {
        System.out.println(id());
        System.out.println(id());
    }
}
