package com.infinityuniverse.sqlcommands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Обрабатывает команду "UPDATE". Пример синтаксиса:
 * UPDATE VALUES 'active'=ложь, 'cast'=10,1 ГДЕ 'id'=3
 *
 * Все строки, соответствующие условию WHERE, обновляются с помощью указанных VALUES.
 * Возвращаются обновленные строки.
 */
public class UpdateCommand implements SQLCommand {
    private final Map<String, Object> values;
    private final List<Condition> whereConditions;

    public UpdateCommand(Map<String, Object> values, List<Condition> whereConditions) {
        this.values = values;
        this.whereConditions = whereConditions;
    }

    @Override
    public List<Map<String, Object>> execute(List<Map<String, Object>> data) throws Exception {
        List<Map<String, Object>> updatedRows = new ArrayList<>();

        for (Map<String, Object> row : data) {
            if (matchesConditions(row)) {
                Map<String, Object> newRow = new HashMap<>(row);
                newRow.putAll(values);
                updatedRows.add(newRow);
            }
        }
        System.out.println("Data after update: " + updatedRows); // Debug print
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