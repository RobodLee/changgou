package com.robod;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Robod
 * @date 2020/7/2 0:01
 */
@SpringBootApplication
@EnableEurekaClient //开启Eureka客户端
@MapperScan("com.robod.mapper.org_mapper")
@tk.mybatis.spring.annotation.MapperScan("com.robod.mapper.tk_mapper")
@EnableTransactionManagement
public class GoodsApplication {
    public static void main(String[] args) {
        SpringApplication.run(GoodsApplication.class, args);
    }
}
