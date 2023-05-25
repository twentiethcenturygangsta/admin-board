package com.github.twentiethcenturygangsta.adminboard;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class AdminBoardWebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AdminBoardInterceptor())
                .order(1)
                .excludePathPatterns("/admin-board/login", "/admin-board/action/login")
                .addPathPatterns("/admin-board/**");
    }
}
