package com.robod;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Robod
 * @date 2020/7/31 10:09
 */
@ControllerAdvice
@Configuration
public class EnableMvcConfig implements WebMvcConfigurer {

    /***
     * 静态资源放行
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/items/**").addResourceLocations("classpath:/templates/items/");
    }
}
