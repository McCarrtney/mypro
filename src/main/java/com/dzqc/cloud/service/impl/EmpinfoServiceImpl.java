package com.dzqc.cloud.service.impl;

import com.dzqc.cloud.dao.EmpinfoMapper;
import com.dzqc.cloud.entity.Empinfo;
import com.dzqc.cloud.service.EmpinfoService;
import com.dzqc.cloud.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpinfoServiceImpl implements EmpinfoService {
    @Autowired
    private EmpinfoMapper empinfoMapper;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public List<Empinfo> findByDid(Integer did) {
        return empinfoMapper.selectByDid(did);
    }

    @Override
    public Integer insertEmp(Empinfo empinfo) {
        return empinfoMapper.insert(empinfo);
    }

    @Override
    public Empinfo selectByPhone(String phone) {
        return empinfoMapper.selectByPhone(phone);
    }

    @Override
    public String login(String phone, String password) {
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
    public List<Empinfo> selectAll() {
        return empinfoMapper.selectAll();
    }

    @Override
    public Empinfo selectByID(Integer id) {
        return empinfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer updateEmpInfo(Empinfo empinfo) {
        return empinfoMapper.updateByPrimaryKeySelective(empinfo);
    }


}
