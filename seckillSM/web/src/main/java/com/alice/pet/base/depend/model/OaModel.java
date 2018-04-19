package com.alice.pet.base.depend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xudong.cheng
 * @date 2018/2/28 下午1:36
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OaModel {

    private int ret;

    private String msg;

    private OaResponse data;

    @Data
    public class OaResponse {
        /**
         * 0:在职, 1:离职
         */
        private int status;

        private String name;
        private String mobile;
        private String password;
        private String email;

    }

}
