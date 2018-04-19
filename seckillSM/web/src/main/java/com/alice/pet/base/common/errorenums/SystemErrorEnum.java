package com.alice.pet.base.common.errorenums;

/**
 * @author xudong.cheng
 * @date 2018/1/17 下午1:42
 */
public enum SystemErrorEnum {

    /**
     * 系统错误
     */
    SYSTEM_ERROR("99999", "系统错误"),
    AUTHC_ERROR("00001", "认证失败"),
    UNION_AUTHC_ERROR("00002", "统一认证失败");

    public String code;
    public String desc;

    SystemErrorEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
