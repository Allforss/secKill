package com.alice.pet.base.shiro;

import com.alice.pet.base.enums.LoginUserTypeEnum;
import lombok.Data;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author xudong.cheng
 * @date 2018/1/16 下午4:41
 */
@Data
public class AuthcToken extends UsernamePasswordToken {

    /**
     * 登录类型
     */
    private LoginUserTypeEnum type = LoginUserTypeEnum.sso;
    /**
     * 验证码
     */
    private String captcha;

    public AuthcToken(String username, String password, String captcha, LoginUserTypeEnum type) {
        super(username, password.toCharArray());
        this.captcha = captcha;
        this.type = type;
    }

}
