package com.infinityuniverse.sqlcommands;

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

<<<<<<< HEAD
/**
 * Обработка команды "DELETE". Пример синтаксиса:
 * DELETE WHERE 'id'=3
 *
 * Все строки, соответствующие условию WHERE, удаляются.
 * Удаленные строки возвращаются в результате.
 */
public class DeleteCommand extends Command {

    @Override
    public List<Map<String, Object>> applyCommand(String request, List<Map<String, Object>> data) throws Exception {
        String upper = request.toUpperCase();
        int idx = upper.indexOf("WHERE");
        String wherePart = null;
        if (idx >= 0) {
            wherePart = request.substring(idx + "WHERE".length()).trim();
        }

        List<Map<String, Object>> removed = new ArrayList<>();
        for (int i = 0; i < data.size(); ) {
            Map<String, Object> row = data.get(i);
            if (matchesCondition(wherePart, row)) {
                removed.add(row);
                data.remove(i);
            } else {
                i++;
            }
        }
        return removed;
=======
=======
import java.util.*;

>>>>>>> master
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
<<<<<<< HEAD
>>>>>>> 81a3ccf (Update Command for SQLEmul)
=======
>>>>>>> master
    }
}