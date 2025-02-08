package com.infinityuniverse.sqlcommands;

import java.util.*;


public class SelectCommand implements SQLCommand {
    private final List<Condition> whereConditions;

    public SelectCommand(List<Condition> whereConditions) {
        this.whereConditions = whereConditions;
    }

    @Override
    public List<Map<String, Object>> execute(List<Map<String, Object>> data) throws Exception {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map<String, Object> row : data) {
            if (matchesConditions(row, whereConditions)) {
                result.add(new HashMap<>(row));
            }
        }
        return result;
    }

    private boolean matchesConditions(Map<String, Object> row, List<Condition> conditions) throws Exception {
        // Implement matching logic
        return true;
    }
}