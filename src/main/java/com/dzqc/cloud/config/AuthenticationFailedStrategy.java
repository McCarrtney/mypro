package com.dzqc.cloud.config;

import com.dzqc.cloud.common.ResultObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AuthenticationFailedStrategy implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        //ResponseOut.out(response, ResponseCodeEnum.AUTHENTICATION_FAIL.getCode(), e.getMessage());
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        try (PrintWriter out = response.getWriter()) {
            out.println(JSON.toJSONString(ResultObject.error("未登录或登录超时，请重新登录",901)));
        } catch (Exception e0) {
            e0.printStackTrace();
        }
    }
}
