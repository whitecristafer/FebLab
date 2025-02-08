package com.infinityuniverse.sqlcommands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Обработка команды "DELETE". Пример синтаксиса:
 * DELETE WHERE 'id'=3
 *
 * Все строки, соответствующие условию WHERE, удаляются.
 * Удаленные строки возвращаются в результате.
 */
public class DeleteCommand extends Command {

    @Override
    public List<Map<String, Object>> applyCommand(String request, List<Map<String, Object>> data) throws Exception {
        String upper = request.toUpperCase();
        int idx = upper.indexOf("WHERE");
        String wherePart = null;
        if (idx >= 0) {
            wherePart = request.substring(idx + "WHERE".length()).trim();
        }

        List<Map<String, Object>> removed = new ArrayList<>();
        for (int i = 0; i < data.size(); ) {
            Map<String, Object> row = data.get(i);
            if (matchesCondition(wherePart, row)) {
                removed.add(row);
                data.remove(i);
            } else {
                i++;
            }
        }
        return removed;
    }
}