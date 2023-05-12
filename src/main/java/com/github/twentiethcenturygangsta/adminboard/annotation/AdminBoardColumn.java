package com.github.twentiethcenturygangsta.adminboard.annotation;

import java.lang.annotation.*;

/**
 * Annotation used at the field parameter label
 * Set the configuration for the field(table column) of the entity
 *
 * @author oereo
 * @version 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface AdminBoardColumn {
    /**
     * Set the description of field(table column)
     *
     * @return  field(table column) description
     *
     */
    String description() default "";

    /**
     * Set the isExposed of field(table column)
     *
     * @return  field(table column) isExposed
     *
     */
    boolean isExposed() default true;

}
