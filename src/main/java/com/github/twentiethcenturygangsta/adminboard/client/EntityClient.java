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
    private Set<Class<?>> entityClasses;
    private Map<String, Class<?>> enumClasses = new HashMap<>();

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
        this.entityClasses = entityClasses;
        this.entities = entities;
    }

    public void registerEnumClasses() {
        Map<String, Class<?>> enumClasses = new HashMap<>();
        Set<Class<?>> classes = new HashSet<>();
        List<String> basePackages = AdminBoardRegistrar.getBasePackages();
        for (String basePackage : basePackages) {
            classes.addAll(new Reflections(basePackage).getSubTypesOf(Enum.class));
        }
        for (Class<?> enumClass : classes) {
            enumClasses.put(enumClass.getSimpleName(), enumClass);
        }
        this.enumClasses = enumClasses;
    }

    public List<EntityInfo> getEntities() {
        return entities;
    }

    public Map<String, Class<?>> getEnumClasses() {
        return enumClasses;
    }
    public Set<Class<?>> getEntityClasses() {
        return entityClasses;
    }

    public EntityInfo getEntity(String entityName) {
        return entities.stream()
                .filter(o -> o.getName().equals(entityName))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Not exist matched entity"));
    }

    public Class<?> getEntityClass(String entityName) {
        return entityClasses.stream()
                .filter(o -> o.getSimpleName().equals(entityName))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Not exist matched entityClass"));
    }
}
