package pg.android.pendex.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import pg.android.pendex.beans.Answer;
import pg.android.pendex.beans.PendexRating;
import pg.android.pendex.beans.Question;

/**
 * Utility for generating questions.
 * 
 * @author Sora
 *
 */
public class QuestionUtil {

	public static Question getRandomQuestion(Context context) {
		
		Question question = null;
		
		JSONArray array = JsonUtil.getJsonQuestions(context);	
		
		try {
			question = convertJsonToQuestion(array.getJSONObject(0));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return question;
		
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
		
			String answerText = ans.getString("answer");
			
			JSONObject pendex = ans.getJSONObject("pendex");
			
			Answer answer = new Answer();
			
			answer.setAnswer(answerText);
			
			PendexRating pendexRating = new PendexRating();
			
			Iterator<String> pendexKeyIter = pendex.keys();
			
			Map<String, Integer> map = new HashMap<String, Integer>();
			
			while (pendexKeyIter.hasNext()) {
				
				String pendexText = pendexKeyIter.next();
				
				int val = pendex.getInt(pendexText);
				
				map.put(pendexText, val);
				
			}
			
			pendexRating.setPendex(map);
			
			answer.setPendexRating(pendexRating);
			
		}
		
		return question;
	}
	
}
