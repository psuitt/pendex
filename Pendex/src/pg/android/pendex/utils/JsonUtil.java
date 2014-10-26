package pg.android.pendex.utils;

import org.json.JSONArray;
import org.json.JSONException;

import pg.android.pendex.db.FileReader;

import android.content.Context;

/**
 * Returns questions.
 * 
 * @author Sora
 *
 */
public class JsonUtil {

	/**
	 * Returns the questions in a {@link JSONArray} format.
	 * 
	 * @param context - {@link Context} - Application context.
	 * 
	 * @return {@link JSONArray} - The json array.
	 */
	public static JSONArray getJsonQuestions(Context context) {
		
		JSONArray array;
		
		try {
			
			array = new JSONArray(FileReader.loadQuestionsFromFile(context));
			
		} catch (JSONException e) {
			e.printStackTrace();
            return null;
		}
		
		return array;
		
	}
	
}
