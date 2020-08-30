package com.robod.order;

import com.robod.entity.FeignHeaderInterceptor;
import com.robod.entity.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author Robod
 * @date 2020/8/22 9:39
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = {"com.robod.order.mapper"})
@EnableFeignClients({"com.robod.goods.feign","com.robod.user.feign"})
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
    }

    @Bean
    public FeignHeaderInterceptor feignHeaderInterceptor() {
        return new FeignHeaderInterceptor();
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker(0,0);
    }

}
