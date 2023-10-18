package com.yang.hdyplm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableScheduling
@EnableTransactionManagement //开启注解方式的事务管理
@SpringBootApplication
public class WeBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeBlogApplication.class, args);
    }

}
