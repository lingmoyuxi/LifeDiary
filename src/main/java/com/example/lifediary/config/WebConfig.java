package com.example.lifediary.config;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.io.File;

/**
 * 描述:WebConfig
 * 日期: 2023/2/21 15:45:25
 *
 * @author MoYuXi
 */
@Configuration
@Slf4j
public class WebConfig extends WebMvcConfigurationSupport {
    @Override
    protected void configurePathMatch(@NotNull PathMatchConfigurer configurer) {
        super.configurePathMatch(configurer);
        //url忽略大小写
        AntPathMatcher matcher = new AntPathMatcher();
        matcher.setCaseSensitive(false);
        configurer.setPathMatcher(matcher);
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        //资源文件路径配置
        String property = System.getProperty("user.dir");
        File file = new File(property, "static/");
        String path = "file:" + file + File.separator;
        log.info("File upload to Path ------>" + path);
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:META-INF/resources/",
                        "classpath:/resources/",
                        "classpath:/static/",
                        "classpath:/public/",
                        path);
    }
}
