package com.infinityuniverse.sqlcommands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Обрабатывает команду "SELECT". Пример:
 * SELECT WHERE 'age'>=30 AND 'lastName' ILIKE '%п%'
 *
 * Возвращает все соответствующие строки. Если WHERE не указано, возвращает все строки.
 */
public class SelectCommand extends SQLCommand {
    private List<Condition> whereConditions;

    public SelectCommand(List<Condition> whereConditions) {
        this.whereConditions = whereConditions;
    }

    @Override
    public List<Map<String, Object>> applyCommand(List<Map<String, Object>> data) throws Exception {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map<String, Object> row : data) {
            boolean matches = true;
            for (Condition condition : whereConditions) {
                if (!condition.evaluate(row)) {
                    matches = false;
                    break;
                }
            }
            if (matches) {
                result.add(row);
            }
        }
        return result;
    }
}