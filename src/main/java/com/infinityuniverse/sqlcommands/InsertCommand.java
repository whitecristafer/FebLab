package com.infinityuniverse.sqlcommands;

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Обрабатывает команду "INSERT". Пример синтаксиса:
 * INSERT VALUES 'column1'='value1', 'column2'='value2', ...
 *
 * В результате возвращается вновь созданная строка.
 */
public class InsertCommand extends Command {

    @Override
    public List<Map<String, Object>> applyCommand(String request, List<Map<String, Object>> data) throws Exception {

        // Пример упрощённого разбора: находим "VALUES" и разделяем по запятым
        String[] assignments = getStrings(request);
        Map<String, Object> newRow = new HashMap<>();

        for (String assignment : assignments) {
            String assignTrim = assignment.trim();
            if (assignTrim.isEmpty()) {
                continue;
            }
            // например "'lastName'='Федоров'"
            String[] parts = assignTrim.split("=", 2);
            if (parts.length != 2) {
                // возможно обрыв или неполнота
                continue;
            }
            String col = parts[0].trim();
            String val = parts[1].trim();

            // Удаляем окружающие кавычки из названия колонки
            col = Parser.stripQuotes(col);
            // Преобразуем val в объект нужного типа
            Object typedValue = Parser.parseValue(val);
            newRow.put(col, typedValue);
        }

        // Добавляем новую строку в data
        data.add(newRow);

        // Возвращаем список, содержащий вставленную строку
        List<Map<String, Object>> result = new ArrayList<>();
        result.add(newRow);
        return result;
    }

    private static String[] getStrings(String request) throws Exception {
        String upper = request.toUpperCase();
        int idx = upper.indexOf("VALUES");
        if (idx < 0) {
            throw new Exception("Ошибка синтаксиса INSERT: отсутствует VALUES");
        }
        String valuesPart = request.substring(idx + "VALUES".length()).trim();

        // Удаляем ведущие знаки пунктуации, если есть (например, после VALUES)
        if (valuesPart.startsWith("'") || valuesPart.startsWith("\"")) {
            // нетипичная ситуация, но на случай, если пользователь написал иначе
        }

        // Удаляем ведущие/закрывающие скобки, если есть: формально не в спецификации
        // Будем просто разбирать по запятым для демонстрации
        valuesPart = valuesPart.replaceFirst("^[ ,]*", "");

        // 'column1'='value1', 'column2'=123 ...
        // Разделяем по запятым вне кавычек -> наивный подход, или более простой split:
        return valuesPart.split(",");
    }


}
=======
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
>>>>>>> master
