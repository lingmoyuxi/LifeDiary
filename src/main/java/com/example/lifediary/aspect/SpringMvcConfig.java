package com.example.lifediary.aspect;

import com.example.lifediary.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * 描述:拦截器
 * 日期: 2023/2/5 17:43:02
 *
 * @author MoYuXi
 */
@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {
    @Resource
    LoginInterceptor loginInterceptor;

    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/error",
                        "/user/login",
                        "/user/registry",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/swagger-resources/**",
                        "/doc/**",
                        "/doc.html",
                        "/images/**",
                        "/webjars/**",
                        "/api/v2/api-docs",
                        "/configuration/ui",
                        "/configuration/security"
                );
    }
}
