package com.alice.pet.base.enums;

/**
 * 登录人员类型枚举
 *
 * @author xudong.cheng
 * @date 2018/1/16 下午4:40
 */
public enum LoginUserTypeEnum {

    /**
     * 后台管理人员
     */
    manager(1),
    /**
     * 统一登录人员
     */
    sso(2);

    public int type;

    LoginUserTypeEnum(int type) {
        this.type = type;
    }

}
