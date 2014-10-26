package pg.android.pendex.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import pg.android.pendex.beans.Question;

/**
 * Utility for generating questions.
 * 
 * @author Sora
 *
 */
public class QuestionUtil {

	public static Question getRandomQuestion(Context context) {
		
		JSONArray array = JsonUtil.getJsonQuestions(context);
    	String s = "Action!";
		
		
		try {
			Question question = convertJsonToQuestion(array.getJSONObject(0));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return null;
		
	}

	private static Question convertJsonToQuestion(JSONObject object) throws JSONException {
		
		Question question = new Question();
		
		String id = object.getString("id");
		String questionText = object.getString("question");
		Integer random = object.getInt("random");
		
		question.setId(id);
		question.setRandom(random);
		question.setQuestion(questionText);
		
		JSONArray answers = object.getJSONArray("answers");
		
		for (int i = 0 ; i < answers.length(); i++) {
			
			JSONObject ans = answers.getJSONObject(i);
		
			
			
		}
		
		
		return null;
	}
	
}
