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
import android.util.Log;

public final class JsonUtil {

    private static final String TAG = "JsonUtil";

    public static Map<String, Integer> createStringIntMapFromJson(final JSONObject object,
            final String field) {

        final Map<String, Integer> map = new HashMap<String, Integer>();

        try {
            return createStringIntMapFromJson(object.getJSONObject(field));
        } catch (final JSONException e) {
            Log.w(TAG, "This object doesn't contain the input field [field=" + field + "]");
        }

        return map;

    }

    public static Map<String, Integer> createStringIntMapFromJson(final JSONObject object)
            throws JSONException {

        final Iterator<String> keyItr = object.keys();

        final Map<String, Integer> map = new HashMap<String, Integer>();

        while (keyItr.hasNext()) {

            final String key = keyItr.next();

            final int val = object.getInt(key);

            map.put(key, val);

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
            Log.w(TAG, "This object doesn't contain the input field [field=" + field + "]");
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
            Log.w(TAG, "This object doesn't contain the input field [field=" + field + "]");
        }

        return Constants.EMPTY_STRING;
    }


    public static boolean getBoolean(final JSONObject profileObj, final String field,
            final boolean defaultValue) {
        try {
            return profileObj.getBoolean(field);
        } catch (final JSONException e) {
            Log.w(TAG, "This object doesn't contain the input field [field=" + field + "]");
        }
        return defaultValue;
    }

}
