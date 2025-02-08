package com.infinityuniverse.sqlcommands;

import java.util.Map;

public abstract class Condition {
    protected String column;
    protected String operator;
    protected Object value;

    protected Condition(String column, String operator, Object value) {
        this.column = column;
        this.operator = operator;
        this.value = value;
    }

    public abstract boolean evaluate(Map<String, Object> row) throws Exception;

    public String getColumn() {
        return column;
    }

    public String getOperator() {
        return operator;
    }

    public Object getValue() {
        return value;
    }
}