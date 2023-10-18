package com.yang.hdyplm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class MyCorsConfig{
    @Bean
    public CorsFilter corsFilter(){
        CorsConfiguration corsConfiguration=new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");  //开放哪些ip、端口、域名的访问权限，星号表示开放所有域
        corsConfiguration.setAllowCredentials(true);      //是否允许发送Cookie信息
        corsConfiguration.addAllowedMethod("*");          //开放哪些Http方法，允许跨域访问
        corsConfiguration.addAllowedHeader("*");          //允许HTTP请求中的携带哪些Header信息
        //添加映射路径，“/**”表示对所有的路径实行全局跨域访问权限的设置
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);
       return new CorsFilter(urlBasedCorsConfigurationSource);
    }

}
