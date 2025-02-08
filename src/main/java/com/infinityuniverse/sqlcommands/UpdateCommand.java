package com.infinityuniverse.sqlcommands;

import java.util.*;

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
            if (matchesConditions(row, whereConditions)) {
                for (Map.Entry<String, Object> entry : values.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    if (value == null) {
                        row.remove(key);
                    } else {
                        row.put(key, value);
                    }
                }
                updatedRows.add(new HashMap<>(row));
            }
        }
        System.out.println("Data after update: " + data); // Debug print
        return updatedRows;
    }

    private boolean matchesConditions(Map<String, Object> row, List<Condition> conditions) throws Exception {
        // Implement matching logic
        return true;
    }
}