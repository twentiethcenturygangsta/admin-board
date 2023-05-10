package com.github.twentiethcenturygangsta.adminboard.annotation.registrar;

import com.github.twentiethcenturygangsta.adminboard.annotation.EnableAdminBoard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

@Slf4j
public class AdminBoardRegistrar implements ImportBeanDefinitionRegistrar {

    private String[] basePackages;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes attributes = getAttributes(importingClassMetadata);
        String[] basePackages = attributes.getStringArray("basePackages");

        if (basePackages.length == 0) {
            this.basePackages = new String[]{ClassUtils.getPackageName(importingClassMetadata.getClassName())};
        } else {
            this.basePackages = basePackages;
        }
    }

    public String[] getBasePackages() {
        return basePackages;
    }

    protected Class<?> getAnnotationClass() {
        return EnableAdminBoard.class;
    }

    protected AnnotationAttributes getAttributes(AnnotationMetadata metadata) {
        String name = getAnnotationClass().getName();
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(metadata.getAnnotationAttributes(name, false));
        Assert.notNull(attributes, () -> "No auto-configuration attributes found. Is " + metadata.getClassName()
                + " annotated with " + ClassUtils.getShortName(name) + "?");
        return attributes;
    }
}
