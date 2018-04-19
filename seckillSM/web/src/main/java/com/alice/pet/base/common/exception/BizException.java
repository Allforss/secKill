package com.alice.pet.base.common.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @author xudong.cheng
 * @date 2018/1/17 下午1:38
 */
@Data
@NoArgsConstructor
public class BizException extends RuntimeException {
    private static final long serialVersionUID = -1989644883109910125L;

    private String code;
    private String desc;

    public BizException(String code, String desc) {
        super();
        if (!Objects.isNull(code)) {
            this.code = code;
        }
        if (!Objects.isNull(desc)) {
            this.desc = desc;
        }
    }
}
