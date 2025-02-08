package com.infinityuniverse.sqlcommands;

import java.util.List;
import java.util.Map;

public interface SQLCommand {
    List<Map<String, Object>> execute(List<Map<String, Object>> data) throws Exception;
}