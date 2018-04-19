package com.alice.pet.base.depend.client;

import com.alice.pet.base.common.exception.BizException;
import com.alice.pet.base.depend.model.OaModel;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author xudong.cheng
 * @date 2018/1/16 下午5:37
 */
@FeignClient(url = "${oa.server.url}", name = "oaClient")
public interface OaClient {

    /**
     * 发送验证码
     *
     * @param email email
     * @return
     */
    @RequestMapping(value = "/api/api-user/query", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    OaModel query(@RequestParam("email") String email);

    default OaModel verifty(OaModel model) {
        if (model.getRet() == 0) {
            return model;
        }
        throw new BizException("88888", model.getMsg());
    }

}
