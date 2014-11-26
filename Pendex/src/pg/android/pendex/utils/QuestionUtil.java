package pg.android.pendex.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pg.android.pendex.beans.Answer;
import pg.android.pendex.beans.PendexRating;
import pg.android.pendex.beans.Question;
import pg.android.pendex.constants.Constants;
import pg.android.pendex.db.File;
import pg.android.pendex.db.enums.ANSWER;
import pg.android.pendex.db.enums.QUESTION;
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

    private static boolean done = false;
    private static String linked;
    private static Question selectedQuestion;
    private static final List<Question> questions = new ArrayList<Question>();
    private static final Map<String, Question> questionsMap = new HashMap<String, Question>();

    private static final int QUESTIONS_TO_REFRESH = 1;
    private static final int MAX_QUESTIONS_LOADED = 1;

    /**
     * Resets the done flag to false and all static data.
     */
    public static void resetQuestions() {
        done = false;
        linked = null;
        selectedQuestion = null;
        questions.clear();
        questionsMap.clear();
    }

    public static Question getRandomQuestion(final Context context) throws QuestionsLoadException,
            OutOfQuestionsException {

        if (done) {
            throw new OutOfQuestionsException();
        }

        if (linked != null) {
            return selectedQuestion;
        }

        if (questions.isEmpty() || questions.size() < QUESTIONS_TO_REFRESH) {
            loadQuestions(context);
        }

        // Sanity check because you could be done.
        if (questions.isEmpty()) {
            selectedQuestion = null;
            done = true;
            throw new OutOfQuestionsException();
        }

        int index = Constants.FIRST;
        final int max = questions.size();
        Question q = questions.get(index);

        while (!q.getParentId().isEmpty() && !ProfileUtil.hasAnswered(q.getParentId())
                && index < max) {
            index++;
            q = questions.get(index);
        }

        setQuestion(q);
        return q;

    }

    private static void loadQuestions(final Context context) throws QuestionsLoadException {

        try {

            final JSONArray array = new JSONArray(File.loadQuestionsFromFile(context));
            int added = 0;

            for (int i = 0; i < array.length(); i++) {

                final JSONObject object = array.getJSONObject(i);
                final String id = object.getString("id");

                if (added >= MAX_QUESTIONS_LOADED) {
                    break;
                }

                if (!ProfileUtil.hasAnswered(id)) {
                    final Question q = convertJsonToQuestion(object);

                    questions.add(q);
                    questionsMap.put(id, q);

                    added++;

                    final String linked1 = q.getAnswers().get(0).getLinked();
                    final String linked2 = q.getAnswers().get(1).getLinked();

                    if (!linked1.isEmpty()) {
                        added--;
                    }

                    if (!linked2.isEmpty()) {
                        added--;
                    }

                }

            }

            Collections.shuffle(questions);

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

        final String id = object.getString(QUESTION.Id.getName());
        final String parentId = object.getString(QUESTION.ParentId.getName());
        final String questionText = object.getString(QUESTION.Question.getName());
        final Integer random = object.getInt(QUESTION.Random.getName());

        question.setId(id);
        question.setParentId(parentId);
        question.setRandom(random);
        question.setQuestion(questionText);

        final JSONArray answers = object.getJSONArray(QUESTION.Answers.getName());
        final List<Answer> answersList = new ArrayList<Answer>();

        for (int i = 0; i < answers.length(); i++) {

            final JSONObject ans = answers.getJSONObject(i);

            final String answerText = ans.getString(ANSWER.Answer.getName());
            final String achievement = ans.getString(ANSWER.Achievement.getName());
            final String linkedType = ans.getString(ANSWER.LinkedType.getName());
            final String linked = ans.getString(ANSWER.Linked.getName());

            final JSONObject pendex = ans.getJSONObject(ANSWER.Pendex.getName());

            final Answer answer = new Answer();

            answer.setAnswer(answerText);
            answer.setAchievement(achievement);
            answer.setLinkedType(linkedType);
            answer.setLinked(linked);

            final PendexRating pendexRating = new PendexRating();

            pendexRating.setPendex(JsonUtil.createPendexMapFromJson(pendex));

            answer.setPendexRating(pendexRating);

            answersList.add(answer);

        }

        question.setAnswers(answersList);

        return question;
    }

    public static void removeLinked() {
        linked = null;
    }

    /**
     * Removes the current question from the list and map and sets the current question.
     * 
     * @param q - {@link Question} - Questions to set.
     */
    private static void setQuestion(final Question q) {
        removeQuestion(q);
        selectedQuestion = q;
    }

    /**
     * Removes the question from the map and list.
     * 
     * @param q - {@link Question} - Question to remove.
     */
    public static void removeQuestion(final Question q) {
        questions.remove(q);
        questionsMap.remove(q.getId());
    }

    /**
     * Sets the current question to the input id.
     * 
     * @param id - String - Id of the question.
     * 
     */
    public static void setQuestionById(final String id) {
        final Question question = questionsMap.get(id);
        // Now set the current question.
        setQuestion(question);
        linked = id;
    }

    /**
     * Removes the question from the map and list.
     * 
     * @param id - String - Id of the question.
     * 
     * @return List&lt;String&gt; - The questions if there are links this will be more than 1.
     */
    public static List<String> removeQuestionById(final String id) {
        final List<String> removed = new ArrayList<String>();
        final Question question = questionsMap.get(id);
        removeQuestion(question);
        removed.add(id);

        final Answer answer1 = question.getAnswers().get(0);
        final Answer answer2 = question.getAnswers().get(1);

        if (!answer1.getLinked().isEmpty()) {
            removed.addAll(removeQuestionById(answer1.getLinked()));
        }

        if (!answer2.getLinked().isEmpty()) {
            removed.addAll(removeQuestionById(answer2.getLinked()));
        }

        return removed;
    }

    /**
     * Returns the question by id.
     * 
     * @param id - String - Id of the question.
     * 
     * @return {@link Question} - Question or null if not found.
     */
    public static Question getQuestionById(final String id) {
        return questionsMap.get(id);
    }

    public static Question getSelectedQuestion() {
        return selectedQuestion;
    }

}
