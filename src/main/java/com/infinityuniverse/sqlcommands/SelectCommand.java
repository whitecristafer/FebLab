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
public class SelectCommand extends Command {

    @Override
    public List<Map<String, Object>> applyCommand(String request, List<Map<String, Object>> data) throws Exception {
        String upper = request.toUpperCase();
        int idx = upper.indexOf("WHERE");
        String wherePart = null;
        if (idx >= 0) {
            wherePart = request.substring(idx + "WHERE".length()).trim();
        }

        List<Map<String, Object>> result = new ArrayList<>();
        for (Map<String, Object> row : data) {
            if (matchesCondition(wherePart, row)) {
                result.add(row);
            }
        }
        return result;
    }
}