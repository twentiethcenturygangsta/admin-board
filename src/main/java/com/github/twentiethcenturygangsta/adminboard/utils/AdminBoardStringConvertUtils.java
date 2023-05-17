package com.github.twentiethcenturygangsta.adminboard.utils;

import java.lang.reflect.WildcardType;

public final class AdminBoardStringConvertUtils {
    private static final String WHITE_SPACE = " ";

    private AdminBoardStringConvertUtils() {}

    public static String getConvertedColumnName(String camelName) {
        StringBuilder result = new StringBuilder();
        for(int i = 0; i<camelName.length(); i++) {
            if(i == 0) {
                result.append(Character.toUpperCase(camelName.charAt(0)));
            } else {
                if(Character.isUpperCase(camelName.charAt(i))) {
                    result.append(WHITE_SPACE);
                    result.append(camelName.charAt(i));
                } else {
                    result.append(Character.toUpperCase(camelName.charAt(i)));
                }
            }
        }
        return result.toString();
    }

    public static String getConvertedTableName(String camelName) {
        StringBuilder result = new StringBuilder();
        for(int i = 0; i<camelName.length(); i++) {
            if (Character.isUpperCase(camelName.charAt(i))) {
                result.append(WHITE_SPACE);
                result.append(i);
            }
        }
        return result.toString();
    }
}
