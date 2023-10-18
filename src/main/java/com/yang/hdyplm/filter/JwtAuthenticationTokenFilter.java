package com.yang.hdyplm.filter;

import com.yang.hdyplm.constant.UserConstant;
import com.yang.hdyplm.context.BaseContext;
import com.yang.hdyplm.pojo.LoginDetails;
import com.yang.hdyplm.utils.JwtUtil;
import com.yang.hdyplm.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisCache redisCache;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)){
            //放行
            filterChain.doFilter(request,response);
            return;
        }
        //解析token
        String id;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            id= claims.getSubject();
            BaseContext.setCurrentId(Long.valueOf(id));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("token非法");
        }
        //从redis中获取用户信息
        String redisKey="uid:"+id;
        LoginDetails cacheObject = redisCache.getCacheObject(redisKey);
        if (cacheObject==null){
            throw new RuntimeException("用户未登录");
        }
        //存入securityContextHolder
        //TODO 获取权限信息，封装到Authentication中
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(cacheObject,null,null);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        //放行
        filterChain.doFilter(request,response);
    }
}
