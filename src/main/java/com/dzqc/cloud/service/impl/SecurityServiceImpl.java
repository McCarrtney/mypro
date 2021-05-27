package com.dzqc.cloud.service.impl;

import com.dzqc.cloud.dao.UserinfoMapper;
import com.dzqc.cloud.dto.SecurityUser;
import com.dzqc.cloud.entity.Userinfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class SecurityServiceImpl implements UserDetailsService {

    @Autowired
    //private final UserinfoMapper userMapper;
    private UserinfoMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Userinfo userinfo = userMapper.selectByPhone(userName);
        if (userinfo != null) {
            return new SecurityUser(userName, userinfo.getPassword(), new ArrayList<>(), 1);
        } else {
            throw new UsernameNotFoundException(String.format("%s 该账号不存在", userName));
        }
    }
}