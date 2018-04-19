package com.alice.pet.base.depend.model;

import lombok.Data;

/**
 * 统一认证 response model
 *
 * @author xudong.cheng
 * @date 2018/1/16 下午6:12
 */
@Data
public class SsoModel {

    private int ret;

    private String msg;

    private SsoResponse data;

    @Data
    public class SsoResponse {
        /**
         * 账号
         */
        private String userid;
        /**
         * 密码
         */
        private String passwd;
        /**
         * 验证码
         */
        private String code;
        /**
         * token
         */
        private String stoken;
        /**
         * 时效
         */
        private int expire;

    }
}
