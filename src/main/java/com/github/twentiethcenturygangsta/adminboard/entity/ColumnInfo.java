package com.github.twentiethcenturygangsta.adminboard.entity;

import com.github.twentiethcenturygangsta.adminboard.annotation.AdminBoardColumn;
import com.github.twentiethcenturygangsta.adminboard.enums.DatabaseRelationType;
import com.github.twentiethcenturygangsta.adminboard.utils.AdminBoardStringConvertUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@Getter
public class ColumnInfo {
    private static final int DEFAULT_MAX_SIZE = 255;
    private static final String WHITE_SPACE = " ";
    private final String name;
    private final String displayName;
    private final String type;
    private final DatabaseRelationType relationType;
    private final int maxSize;
    private final boolean isId;
    private final String description;
    private final boolean isExposed;
    private final boolean isAllowedNull;
    private final boolean isAllowedBlank;

    @Builder
    public ColumnInfo(Field field) {
        this.name = field.getName();
        this.displayName = getFieldDisplayName(field);
        this.type = getFieldType(field);
        this.relationType = getFieldRelationType(field);
        this.maxSize = getFieldMaxSize(field);
        this.isId = field.isAnnotationPresent(Id.class);
        this.description = getFieldDescription(field);
        this.isExposed = getFieldIsExposed(field);
        this.isAllowedNull = field.isAnnotationPresent(NotNull.class);
        this.isAllowedBlank = field.isAnnotationPresent(NotBlank.class);
    }

    public boolean getIsId() {
        return isId;
    }

    public String getDescription() {
        return description;
    }

    private String getFieldType(Field field) {
        if (field.isAnnotationPresent(OneToMany.class) || field.isAnnotationPresent(ManyToMany.class)) {
            return getListItemType(field);
        }
        return field.getType().getSimpleName();
    }

    private String getListItemType(Field field) {
        Type genericType = field.getGenericType();
        if (genericType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            Type[] typeArguments = parameterizedType.getActualTypeArguments();
            if (typeArguments.length > 0) {
                Type typeArgument = typeArguments[0];
                if (typeArgument instanceof Class<?> classObject) {
                    return classObject.getSimpleName();
                }
            }
        }
        return null;
    }

    private String getFieldDisplayName(Field field) {
        return AdminBoardStringConvertUtil.getFormattedColumnName(field.getName());
    }

    private DatabaseRelationType getFieldRelationType(Field field) {
        if(field.isAnnotationPresent(OneToOne.class)) {
            return DatabaseRelationType.ONE_TO_ONE;
        }
        if(field.isAnnotationPresent(OneToMany.class)) {
            return DatabaseRelationType.ONE_TO_MANY;
        }
        if(field.isAnnotationPresent(ManyToOne.class)) {
            return DatabaseRelationType.MANY_TO_ONE;
        }
        if(field.isAnnotationPresent(ManyToMany.class)) {
            return DatabaseRelationType.MANY_TO_MANY;
        }
        return DatabaseRelationType.NON_RELATIONSHIP;
    }

    private String getFieldDescription(Field field) {
        if(field.isAnnotationPresent(AdminBoardColumn.class)) {
            return field.getAnnotation(AdminBoardColumn.class).description();
        }
        return "";
    }
    private boolean getFieldIsExposed(Field field) {
        if(field.isAnnotationPresent(AdminBoardColumn.class)) {
            return field.getAnnotation(AdminBoardColumn.class).isExposed();
        }
        return true;
    }

    private int getFieldMaxSize(Field field) {
        if(field.isAnnotationPresent(Column.class)) {
            return field.getAnnotation(Column.class).length();
        }
        if (field.isAnnotationPresent(Size.class)) {
            return field.getAnnotation(Size.class).max();
        }
        return DEFAULT_MAX_SIZE;
    }
}
