package com.yang.hdyplm.service.impl;

import com.yang.hdyplm.mapper.UserMapper;
import com.yang.hdyplm.pojo.LoginDetails;
import com.yang.hdyplm.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.getByName(username);
        if (user==null){
            throw new RuntimeException("用户不存在");
        }

        return new LoginDetails(user);
    }
}
