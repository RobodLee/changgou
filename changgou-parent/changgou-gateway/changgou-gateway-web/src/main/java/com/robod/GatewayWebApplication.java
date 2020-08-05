package com.robod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @author Robod
 * @date 2020/8/3 23:41
 */
@SpringBootApplication
@EnableEurekaClient
public class GatewayWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayWebApplication.class, args);
    }

    @Bean(name = "ipKeyResolver")
    public KeyResolver userKeyResolver() {
        return exchange -> {
            String ip = Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getHostName();
            return Mono.just(ip);
        };
    }
}
