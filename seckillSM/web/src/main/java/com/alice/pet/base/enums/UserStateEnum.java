package com.alice.pet.base.enums;

/**
 * @author xudong.cheng
 * @date 2018/1/18 下午3:46
 */
public enum UserStateEnum {

    DISABLED(0),
    ENABLE(1);

    public int state;

    UserStateEnum(int state) {
        this.state = state;
    }

}
