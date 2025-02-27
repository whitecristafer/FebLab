package com.infinityuniverse.sqlcommands;

import java.util.Map;

public class SimpleCondition extends Condition {
    public SimpleCondition(String column, String operator, Object value) {
        super(column, operator, value);
    }

    @Override
    public boolean evaluate(Map<String, Object> row) throws Exception {
        Object rowValue = row.get(column);
        if (rowValue == null) {
            return false;
        }

        switch (operator) {
            case "=":
                return rowValue.equals(value);
            case "!=":
                return !rowValue.equals(value);
            case ">":
                return compare(rowValue, value) > 0;
            case "<":
                return compare(rowValue, value) < 0;
            case ">=":
                return compare(rowValue, value) >= 0;
            case "<=":
                return compare(rowValue, value) <= 0;
            case "like":
                return rowValue.toString().contains(value.toString());
            case "ilike":
                return rowValue.toString().toLowerCase().contains(value.toString().toLowerCase());
            default:
                throw new Exception("Unknown operator: " + operator);
        }
    }

    @SuppressWarnings("unchecked")
    private int compare(Object a, Object b) throws Exception {
        if (a instanceof Comparable && b instanceof Comparable) {
            return ((Comparable) a).compareTo(b);
        } else {
            throw new Exception("Values are not comparable: " + a + " and " + b);
        }
    }
}