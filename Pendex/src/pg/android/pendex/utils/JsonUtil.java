package pg.android.pendex.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pg.android.pendex.constants.Constants;

public final class JsonUtil {

    public static Map<String, Integer> createPendexMapFromJson(final JSONObject pendex)
            throws JSONException {

        final Iterator<String> pendexKeyIter = pendex.keys();

        final Map<String, Integer> map = new HashMap<String, Integer>();

        while (pendexKeyIter.hasNext()) {

            final String pendexText = pendexKeyIter.next();

            final int val = pendex.getInt(pendexText);

            map.put(pendexText, val);

        }

        return map;

    }

    /**
     * Gets a json array from an object and returns a string list.
     * 
     * @param object - {@link JSONObject} - Contains the array.
     * @param field - String - Array field.
     * 
     * @return List&lt;String&gt; - List of objects.
     */
    public static List<String> getListString(final JSONObject object, final String field) {

        final List<String> list = new ArrayList<String>();

        try {
            final JSONArray array = object.getJSONArray(field);

            for (int i = 0; i < array.length(); i++) {

                list.add(array.getString(i));

            }

        } catch (final JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static JSONArray stringListToJSONArray(final List<String> list) {

        final JSONArray array = new JSONArray(list);

        return array;

    }

    public static String getString(final JSONObject object, final String field) {

        try {
            return object.getString(field);
        } catch (final JSONException e) {
            e.printStackTrace();
        }

        return Constants.EMPTY_STRING;
    }

}
