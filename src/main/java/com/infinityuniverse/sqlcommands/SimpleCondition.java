package com.infinityuniverse.sqlcommands;

import java.util.Map;
import java.util.Objects;

public class SimpleCondition extends Condition {

    public SimpleCondition(String column, String operator, Object value) {
        super(column, operator, value);
    }

    @Override
    public boolean evaluate(Map<String, Object> row) throws Exception {
        Object rowValue = row.get(column);
        switch (operator) {
            case "=":
                return Objects.equals(rowValue, value);
            case "!=":
                return !Objects.equals(rowValue, value);
            case "like":
            case "ilike":
                return handleLike(rowValue, value, operator);
            case ">=":
            case "<=":
            case ">":
            case "<":
                return handleNumericComparison(rowValue, value, operator);
            default:
                throw new Exception("Unsupported operator: " + operator);
        }
    }

    private boolean handleLike(Object rowValue, Object conditionValue, String operator) throws Exception {
        if (!(rowValue instanceof String) || !(conditionValue instanceof String)) {
            if (rowValue == null) {
                return false;
            }
            throw new Exception("Operator " + operator + " requires String values");
        }
        String rowStr = (String) rowValue;
        String pattern = (String) conditionValue;
        String regex = convertPatternToRegex(pattern, operator.equals("ilike"));
        return rowStr.matches(regex);
    }

    private String convertPatternToRegex(String pattern, boolean caseInsensitive) {
        String regex = pattern.replace("%", ".*")
                .replaceAll("([\\\\^$.|?*+()\\[\\]{}])", "\\\\$1");
        regex = "^" + regex + "$";
        if (caseInsensitive) {
            regex = "(?i)" + regex;
        }
        return regex;
    }

    private boolean handleNumericComparison(Object rowValue, Object conditionValue, String operator) throws Exception {
        if (!(rowValue instanceof Number) || !(conditionValue instanceof Number)) {
            if (rowValue == null) {
                return false;
            }
            throw new Exception("Operator " + operator + " requires numeric values");
        }
        double rowNum = ((Number) rowValue).doubleValue();
        double condNum = ((Number) conditionValue).doubleValue();
        switch (operator) {
            case ">=":
                return rowNum >= condNum;
            case "<=":
                return rowNum <= condNum;
            case ">":
                return rowNum > condNum;
            case "<":
                return rowNum < condNum;
            default:
                throw new Exception("Unsupported operator: " + operator);
        }
    }
}