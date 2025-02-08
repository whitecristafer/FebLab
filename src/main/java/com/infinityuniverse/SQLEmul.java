package com.infinityuniverse;

<<<<<<< HEAD
import com.infinityuniverse.sqlcommands.Command;
import com.infinityuniverse.sqlcommands.DeleteCommand;
import com.infinityuniverse.sqlcommands.InsertCommand;
import com.infinityuniverse.sqlcommands.SelectCommand;
import com.infinityuniverse.sqlcommands.UpdateCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Основной класс для обработки запросов в SQL-подобном формате к коллекции в памяти.
 *
 * Реализует интерфейс Executor и хранит List<Map<String, Object>> в качестве хранилища.
 */
public class SQLEmul implements Executor {

    // "Таблица" в памяти: список строк, каждая строка — это Map с ключом (название колонки)
    // и значением (Object).
    private final List<Map<String, Object>> data;

    /**
     * Конструктор по умолчанию инициализирует пустую коллекцию данных.
     */
    public SQLEmul() {
        this.data = new ArrayList<>();
    }

    /**
     * Выполняет запрошенную команду в простом SQL-подобном формате
     * (INSERT, UPDATE, DELETE, SELECT). Пример:
     * "INSERT VALUES 'lastName'='Федоров', 'id'=3, 'age'=40, 'active'=true"
     *
     * @param request SQL-подобный запрос
     * @return список строк, которые были найдены, изменены, вставлены или удалены
     * @throws Exception если возникает проблема с именами колонок или типами данных
     */
    @Override
    public List<Map<String, Object>> execute(String request) throws Exception {
        if (request == null || request.trim().isEmpty()) {
            throw new Exception("Запрос пуст");
        }

        // Приведение пробелов и перевод команды в верхний регистр
        String trimmed = request.trim();
        String upperRequest = trimmed.toUpperCase();

        // Определяем тип команды по первому слову
        Command command;
        if (upperRequest.startsWith("INSERT")) {
            command = new InsertCommand();
        } else if (upperRequest.startsWith("UPDATE")) {
            command = new UpdateCommand();
        } else if (upperRequest.startsWith("DELETE")) {
            command = new DeleteCommand();
        } else if (upperRequest.startsWith("SELECT")) {
            command = new SelectCommand();
        } else {
            throw new Exception("Неизвестный тип команды: " + request);
        }

        // Применяем логику команды к нашей коллекции данных
        return command.applyCommand(request, data);
    }

    /**
     * Возвращает внутренние данные для отладки или других задач.
     *
     * @return изменяемый список строк
     */
    public List<Map<String, Object>> getData() {
        return data;
=======
import com.infinityuniverse.sqlcommands.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SQLEmul {
    private List<Map<String, Object>> data = new ArrayList<>();

    public SQLEmul() {
    }

    public List<Map<String, Object>> execute(String request) throws Exception {
        System.out.println("Executing request: " + request); // Debug print
        String normalizedRequest = request.trim().replaceAll("\\s+", " ");
        SQLCommand command = parseCommand(normalizedRequest);
        return command.execute(data);
    }

    private SQLCommand parseCommand(String request) throws Exception {
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

    private SQLCommand parseInsert(String request) throws Exception {
        Pattern pattern = Pattern.compile("insert values (.*)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(request);
        if (!matcher.find()) {
            throw new Exception("Invalid INSERT command");
        }
        String valuesPart = matcher.group(1);
        Map<String, Object> values = parseValues(valuesPart);
        return new InsertCommand(values);
    }

    private SQLCommand parseUpdate(String request) throws Exception {
        Pattern pattern = Pattern.compile("update values (.*?)( where (.*))?", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher matcher = pattern.matcher(request);
        if (!matcher.find()) {
            throw new Exception("Invalid UPDATE command");
        }
        String valuesPart = matcher.group(1).trim();
        String wherePart = matcher.group(3);
        Map<String, Object> values = parseValues(valuesPart);
        List<com.infinityuniverse.sqlcommands.Condition> whereConditions = parseWhereClause(wherePart);
        return new UpdateCommand(values, whereConditions);
    }

    private SQLCommand parseDelete(String request) throws Exception {
        Pattern pattern = Pattern.compile("delete( where (.*))?", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(request);
        if (!matcher.find()) {
            throw new Exception("Invalid DELETE command");
        }
        String wherePart = matcher.group(2);
        List<com.infinityuniverse.sqlcommands.Condition> whereConditions = parseWhereClause(wherePart);
        return new DeleteCommand(whereConditions);
    }

    private SQLCommand parseSelect(String request) throws Exception {
        Pattern pattern = Pattern.compile("select( where (.*))?", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(request);
        if (!matcher.find()) {
            throw new Exception("Invalid SELECT command");
        }
        String wherePart = matcher.group(2);
        List<com.infinityuniverse.sqlcommands.Condition> whereConditions = parseWhereClause(wherePart);
        return new SelectCommand(whereConditions);
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

    private List<com.infinityuniverse.sqlcommands.Condition> parseWhereClause(String whereClause) throws Exception {
        List<com.infinityuniverse.sqlcommands.Condition> conditions = new ArrayList<>();
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
                conditions.add(new SimpleCondition(column, operator, value));
            } else {
                throw new Exception("Invalid condition: " + token);
            }
        }
        return conditions;
>>>>>>> master
    }
}