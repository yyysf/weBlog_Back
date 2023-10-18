package com.yang.hdyplm.service;

import com.yang.hdyplm.pojo.User;
import com.yang.hdyplm.result.Result;

public interface UserService {
    Result login(User user);
    Result getInfo(String username);
}
