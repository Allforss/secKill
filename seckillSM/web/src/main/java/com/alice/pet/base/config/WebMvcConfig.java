package com.alice.pet.base.config;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Bruce
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    /**
     * 容器添加错误处理
     *
     * @return
     */
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {

        return (container -> {
            ErrorPage error403Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/error/403");
//            // UnauthorizedException
//            ErrorPage unauthorizedExceptionPage = new ErrorPage(UnauthorizedException.class, "/error/403");
//            // AuthorizationException
            ErrorPage authorizationExceptionPage = new ErrorPage(AuthorizationException.class, "/error/403");
            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/error/404");
            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500");

            container.addErrorPages(
                    error403Page,
//                    unauthorizedExceptionPage,
                    authorizationExceptionPage,
                    error404Page,
                    error500Page);
        });
    }

}
