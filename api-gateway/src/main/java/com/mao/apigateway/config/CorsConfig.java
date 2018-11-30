package com.mao.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * 跨域配置
 * cors  c - cross  o - origin    r - resource   s - sharing  跨域资源共享
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter(){
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);//是否支持cookie跨域
        config.setAllowedOrigins(Arrays.asList("*")); // 原始域  *代表所有
        config.setAllowedMethods(Arrays.asList("*")); // 允许的请求类型 *代表全部允许
        config.setAllowedHeaders(Arrays.asList("*")); // 允许的头 *代表全部允许
        config.setMaxAge(300l); //缓存时间
        source.registerCorsConfiguration("/**",config);
        return new CorsFilter(source);
    }
}
