package pg.android.pendex.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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

    public static String getString(final JSONObject object, final String field) {

        try {
            return object.getString(field);
        } catch (final JSONException e) {
            e.printStackTrace();
        }

        return Constants.EMPTY_STRING;
    }

}
