package com.alice.pet.base.utils;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * 密码生成工具类
 *
 * @author Bruce
 */
public class PasswordUtil {

    public static final String HASH_ALGORITHM_NAME = "md5";
    public static final String SALT = "c{7dFaGjX0P-a0SO#m&H";
    public static final ByteSource SALT_BYTE_SOURCE = ByteSource.Util.bytes(SALT);
    public static final int HASH_ITERATIONS = 1;

    public static String createUserPwd(String password) {
        return new SimpleHash(HASH_ALGORITHM_NAME, password, SALT_BYTE_SOURCE, HASH_ITERATIONS).toHex();
    }

    public static void main(String[] args) {
        System.out.println(RandomStringUtils.randomAlphanumeric(20));
        System.out.println(RandomStringUtils.randomAlphabetic(20));
        System.out.println(RandomStringUtils.randomAscii(20));
        System.out.println(RandomStringUtils.randomNumeric(20));
        System.out.println(createUserPwd("123"));
    }

}
