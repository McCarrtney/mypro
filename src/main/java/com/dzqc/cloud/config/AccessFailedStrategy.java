package com.dzqc.cloud.config;

import com.alibaba.fastjson.JSON;
import com.dzqc.cloud.common.ResultObject;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author kczhang@wisedu.com
 * @version 1.0.0
 * @since 2020-12-09
 */
@Component
public class AccessFailedStrategy implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        try (PrintWriter out = response.getWriter()) {
            out.println(JSON.toJSONString(ResultObject.error("没有访问权限",908)));
        } catch (Exception e0) {
            e0.printStackTrace();
        }
    }

}
