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
public class DeleteCommand extends SQLCommand {
    private List<Condition> whereConditions;

    public DeleteCommand(List<Condition> whereConditions) {
        this.whereConditions = whereConditions;
    }

    @Override
    public List<Map<String, Object>> applyCommand(List<Map<String, Object>> data) throws Exception {
        List<Map<String, Object>> removed = new ArrayList<>();
        for (int i = 0; i < data.size(); ) {
            Map<String, Object> row = data.get(i);
            boolean matches = true;
            for (Condition condition : whereConditions) {
                if (!condition.evaluate(row)) {
                    matches = false;
                    break;
                }
            }
            if (matches) {
                removed.add(row);
                data.remove(i);
            } else {
                i++;
            }
        }
        return removed;
    }
}