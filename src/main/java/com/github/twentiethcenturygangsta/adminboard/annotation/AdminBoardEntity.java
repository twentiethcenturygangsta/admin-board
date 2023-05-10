package com.github.twentiethcenturygangsta.adminboard.annotation;

import com.github.twentiethcenturygangsta.adminboard.annotation.registrar.AdminBoardRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface AdminBoardEntity {

    /**
     * The highest group that can group entities in the dashboard,
     * and can designate the group to which the entity belongs.
     *
     * @return  Name of the group to specify
     *
     */
    String group() default "";

    /**
     * Set the description of the entity
     *
     * @return  description of the entity
     *
     */
    String description() default "";
}