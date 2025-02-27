package com.infinityuniverse.sqlcommands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Обрабатывает команду "UPDATE". Пример синтаксиса:
 * UPDATE VALUES 'active'=ложь, 'cast'=10,1 ГДЕ 'id'=3
 *
 * Все строки, соответствующие условию WHERE, обновляются с помощью указанных VALUES.
 * Возвращаются обновленные строки.
 */
public class UpdateCommand extends Command {

    @Override
    public List<Map<String, Object>> applyCommand(String request, List<Map<String, Object>> data) throws Exception {
        String upper = request.toUpperCase();

        int valuesIdx = upper.indexOf("VALUES");
        if (valuesIdx < 0) {
            throw new Exception("UPDATE syntax error: missing VALUES");
        }

        String valuesPart;
        String wherePart = null;

        int whereIdx = upper.indexOf("WHERE");
        if (whereIdx >= 0) {
            valuesPart = request.substring(valuesIdx + "VALUES".length(), whereIdx).trim();
            wherePart = request.substring(whereIdx + "WHERE".length()).trim();
        } else {
            valuesPart = request.substring(valuesIdx + "VALUES".length()).trim();
        }

        // Парсим значения
        String[] assignments = valuesPart.split(",");
        List<Map<String, Object>> updatedRows = new ArrayList<>();

        // Для каждой строки, если она соответствует условию, примените изменения
        for (Map<String, Object> row : data) {
            if (matchesCondition(wherePart, row)) {
                // Для каждого значения
                for (String assignment : assignments) {
                    String assignTrim = assignment.trim();
                    if (assignTrim.isEmpty()) {
                        continue;
                    }
                    String[] parts = assignTrim.split("=", 2);
                    if (parts.length != 2) {
                        continue;
                    }
                    String col = parts[0].trim();
                    String val = parts[1].trim();

                    col = Parser.stripQuotes(col);
                    Object typedVal = Parser.parseValue(val);

                    // Обновляем строку
                    if (typedVal == null) {
                        row.remove(col);
                    } else {
                        row.put(col, typedVal);
                    }
                }
                updatedRows.add(row);
            }
        }
        return updatedRows;
    }
}