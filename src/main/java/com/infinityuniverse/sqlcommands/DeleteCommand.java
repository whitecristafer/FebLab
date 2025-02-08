package com.infinityuniverse.sqlcommands;

import java.util.*;

public class DeleteCommand implements SQLCommand {
    private final List<Condition> whereConditions;

    public DeleteCommand(List<Condition> whereConditions) {
        this.whereConditions = whereConditions;
    }

    @Override
    public List<Map<String, Object>> execute(List<Map<String, Object>> data) throws Exception {
        List<Map<String, Object>> rowsToDelete = new ArrayList<>();
        Iterator<Map<String, Object>> iterator = data.iterator();
        while (iterator.hasNext()) {
            Map<String, Object> row = iterator.next();
            if (matchesConditions(row, whereConditions)) {
                rowsToDelete.add(new HashMap<>(row));
                iterator.remove();
            }
        }
        System.out.println("Data after delete: " + data);
        return rowsToDelete;
    }

    private boolean matchesConditions(Map<String, Object> row, List<Condition> conditions) throws Exception {
        // Implement matching logic
        return true;
    }
}