package pg.android.pendex.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtil {

	public static Map<String, Integer > createPendexMapFromJson(final JSONObject pendex) throws JSONException{

		final Iterator<String> pendexKeyIter = pendex.keys();

		final Map<String, Integer> map = new HashMap<String, Integer>();

		while (pendexKeyIter.hasNext()) {

			final String pendexText = pendexKeyIter.next();

			final int val = pendex.getInt(pendexText);

			map.put(pendexText, val);

		}

		return map;


	}

}
