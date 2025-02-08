package com.infinityuniverse.sqlcommands;

import java.util.ArrayList;
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