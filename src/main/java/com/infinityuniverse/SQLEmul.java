package com.infinityuniverse;

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
    }
}