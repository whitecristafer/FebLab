package com.infinityuniverse.sqlcommands;

public class Parser {

    public static String stripQuotes(String text) {
        if (text.startsWith("'") && text.endsWith("'") && text.length() >= 2) {
            return text.substring(1, text.length() - 1);
        }
        return text;
    }

    // Очень простой парсер: проверяем булевы значения, числа либо строку
    public static Object parseValue(String val) {
        val = val.trim();
        if (val.equalsIgnoreCase("true")) {
            return true;
        }
        if (val.equalsIgnoreCase("false")) {
            return false;
        }
        if (val.equalsIgnoreCase("null")) {
            return null;
        }
        // Пытаемся интерпретировать как Long
        try {
            long l = Long.parseLong(val);
            return l;
        } catch (NumberFormatException e1) {
        }
        // Пытаемся интерпретировать как Double
        try {
            double d = Double.parseDouble(val);
            return d;
        } catch (NumberFormatException e2) {
        }
        // Если дошли сюда, значит это строка. Удаляем кавычки при наличии.
        return stripQuotes(val);
    }
}
