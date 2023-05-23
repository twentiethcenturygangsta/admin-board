package com.github.twentiethcenturygangsta.adminboard.utils;

public class AdminBoardStringConvertUtil {
    private final static String WHITE_SPACE = " ";

    private AdminBoardStringConvertUtil() {}

    public static String getFormattedTableName(String entityName) {
        StringBuilder convertedEntityName = new StringBuilder();
        for(int i = 0; i<entityName.length(); i++) {
            if(i == 0) {
                convertedEntityName.append(Character.toUpperCase(entityName.charAt(0)));
            } else {
                if(Character.isUpperCase(entityName.charAt(i))) {
                    convertedEntityName.append(WHITE_SPACE);
                    convertedEntityName.append(entityName.charAt(i));
                } else {
                    convertedEntityName.append(Character.toUpperCase(entityName.charAt(i)));
                }
            }
        }
        return convertedEntityName.toString();
    }

    public static String getFormattedColumnName(String fieldName) {
        StringBuilder convertedFieldName = new StringBuilder();
        for(int i = 0; i<fieldName.length(); i++) {
            if (Character.isUpperCase(fieldName.charAt(i))) {
                convertedFieldName.append(WHITE_SPACE);
            }
            convertedFieldName.append(fieldName.charAt(i));
        }
        return convertedFieldName.toString();
    }
}
