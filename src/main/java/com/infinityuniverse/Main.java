package com.infinityuniverse;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
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
        }
    }
}