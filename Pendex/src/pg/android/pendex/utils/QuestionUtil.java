package pg.android.pendex.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pg.android.pendex.beans.Answer;
import pg.android.pendex.beans.PendexRating;
import pg.android.pendex.beans.Question;
import pg.android.pendex.db.File;
import pg.android.pendex.exceptions.OutOfQuestionsException;
import pg.android.pendex.exceptions.QuestionsLoadException;
import android.content.Context;

/**
 * Utility for generating questions.
 * 
 * @author Sora
 *
 */
public final class QuestionUtil {

	private static Question selectedQuestion;
	private static final List<Question> questions = new ArrayList<Question>();

	private static final int QUESTIONS_TO_REFRESH = 5;
	private static final int MAX_QUESTIONS_LOADED = 10;

	public static Question getRandomQuestion(final Context context) throws QuestionsLoadException, OutOfQuestionsException {

		if (questions.isEmpty() || questions.size() < QUESTIONS_TO_REFRESH) {
			loadQuestions(context);
		}

		// Sanity check because you could be done.
		if (!questions.isEmpty()) {
			final Random r = new Random();
			final int randomIndex = r.nextInt(questions.size());
			final Question q = questions.get(randomIndex);
			questions.remove(randomIndex);
			selectedQuestion = q;
			return q;
		}

		selectedQuestion = null;

		throw new OutOfQuestionsException();

	}

	private static void loadQuestions(final Context context) throws QuestionsLoadException {

		try {

			final JSONArray array =  new JSONArray(File.loadQuestionsFromFile(context));
			int added = 0;

			for (int i = 0 ; i < array.length(); i++) {

				final JSONObject object = array.getJSONObject(i);
				final String id = object.getString("id");

				if (!ProfileUtil.hasAnswered(id) && added < MAX_QUESTIONS_LOADED) {
					questions.add(convertJsonToQuestion(object));
					added++;
				}

			}

		} catch (final JSONException e) {

			e.printStackTrace();
			throw new QuestionsLoadException();

		} catch (final IOException e) {

			e.printStackTrace();
			throw new QuestionsLoadException();

		}

	}

	private static Question convertJsonToQuestion(final JSONObject object) throws JSONException {

		final Question question = new Question();

		final String id = object.getString("id");
		final String questionText = object.getString("question");
		final Integer random = object.getInt("random");

		question.setId(id);
		question.setRandom(random);
		question.setQuestion(questionText);

		final JSONArray answers = object.getJSONArray("answers");
		final List<Answer> answersList = new ArrayList<Answer>();

		for (int i = 0 ; i < answers.length(); i++) {

			final JSONObject ans = answers.getJSONObject(i);

			final String answerText = ans.getString("answer");

			final JSONObject pendex = ans.getJSONObject("pendex");

			final Answer answer = new Answer();

			answer.setAnswer(answerText);

			final PendexRating pendexRating = new PendexRating();

			pendexRating.setPendex(JsonUtil.createPendexMapFromJson(pendex));

			answer.setPendexRating(pendexRating);

			answersList.add(answer);

		}

		question.setAnswers(answersList);

		return question;
	}

	public static Question getSelectedQuestion() {
		return selectedQuestion;
	}

}
