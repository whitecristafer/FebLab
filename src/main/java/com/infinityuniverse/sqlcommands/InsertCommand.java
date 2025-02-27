package com.infinityuniverse.sqlcommands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Обрабатывает команду "INSERT". Пример синтаксиса:
 * INSERT VALUES 'column1'='value1', 'column2'='value2', ...
 *
 * В результате возвращается вновь созданная строка.
 */
public class InsertCommand extends SQLCommand {
    private Map<String, Object> values;

    public InsertCommand(Map<String, Object> values) {
        this.values = values;
    }

    @Override
    public List<Map<String, Object>> applyCommand(List<Map<String, Object>> data) throws Exception {
        data.add(values);
        List<Map<String, Object>> result = new ArrayList<>();
        result.add(values);
        return result;
    }
}