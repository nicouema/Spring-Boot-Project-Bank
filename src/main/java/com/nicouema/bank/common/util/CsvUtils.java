package com.nicouema.bank.common.util;

import java.lang.reflect.Field;

public abstract class CsvUtils {

    public static String objectToCsv(Object o) throws IllegalAccessException {

        StringBuilder csvObjectBuilder = new StringBuilder();
        for (Field field:o.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if(field.get(o) == null){
                csvObjectBuilder.append("null").append(",");
            } else {
                csvObjectBuilder.append(field.get(o).toString()).append(",");
            }
        }
        String csvObject = csvObjectBuilder.toString();
        csvObject = removeLastCharacter(csvObject);
        csvObject += "\n";

        return csvObject;
    }

    public static String generateColumnsCsv(Object o) {

        StringBuilder csvColumnsBuilder = new StringBuilder();
        for (Field field:o.getClass().getDeclaredFields()) {
            csvColumnsBuilder.append(field.getName()).append(",");
        }
        String csvColumns = csvColumnsBuilder.toString();
        csvColumns = removeLastCharacter(csvColumns);
        csvColumns += "\n";

        return csvColumns;

    }

    private static String removeLastCharacter(String str) {
        return str.substring(0, str.length() - 1);
    }
}
