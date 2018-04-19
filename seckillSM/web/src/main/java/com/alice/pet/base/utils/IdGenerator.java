package com.alice.pet.base.utils;

import org.apache.commons.lang.RandomStringUtils;

/**
 * @author xudong.cheng
 * @date 2018/1/22 下午7:28
 */
public class IdGenerator {

    private IdGenerator() {
    }

    public static String id() {
        return System.currentTimeMillis() + RandomStringUtils.randomNumeric(7);
    }

    public static void main(String[] args) {
        System.out.println(id());
        System.out.println(id());
    }
}
