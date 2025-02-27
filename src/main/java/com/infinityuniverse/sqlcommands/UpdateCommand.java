package com.infinityuniverse.sqlcommands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Обрабатывает команду "UPDATE". Пример синтаксиса:
 * UPDATE VALUES 'active'=false, 'cost'=10.1 WHERE 'id'=3
 *
 * Все строки, соответствующие условию WHERE, обновляются с помощью указанных VALUES.
 * Возвращаются обновленные строки.
 */
public class UpdateCommand extends SQLCommand {
    private Map<String, Object> values;
    private List<Condition> whereConditions;

    public UpdateCommand(Map<String, Object> values, List<Condition> whereConditions) {
        this.values = values;
        this.whereConditions = whereConditions;
    }

    @Override
    public List<Map<String, Object>> applyCommand(List<Map<String, Object>> data) throws Exception {
        List<Map<String, Object>> updatedRows = new ArrayList<>();

        for (Map<String, Object> row : data) {
            boolean matches = true;
            for (Condition condition : whereConditions) {
                if (!condition.evaluate(row)) {
                    matches = false;
                    break;
                }
            }
            if (matches) {
                row.putAll(values);
                updatedRows.add(row);
            }
        }
        return updatedRows;
    }
}