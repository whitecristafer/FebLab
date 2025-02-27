import com.infinityuniverse.SQLEmul;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class SQLEmulTest {
    private SQLEmul sqlEmul = new SQLEmul();;

    @Test
    public void testInsert() throws Exception {
        List<Map<String, Object>> result = sqlEmul.execute("INSERT VALUES 'lastName'='Федоров', 'id'=3, 'age'=40, 'active'=true'");
        assertEquals(1, result.size());
        assertEquals("Федоров", result.get(0).get("lastname"));
        assertEquals(3L, result.get(0).get("id"));
        assertEquals(40L, result.get(0).get("age"));
        assertEquals(true, result.get(0).get("active"));
    }

    @Test
    public void testUpdate() throws Exception {
        sqlEmul.execute("INSERT VALUES 'lastName'='Федоров', 'id'=3, 'age'=40, 'active'=true'");
        List<Map<String, Object>> result = sqlEmul.execute("UPDATE VALUES 'active'=false, 'cost'=10.1 WHERE 'id'=3");
        assertEquals(1, result.size());
        assertEquals(false, result.get(0).get("active"));
        assertEquals(10.1, result.get(0).get("cost"));
    }

    @Test
    public void testDelete() throws Exception {
        sqlEmul.execute("INSERT VALUES 'lastName'='Федоров', 'id'=3, 'age'=40, 'active'=true'");
        List<Map<String, Object>> result = sqlEmul.execute("DELETE WHERE 'id'=3");
        assertEquals(1, result.size());
        assertEquals("Федоров", result.get(0).get("lastname"));
        assertEquals(3L, result.get(0).get("id"));
        assertEquals(40L, result.get(0).get("age"));
        assertEquals(true, result.get(0).get("active"));
    }

    @Test
    public void testSelect() throws Exception {
        sqlEmul.execute("INSERT VALUES 'lastName'='Федоров', 'id'=3, 'age'=40, 'active'=true'");
        List<Map<String, Object>> result = sqlEmul.execute("SELECT WHERE 'id'=3");
        assertEquals(1, result.size());
    }
}