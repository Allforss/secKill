package com.alice.pet.base.depend.client;

import com.alice.pet.base.common.exception.BizException;
import com.alice.pet.base.depend.model.SsoModel;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author xudong.cheng
 * @date 2018/1/16 下午5:37
 */
@FeignClient(url = "${sso.server.url}", name = "ssoClient")
public interface SsoClient {

    /**
     * 发送验证码
     *
     * @param userid 账号
     * @return
     */
    @RequestMapping(value = "/user/apiCode", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    SsoModel sendCode(@RequestParam("userid") String userid);

    /**
     * 登录
     *
     * @param userid 账号
     * @param passwd 密码
     * @param code   验证码
     * @param domain 调用方域名
     * @return
     */
    @RequestMapping(value = "/user/ssoLogin", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    SsoModel login(@RequestParam("userid") String userid, @RequestParam("passwd") String passwd, @RequestParam("code") String code, @RequestParam("domain") String domain);

    /**
     * 检验登录态
     *
     * @param userid 账号
     * @param stoken token
     * @param domain 调用方域名
     * @return
     */
    @RequestMapping(value = "/user/ssoCheck", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    SsoModel check(@RequestParam("userid") String userid, @RequestParam("stoken") String stoken, @RequestParam("domain") String domain);

    /**
     * 登出
     *
     * @param userid 账号
     * @param stoken token
     * @param domain 调用方域名
     * @return
     */
    @RequestMapping(value = "/user/ssoLogout", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    SsoModel logout(@RequestParam("userid") String userid, @RequestParam("stoken") String stoken, @RequestParam("domain") String domain);


    default SsoModel verifty(SsoModel model) {
        if (model.getRet() == 0) {
            return model;
        }
        throw new BizException("88888", model.getMsg());
    }
}
