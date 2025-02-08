package com.infinityuniverse.sqlcommands;

import java.util.*;

public class InsertCommand implements SQLCommand {
    private Map<String, Object> values;

    public InsertCommand(Map<String, Object> values) {
        this.values = values;
    }

    @Override
    public List<Map<String, Object>> execute(List<Map<String, Object>> data) {
        Map<String, Object> newRow = new HashMap<>(values);
        data.add(newRow);
        System.out.println("Data after insert: " + data); // Debug print
        return Collections.singletonList(new HashMap<>(newRow));
    }
}
