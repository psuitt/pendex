package pg.android.pendex.test.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import pg.android.pendex.utils.Utils;

/**
 * Unit tests for utils.
 * 
 * @author Sora
 * 
 */
public class UtilsTest extends TestCase {

    public void testRandomFromList() {
        final List<String> list = new ArrayList<String>();

        list.add("String");
        list.add("String 2");

        final String random = Utils.randomFromList(list);

        assertTrue(list.contains(random));

    }

    public void testGetHighestKeysFromMap() {

        final Map<String, Integer> map = new HashMap<String, Integer>();

        map.put("Test 1", 1);
        map.put("Test 2", 3);
        map.put("Test 3", 1);
        map.put("Test 4", 4);

        final List<String> highest = Utils.getHighestKeysFromMap(map);

        assertEquals(highest.get(0), "Test 4");

    }

    public void testGetHighestKeysFromMapMultiple() {

        final Map<String, Integer> map = new HashMap<String, Integer>();

        map.put("Test 1", 1);
        map.put("Test 2", 3);
        map.put("Test 3", 4);
        map.put("Test 4", 4);

        final List<String> highest = Utils.getHighestKeysFromMap(map);

        assertTrue(highest.contains("Test 3"));
        assertTrue(highest.contains("Test 4"));

    }

}
