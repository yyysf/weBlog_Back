package com.yang.hdyplm.controller;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.yang.hdyplm.context.BaseContext;
import com.yang.hdyplm.dto.UserLoginDTO;
import com.yang.hdyplm.mapper.UserMapper;
import com.yang.hdyplm.pojo.LoginDetails;
import com.yang.hdyplm.pojo.User;
import com.yang.hdyplm.result.Result;
import com.yang.hdyplm.service.UserService;
import com.yang.hdyplm.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/hdyplm/user")
public class UserController{

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisCache redisCache;

    @PostMapping("/login")
    public Result login(@RequestBody User user){
        Result login = userService.login(user);
        return login;
    }

   @GetMapping("/info")
    public Result info(){
       Long currentId = BaseContext.getCurrentId();
       User user = userMapper.getById(Integer.valueOf(String.valueOf(currentId)));
       log.info("获取用户信息");
        Map data=new HashMap<>();
        data.put("user",user);
        return Result.success(data);
    }

    @PostMapping("/logout")
    public Result logout(){
        log.info("用户退出");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginDetails user = (LoginDetails) authentication.getPrincipal();
        redisCache.deleteObject("uid:"+user.getUser().getId());
        return Result.success();
    }

    @GetMapping ( "/userList")
    public Result searchUser(){
        log.info("查询用户信息");
        List<User> userList = userMapper.getUserList();
        Map data=new HashMap();
        data.put("rows",userList);
        return Result.success(data);
    }


}
