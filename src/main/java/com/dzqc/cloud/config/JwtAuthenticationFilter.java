package com.dzqc.cloud.config;

import com.dzqc.cloud.service.impl.SecurityServiceImpl;
import com.dzqc.cloud.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private SecurityServiceImpl securityService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    private static final String JWT_PREFIX = "JWTTOKEN ";
    private static final String AUTHORIZATION_HEAD = "Authorization";


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHead = request.getHeader(AUTHORIZATION_HEAD);
        if (authorizationHead != null && authorizationHead.startsWith(JWT_PREFIX)) {
            String token = authorizationHead.substring(JWT_PREFIX.length());
            String userName = jwtTokenUtil.getUsernameFromToken(token);
            // 用户名不等于空 并且未认证过 进行登录验证
            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = securityService.loadUserByUsername(userName);
                // 验证token
                if (jwtTokenUtil.validateToken(token, userDetails)) {
                    // 验证通过 构建Secruity登录对象
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
