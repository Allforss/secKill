package com.alice.pet.base.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.http.MediaType;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStreamWriter;
import java.util.zip.GZIPOutputStream;

/**
 * @author xudong.cheng
 * @date 2018/1/26 下午5:35
 */
@Slf4j
public class CustomAuthenticationFilter extends FormAuthenticationFilter {

    private static final String ERR_MSG = "{\"code\":\"00001\",\"msg\":\"无权限\",\"state\":0}";

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                return executeLogin(request, response);
            } else {
                return true;
            }
        } else {
            String accept = ((HttpServletRequest) request).getHeader("Accept");
            if (accept != null && accept.contains(MediaType.APPLICATION_JSON_VALUE)) {
                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//                httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                httpServletResponse.setHeader("Content-Type", "application/json");
                String acceptEncoding = ((HttpServletRequest) request).getHeader("Accept-Encoding");
                if (acceptEncoding != null && acceptEncoding.contains("gzip")) {
                    httpServletResponse.setHeader("Content-Encoding", "gzip");
                    GZIPOutputStream gzipOutputStream = new GZIPOutputStream(response.getOutputStream());
                    OutputStreamWriter writer = new OutputStreamWriter(gzipOutputStream);
                    writer.write(ERR_MSG);
                    writer.flush();
                    writer.close();
                } else {
                    response.getWriter().write(ERR_MSG);
                    response.getWriter().flush();
                    response.getWriter().close();
                }
            } else {
                saveRequestAndRedirectToLogin(request, response);
            }
            return false;
        }
    }

}
