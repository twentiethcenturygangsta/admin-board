package com.github.twentiethcenturygangsta.adminboard.annotation.registrar;

import com.github.twentiethcenturygangsta.adminboard.annotation.EnableAdminBoard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class AdminBoardRegistrar implements ImportBeanDefinitionRegistrar {

    private static final String ADMIN_BOARD_PACKAGE_NAME = "com.github.twentiethcenturygangsta.adminboard";
    private static List<String> packages = new ArrayList<>();

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes attributes = getAttributes(importingClassMetadata);
        String[] basePackages = attributes.getStringArray("basePackages");

        if (basePackages.length == 0) {
            packages.add(ClassUtils.getPackageName(importingClassMetadata.getClassName()));
        } else {
            packages = Arrays.asList(basePackages);
        }
        packages.add(ADMIN_BOARD_PACKAGE_NAME);
    }

    public static List<String> getBasePackages() {
        return packages;
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
