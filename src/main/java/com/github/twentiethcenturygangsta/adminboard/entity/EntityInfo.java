package com.github.twentiethcenturygangsta.adminboard.entity;

import com.github.twentiethcenturygangsta.adminboard.annotation.AdminBoardEntity;
import com.github.twentiethcenturygangsta.adminboard.utils.AdminBoardStringConvertUtil;
import lombok.Builder;
import lombok.Getter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class EntityInfo {
    private final static String WHITE_SPACE = " ";
    private final String name;
    private final String displayName;
    private final String group;
    private final String description;
    private final List<ColumnInfo> columns;

    @Builder
    public EntityInfo(Class<?> object) {
        this.name = object.getSimpleName();
        this.displayName = getObjectDisplayName(object);
        this.group = getObjectGroup(object);
        this.description = getObjectDescription(object);
        this.columns = getObjectColumns(object);
    }

    private String getObjectDisplayName(Class<?> object) {
        return AdminBoardStringConvertUtil.getFormattedTableName(object.getSimpleName());
    }

    private String getObjectGroup(Class<?> object) {
        return getAdminBoardEntityAnnotation(object).group();
    }

    private String getObjectDescription(Class<?> object) {
        return getAdminBoardEntityAnnotation(object).description();
    }

    private List<ColumnInfo> getObjectColumns(Class<?> object) {
        List<ColumnInfo> columns = new ArrayList<>();
        for (Field field : object.getDeclaredFields()) {
            if (isStaticField(field)) {
                ColumnInfo column = ColumnInfo.builder().field(field).build();
                columns.add(column);
            }
        }
        return columns;
    }

    private AdminBoardEntity getAdminBoardEntityAnnotation(Class<?> object) {
        List<Annotation> annotations = Arrays.asList(object.getAnnotations());
        List<AdminBoardEntity> annotationsWithAdminBoardEntity = annotations.stream().filter(ob -> ob instanceof AdminBoardEntity)
                .map(ob -> (AdminBoardEntity) ob)
                .collect(Collectors.toList());
        if (annotationsWithAdminBoardEntity.size() == 1) {
            return annotationsWithAdminBoardEntity.get(0);
        }
        throw new RuntimeException("Not AdminBoardEntity");
    }

    private boolean isStaticField(Field field) {
        return Modifier.isStatic(field.getModifiers());
    }
}
