package com.yang.hdyplm.mapper;

import com.yang.hdyplm.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserMapper {
    @Select("select * from user where username = #{username} and password = #{password}")
    User getByNameAndPass(String username,String password);

    @Select("select * from user where username = #{username}")
    User getByName(String username);

    @Select("select * from user where id = #{id}")
    User getById(Integer id);
    @Select("select * from user")
    List<User> getUserList();
}
