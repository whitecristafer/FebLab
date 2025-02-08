package com.infinityuniverse;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
<<<<<<< HEAD
        SQLEmul sqlEmul = new SQLEmul();

        try {
            // Пример: добавляем новую запись в коллекцию
            List<Map<String, Object>> resultInsert = sqlEmul.execute(
                    "INSERT VALUES 'lastName'='Федоров', 'id'=3, 'age'=40, 'active'=true"
            );
            System.out.println("Результат INSERT: " + resultInsert);

            // Пример: обновляем запись с id=3
            List<Map<String, Object>> resultUpdate = sqlEmul.execute(
                    "UPDATE VALUES 'active'=false, 'cost'=10.1 WHERE 'id'=3"
            );
            System.out.println("Результат UPDATE: " + resultUpdate);

            // Пример: выбираем все записи (без WHERE)
            List<Map<String, Object>> resultSelect = sqlEmul.execute("SELECT");
            System.out.println("Результат SELECT: " + resultSelect);

            // Пример: удаляем запись с id=3
            List<Map<String, Object>> resultDelete = sqlEmul.execute("DELETE WHERE 'id'=3");
            System.out.println("Результат DELETE: " + resultDelete);

            // Снова выбираем все записи после удаления
            List<Map<String, Object>> finalSelect = sqlEmul.execute("SELECT");
            System.out.println("Итоговое состояние коллекции: " + finalSelect);

        } catch (Exception ex) {
            ex.printStackTrace();
=======
        try {
            SQLEmul sqlEmul = new SQLEmul();

            // Команда INSERT
            String insertCommand = "INSERT VALUES 'name'='John Doe', 'age'=30";
            List<Map<String, Object>> insertResult = sqlEmul.execute(insertCommand);
            System.out.println("Insert Result: " + insertResult);

            // Команда SELECT
            String selectCommand = "SELECT";
            List<Map<String, Object>> selectResult = sqlEmul.execute(selectCommand);
            System.out.println("Select Result: " + selectResult);

            // Команда UPDATE
            String updateCommand = "UPDATE VALUES 'age'=31 WHERE 'name'='John Doe'";
            List<Map<String, Object>> updateResult = sqlEmul.execute(updateCommand);
            System.out.println("Update Result: " + updateResult);

            // Команда DELETE
            String deleteCommand = "DELETE WHERE 'name'='John Doe'";
            List<Map<String, Object>> deleteResult = sqlEmul.execute(deleteCommand);
            System.out.println("Delete Result: " + deleteResult);

        } catch (Exception e) {
            e.printStackTrace();
>>>>>>> master
        }
    }
}