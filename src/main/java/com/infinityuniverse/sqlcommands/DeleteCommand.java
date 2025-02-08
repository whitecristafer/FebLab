package com.infinityuniverse.sqlcommands;

import java.util.*;

public class DeleteCommand implements SQLCommand {
    private List<Condition> whereConditions;

    public DeleteCommand(List<Condition> whereConditions) {
        this.whereConditions = whereConditions;
    }

    @Override
    public List<Map<String, Object>> execute(List<Map<String, Object>> data) throws Exception {
        List<Map<String, Object>> deletedRows = new ArrayList<>();
        Iterator<Map<String, Object>> iterator = data.iterator();
        while (iterator.hasNext()) {
            Map<String, Object> row = iterator.next();
            if (matchesConditions(row)) {
                deletedRows.add(new HashMap<>(row));
                iterator.remove();
            }
        }
        return deletedRows;
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