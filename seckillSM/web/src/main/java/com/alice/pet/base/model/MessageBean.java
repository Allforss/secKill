package com.alice.pet.base.model;

import com.alice.pet.base.common.exception.BizException;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xudong.cheng
 * @date 2018/1/17 下午1:28
 */
@Data
@NoArgsConstructor
public class MessageBean implements Serializable {

    private static final long serialVersionUID = -7726066133899469398L;

    public static final int SUCC = 1;
    public static final int FAIL = 0;

    private int state = SUCC;
    private String code;
    private String msg;
    private Object data;

    public MessageBean(Object data) {
        this.data = data;
    }

    public MessageBean(BizException e) {
        this.state = FAIL;
        this.msg = e.getDesc();
        this.code = e.getCode();
    }

    public MessageBean(Exception e) {
        this.state = FAIL;
        this.msg = e.getMessage();
    }

}
