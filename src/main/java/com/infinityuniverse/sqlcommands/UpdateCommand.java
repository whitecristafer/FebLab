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


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateCommand implements SQLCommand {
    private Map<String, Object> values;
    private List<Condition> whereConditions;

    public UpdateCommand(Map<String, Object> values, List<Condition> whereConditions) {
        this.values = values;
        this.whereConditions = whereConditions;
    }

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

                }
                updatedRows.add(row);
    @Override
    public List<Map<String, Object>> execute(List<Map<String, Object>> data) throws Exception {
        List<Map<String, Object>> updatedRows = new ArrayList<>();
        for (Map<String, Object> row : data) {
            if (matchesConditions(row)) {
                for (Map.Entry<String, Object> entry : values.entrySet()) {
                    row.put(entry.getKey(), entry.getValue());
                }
                updatedRows.add(new HashMap<>(row));
            }
        }
        return updatedRows;
    }

    private boolean matchesConditions(Map<String, Object> row) throws Exception {
        for (Condition condition : whereConditions) {
            if (!condition.evaluate(row)) {
                return false;
            }
        }
        return true;
    }
}