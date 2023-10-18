package com.yang.hdyplm.service.impl;

import com.yang.hdyplm.constant.MessageConstant;
import com.yang.hdyplm.mapper.UserMapper;
import com.yang.hdyplm.pojo.User;
import com.yang.hdyplm.pojo.LoginDetails;
import com.yang.hdyplm.result.Result;
import com.yang.hdyplm.service.UserService;
import com.yang.hdyplm.utils.JwtUtil;
import com.yang.hdyplm.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;

    @Override
    public Result login(User user) {
        //封装登录信息并认证
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if (authenticate==null){
            return Result.error(MessageConstant.PASSWORD_ERROR);
        }
        LoginDetails userDetails=(LoginDetails) authenticate.getPrincipal();
        //创建jwt
        String uid = userDetails.getUser().getId();
        String jwt = JwtUtil.createJWT(uid);
        Map data=new HashMap();
        data.put("token",jwt);
        //存入redis
        redisCache.setCacheObject("uid:"+uid,userDetails,1, TimeUnit.HOURS);    //设置过期时间一小时
        return Result.success(data,"登陆成功");
    }

    @Override
    public Result getInfo(String username) {
        return null;
    }
}
