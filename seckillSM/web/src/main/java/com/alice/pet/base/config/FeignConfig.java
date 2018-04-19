package com.alice.pet.base.config;

import feign.Logger;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

/**
 * @author xudong.cheng
 * @date 2018/1/16 下午5:44
 */
@Configuration
public class FeignConfig {

    @Bean
    @Primary
    @Scope(SCOPE_PROTOTYPE)
    public Encoder feignFormEncoder(){
        return new SpringFormEncoder();
    }

    @Bean
    public Logger.Level level() {
        return Logger.Level.FULL;
    }

}
