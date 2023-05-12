package com.github.twentiethcenturygangsta.adminboard.client;

import com.github.twentiethcenturygangsta.adminboard.annotation.AdminBoardEntity;
import com.github.twentiethcenturygangsta.adminboard.annotation.registrar.AdminBoardRegistrar;
import com.github.twentiethcenturygangsta.adminboard.entity.EntityInfo;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import java.util.*;

@Slf4j
public class EntityClient {

    private List<EntityInfo> entities;

    public void registerEntities() {
        List<EntityInfo> entities = new ArrayList<>();
        Set<Class<?>> entityClasses = new HashSet<>();
        List<String> basePackages = AdminBoardRegistrar.getBasePackages();

        for(String basePackage : basePackages) {
            entityClasses.addAll(new Reflections(basePackage).getTypesAnnotatedWith(AdminBoardEntity.class));
        }

        for (Class<?> entity : entityClasses) {
            entities.add(EntityInfo.builder().object(entity).build());
        }

        this.entities = entities;
    }

    public List<EntityInfo> getEntities() {
        return entities;
    }
}
