package com.infinityuniverse;

import java.util.List;
import java.util.Map;

public interface Executor {

    /**
     * Executes the given SQL-like request string and returns the list of rows
     * that were affected or selected.
     *
     * @param request the SQL-like request (INSERT, UPDATE, DELETE, SELECT)
     * @return list of rows that were found, changed, inserted or deleted
     * @throws Exception in case of bad column name, bad data type, or processing error
     */
    List<Map<String, Object>> execute(String request) throws Exception;
}