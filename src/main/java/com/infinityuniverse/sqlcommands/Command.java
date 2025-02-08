package com.infinityuniverse.sqlcommands;

import java.util.List;
import java.util.Map;

/**
 * Абстрактный базовый класс для всех команд (INSERT, UPDATE, DELETE, SELECT).
 * Каждая команда должна реализовать метод applyCommand для обработки SQL-подобного запроса.
 */
public abstract class Command {

    /**
     * Применяет эту команду к коллекции данных с использованием указанного запроса.
     *
     * @param request сырой SQL-подобный запрос в виде строки
     * @param data коллекция строк данных
     * @return список строк данных, которые были найдены, изменены, вставлены или удалены этой командой
     * @throws Exception если что-то пошло не так (например, неизвестные колонки, неверные типы данных)
     */
    public abstract List<Map<String, Object>> applyCommand(String request, List<Map<String, Object>> data) throws Exception;

    /**
     * Вспомогательный метод для разбора условия "WHERE" (если присутствует).
     * В реальной реализации этот метод должен разбирать логику, обрабатывать AND/OR и оценивать условия.
     * Здесь вы можете построить что-то для фильтрации строк из данных.
     *
     * @param wherePart строка после WHERE (или пустая строка, если отсутствует)
     * @param row одна строка данных для проверки
     * @return true, если строка соответствует условию, false в противном случае
     * @throws Exception если используются неверные колонки или типы данных
     */
    protected boolean matchesCondition(String wherePart, Map<String, Object> row) throws Exception {
        // Заглушка: в реальном решении вы бы разобрали и применили все операторы ( =, !=, like, ilike и т.д. )
        if (wherePart == null || wherePart.trim().isEmpty()) {
            // Отсутствие условия означает "совпадение с любым значением"
            return true;
        }
        // Для демонстрации всегда возвращаем true здесь.
        // Реальная логика разобрала бы условие правильно.
        return true;
    }
}