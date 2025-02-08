# SQLEmul

Проект демонстрирует реализацию простой системы обработки SQL-подобных запросов в памяти.  
Она состоит из нескольких основных компонентов, размещённых в различных пакетах:

- **com.infinityuniverse**: Содержит главный класс (SQLEmul) и интерфейс Executor.
    - Класс SQLEmul управляет всей логикой обработки команды и хранит коллекцию (List<Map<String, Object>>).
    - Интерфейс Executor задаёт метод execute, который каждый класс с реализацией должен поддерживать.

- **com.infinityuniverse.sqlcommands**: Содержит классы, отвечающие за выполнение конкретных команд (INSERT, UPDATE, DELETE, SELECT).

## Основные особенности

1. Используется структура хранения данных в виде List<Map<String, Object>>, где каждая Map представляет строку, а ключи задают названия колонок.
2. Поддерживаются команды:
    - INSERT
    - UPDATE
    - DELETE
    - SELECT
3. Каждый тип команды обрабатывается отдельным классом:
    - InsertCommand
    - UpdateCommand
    - DeleteCommand
    - SelectCommand
4. Запросы могут содержать простые условия (WHERE). Наброски парсинга приведены в реализованных классов, но они требуют доработки при необходимости полнофункциональной поддержки операторов (AND, OR, =, !=, <, >, <=, >=, like, ilike и т.д.).
5. Код показывает базовый каркас для демонстрации: можно добавлять новые Rows, удалять, изменять и выбирать данные с учётом простых условий.

## Как использовать

1. Вызвать конструктор класса SQLEmul:
   ```java
   SQLEmul sqlEmul = new SQLEmul();
   ```

2. Полный пример файла Main:
   ```
    public class Main {
        public static void main(String[] args) {
            SQLEmul sqlEmul = new SQLEmul();
    
            try {
                List<Map<String, Object>> resultInsert = sqlEmul.execute(
                        "INSERT VALUES 'lastName'='Федоров', 'id'=3, 'age'=40, 'active'=true"
                );
                System.out.println("Результат INSERT: " + resultInsert);
    
                List<Map<String, Object>> resultUpdate = sqlEmul.execute(
                        "UPDATE VALUES 'active'=false, 'cost'=10.1 WHERE 'id'=3"
                );
                System.out.println("Результат UPDATE: " + resultUpdate);
    
                List<Map<String, Object>> resultSelect = sqlEmul.execute("SELECT");
                System.out.println("Результат SELECT: " + resultSelect);
    
                List<Map<String, Object>> resultDelete = sqlEmul.execute("DELETE WHERE 'id'=3");
                System.out.println("Результат DELETE: " + resultDelete);
    
                List<Map<String, Object>> finalSelect = sqlEmul.execute("SELECT");
                System.out.println("Итоговое состояние коллекции: " + finalSelect);
    
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
   ```
   
