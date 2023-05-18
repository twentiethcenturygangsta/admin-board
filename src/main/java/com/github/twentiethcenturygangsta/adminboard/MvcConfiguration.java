package com.github.twentiethcenturygangsta.adminboard;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class MvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/adminboard-resources/**")
                .addResourceLocations(
                        "classpath:/static/adminboard-resources/css",
                        "classpath:/static/adminboard-resources/js",
                        "classpath:/static/adminboard-resources/fonts",
                        "classpath:/static/adminboard-resources/assets",
                        "classpath:/static/adminboard-resources/img",
                        "classpath:/static/adminboard-resources/sass"
                );
    }
}
