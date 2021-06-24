package com.dzqc.cloud.service.impl;

import com.dzqc.cloud.dao.UserinfoMapper;
import com.dzqc.cloud.entity.Userinfo;
import com.dzqc.cloud.service.UserService;
import com.dzqc.cloud.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserinfoMapper userinfoMapper;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public Userinfo selectByPhone(String phone) {
        return userinfoMapper.selectByPhone(phone);
    }
    //2021/5/22 wxr
    @Override
    public int insertUser(Userinfo record){
        return userinfoMapper.insert(record);
    }

    @Override
    public String login(String phone, String password){
        // 创建Spring Security登录token
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(phone, password);
        // 委托Spring Security认证组件执行认证过程
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        // 认证成功，设置上下文
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 生成jwt token
        return jwtTokenUtil.generateToken(phone);
    }

    @Override
    public Integer updateUserInfo(Userinfo userinfo) {
        return userinfoMapper.updateByPrimaryKeySelective(userinfo);
    }


}
