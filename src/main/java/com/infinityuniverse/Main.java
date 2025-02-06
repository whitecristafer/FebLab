package com.infinityuniverse;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        SQLEmul starter = new SQLEmul();
        try {
            List<Map<String, Object>> result1 = starter.execute(
                    "INSERT VALUES 'lastName' = 'Федоров', 'id' = 3, 'age' = 40, 'active' = true"
            );
            System.out.println("Результат вставки:");
            printResult(result1);

            List<Map<String, Object>> result2 = starter.execute(
                    "UPDATE VALUES 'active' = false, 'cost' = 10.1 WHERE 'id' = 3"
            );
            System.out.println("Результат обновления:");
            printResult(result2);

            List<Map<String, Object>> result4 = starter.execute(
                    "DELETE WHERE 'id' = 3"
            );
            System.out.println("Результат удаления:");
            printResult(result4);


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void printResult(List<Map<String, Object>> result) {
        for (Map<String, Object> row : result) {
            for (Map.Entry<String, Object> entry : row.entrySet()) {
                System.out.print(entry.getKey() + ": " + entry.getValue() + ", ");
            }
            System.out.println();
        }
        System.out.println();
    }

}