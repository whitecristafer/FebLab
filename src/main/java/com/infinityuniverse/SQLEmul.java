package com.infinityuniverse;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SQLEmul {
    private List<Map<String, Object>> data = new ArrayList<>();

    public SQLEmul() {
    }

    public List<Map<String, Object>> execute(String request) throws Exception {
        String normalizedRequest = request.trim().replaceAll("\\s+", " ");
        Command command = parseCommand(normalizedRequest);
        switch (command.type) {
            case INSERT:
                return processInsert(command);
            case UPDATE:
                return processUpdate(command);
            case DELETE:
                return processDelete(command);
            case SELECT:
                return processSelect(command);
            default:
                throw new Exception("Unknown command: " + command.type);
        }
    }

    private Command parseCommand(String request) throws Exception {
        String lowerRequest = request.toLowerCase();
        if (lowerRequest.startsWith("insert")) {
            return parseInsert(request);
        } else if (lowerRequest.startsWith("update")) {
            return parseUpdate(request);
        } else if (lowerRequest.startsWith("delete")) {
            return parseDelete(request);
        } else if (lowerRequest.startsWith("select")) {
            return parseSelect(request);
        } else {
            throw new Exception("Unknown command: " + request);
        }
    }

    private Command parseInsert(String request) throws Exception {
        Pattern pattern = Pattern.compile("insert values (.*)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(request);
        if (!matcher.find()) {
            throw new Exception("Invalid INSERT command");
        }
        String valuesPart = matcher.group(1);
        Map<String, Object> values = parseValues(valuesPart);
        return new Command(CommandType.INSERT, values, null, null);
    }

    private Command parseUpdate(String request) throws Exception {
        Pattern pattern = Pattern.compile("update values (.*?)( where (.*))?", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher matcher = pattern.matcher(request);
        if (!matcher.find()) {
            throw new Exception("Invalid UPDATE command");
        }
        String valuesPart = matcher.group(1).trim();
        String wherePart = matcher.group(3);
        Map<String, Object> values = parseValues(valuesPart);
        List<Condition> whereConditions = parseWhereClause(wherePart);
        return new Command(CommandType.UPDATE, values, whereConditions, null);
    }

    private Command parseDelete(String request) throws Exception {
        Pattern pattern = Pattern.compile("delete( where (.*))?", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(request);
        if (!matcher.find()) {
            throw new Exception("Invalid DELETE command");
        }
        String wherePart = matcher.group(2);
        List<Condition> whereConditions = parseWhereClause(wherePart);
        return new Command(CommandType.DELETE, null, whereConditions, null);
    }

    private Command parseSelect(String request) throws Exception {
        Pattern pattern = Pattern.compile("select( where (.*))?", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(request);
        if (!matcher.find()) {
            throw new Exception("Invalid SELECT command");
        }
        String wherePart = matcher.group(2);
        List<Condition> whereConditions = parseWhereClause(wherePart);
        return new Command(CommandType.SELECT, null, whereConditions, null);
    }

    private Map<String, Object> parseValues(String valuesPart) throws Exception {
        Map<String, Object> values = new HashMap<>();
        if (valuesPart == null || valuesPart.isEmpty()) {
            return values;
        }
        String[] pairs = valuesPart.split(",");
        for (String pair : pairs) {
            pair = pair.trim();
            String[] keyValue = pair.split("=", 2);
            if (keyValue.length != 2) {
                throw new Exception("Invalid key-value pair: " + pair);
            }
            String key = keyValue[0].trim().replaceAll("'", "").toLowerCase();
            String valueStr = keyValue[1].trim();
            Object value = parseValue(valueStr);
            values.put(key, value);
        }
        return values;
    }

    private Object parseValue(String valueStr) throws Exception {
        if (valueStr.startsWith("'") && valueStr.endsWith("'")) {
            return valueStr.substring(1, valueStr.length() - 1);
        } else if (valueStr.equalsIgnoreCase("true")) {
            return true;
        } else if (valueStr.equalsIgnoreCase("false")) {
            return false;
        } else if (valueStr.equalsIgnoreCase("null")) {
            return null;
        } else if (valueStr.contains(".")) {
            try {
                return Double.parseDouble(valueStr);
            } catch (NumberFormatException e) {
                throw new Exception("Invalid double value: " + valueStr);
            }
        } else {
            try {
                return Long.parseLong(valueStr);
            } catch (NumberFormatException e) {
                throw new Exception("Invalid long value: " + valueStr);
            }
        }
    }

    private List<Condition> parseWhereClause(String whereClause) throws Exception {
        List<Condition> conditions = new ArrayList<>();
        if (whereClause == null || whereClause.isEmpty()) {
            return conditions;
        }
        String[] tokens = whereClause.split("(?i)\\s+(AND|OR)\\s+");
        for (String token : tokens) {
            Matcher matcher = Pattern.compile("'([^']*)'\\s*(=|!=|like|ilike|>=|<=|>|<)\\s*(.*)").matcher(token);
            if (matcher.find()) {
                String column = matcher.group(1).toLowerCase();
                String operator = matcher.group(2).toLowerCase();
                String valueStr = matcher.group(3).trim();
                Object value = parseValue(valueStr);
                conditions.add(new Condition(column, operator, value));
            } else {
                throw new Exception("Invalid condition: " + token);
            }
        }
        return conditions;
    }

    private List<Map<String, Object>> processInsert(Command command) throws Exception {
        Map<String, Object> newRow = new HashMap<>(command.values);
        data.add(newRow);
        return Collections.singletonList(new HashMap<>(newRow));
    }

    private List<Map<String, Object>> processUpdate(Command command) throws Exception {
        checkColumnsExist(command);
        List<Map<String, Object>> updatedRows = new ArrayList<>();
        for (Map<String, Object> row : data) {
            if (matchesConditions(row, command.whereConditions)) {
                for (Map.Entry<String, Object> entry : command.values.entrySet()) {
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
        return updatedRows;
    }

    private List<Map<String, Object>> processDelete(Command command) throws Exception {
        checkColumnsExist(command);
        List<Map<String, Object>> rowsToDelete = new ArrayList<>();
        Iterator<Map<String, Object>> iterator = data.iterator();
        while (iterator.hasNext()) {
            Map<String, Object> row = iterator.next();
            if (matchesConditions(row, command.whereConditions)) {
                rowsToDelete.add(new HashMap<>(row));
                iterator.remove();
            }
        }
        return rowsToDelete;
    }

    private List<Map<String, Object>> processSelect(Command command) throws Exception {
        checkColumnsExist(command);
        return data.stream()
                .filter(row -> {
                    try {
                        return matchesConditions(row, command.whereConditions);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(row -> new HashMap<>(row))
                .collect(Collectors.toList());
    }

    private boolean matchesConditions(Map<String, Object> row, List<Condition> conditions) throws Exception {
        if (conditions.isEmpty()) {
            return true;
        }
        boolean result = evaluateCondition(row, conditions.get(0));
        for (int i = 1; i < conditions.size(); i++) {
            // Simplified logical operators handling (assumes implicit AND if not specified)
            result = result && evaluateCondition(row, conditions.get(i));
        }
        return result;
    }

    private boolean evaluateCondition(Map<String, Object> row, Condition condition) throws Exception {
        Object rowValue = row.get(condition.column);
        Object conditionValue = condition.value;
        String operator = condition.operator;

        if (conditionValue == null) {
            throw new Exception("Condition value cannot be null");
        }

        switch (operator) {
            case "=":
                return Objects.equals(rowValue, conditionValue) && (rowValue == null || rowValue.getClass().equals(conditionValue.getClass()));
            case "!=":
                return !Objects.equals(rowValue, conditionValue) || (rowValue != null && !rowValue.getClass().equals(conditionValue.getClass()));
            case "like":
            case "ilike":
                return handleLike(rowValue, conditionValue, operator);
            case ">=":
            case "<=":
            case ">":
            case "<":
                return handleNumericComparison(rowValue, conditionValue, operator);
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

    private void checkColumnsExist(Command command) throws Exception {
        Set<String> columns = getAllColumns();
        Set<String> queryColumns = new HashSet<>();
        if (command.type == CommandType.UPDATE) {
            queryColumns.addAll(command.values.keySet());
        }
        if (command.whereConditions != null) {
            command.whereConditions.forEach(c -> queryColumns.add(c.column));
        }
        for (String column : queryColumns) {
            if (!columns.contains(column.toLowerCase())) {
                throw new Exception("Column '" + column + "' does not exist");
            }
        }
    }

    private Set<String> getAllColumns() {
        return data.stream()
                .flatMap(map -> map.keySet().stream())
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
    }

    private enum CommandType {
        INSERT, UPDATE, DELETE, SELECT
    }

    private static class Command {
        CommandType type;
        Map<String, Object> values;
        List<Condition> whereConditions;

        Command(CommandType type, Map<String, Object> values, List<Condition> whereConditions, List<String> logicalOperators) {
            this.type = type;
            this.values = values;
            this.whereConditions = whereConditions;
        }
    }

    private static class Condition {
        String column;
        String operator;
        Object value;

        Condition(String column, String operator, Object value) {
            this.column = column;
            this.operator = operator;
            this.value = value;
        }
    }
}