package com.github.twentiethcenturygangsta.adminboard.annotation;

import com.github.twentiethcenturygangsta.adminboard.annotation.registrar.AdminBoardRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(AdminBoardRegistrar.class)
public @interface EnableAdminBoard {
    String[] basePackages() default {};
}
