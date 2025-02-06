package com.infinityuniverse;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {
            SQLEmul sqlEmul = new SQLEmul();

            // Вставка данных
            System.out.println("Вставка данных:");
            List<Map<String, Object>> resultInsert = sqlEmul.execute("INSERT VALUES id=3, active=true, age=40, lastname='Федоров'");
            System.out.println("Результат вставки: " + resultInsert);

            // Обновление данных
            System.out.println("Обновление данных:");
            List<Map<String, Object>> resultUpdate = sqlEmul.execute("UPDATE VALUES age=41 WHERE 'id' = 3");
            System.out.println("Результат обновления: " + resultUpdate);

            // Удаление данных
            System.out.println("Удаление данных:");
            List<Map<String, Object>> resultDelete = sqlEmul.execute("DELETE WHERE 'id' = 3");
            System.out.println("Результат удаления: " + resultDelete);

            // Проверка данных после операций
            System.out.println("Данные после операций:");
            List<Map<String, Object>> resultSelect = sqlEmul.execute("SELECT");
            System.out.println("Текущие данные: " + resultSelect);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}