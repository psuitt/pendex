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
import pg.android.pendex.constants.Assets;
import pg.android.pendex.constants.Constants;
import pg.android.pendex.db.File;
import pg.android.pendex.db.enums.ANSWER;
import pg.android.pendex.db.enums.QUESTION;
import pg.android.pendex.exceptions.OutOfQuestionsException;
import pg.android.pendex.exceptions.QuestionsLoadException;
import android.content.Context;
import android.util.Log;

/**
 * Utility for generating questions.
 * 
 * @author Sora
 * 
 */
public final class QuestionUtil {

    private static final String TAG = "QuestionUtil";

    private static boolean done = false;
    private static String linked;
    private static Question selectedQuestion;
    private static final List<Question> questions = new ArrayList<Question>();
    private static final Map<String, Question> questionsMap = new HashMap<String, Question>();

    private static final int QUESTIONS_TO_REFRESH = 10;
    private static final int MAX_QUESTIONS_LOADED = 20;

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

        // This will only occur after finishing the questions.
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

        Collections.shuffle(questions);

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
        loadQuestions(context, null);
    }

    private static void loadQuestions(final Context context, final String s)
            throws QuestionsLoadException {

        try {

            int maxQuestionsToLoad = MAX_QUESTIONS_LOADED;
            int start = 0;
            JSONArray array = null;

            if (s == null) {
                array =
                        new JSONArray(File.loadQuestionsFromFile(context,
                                ProfileUtil.getFinishedLists()));
            } else {
                array =
                        new JSONArray(File.loadAssetsFileJSON(context, Assets.getFileNameFromId(s)));
                maxQuestionsToLoad = 1;
                start = Assets.getIndexFromId(s);
            }

            int added = 0;
            int count = 0;
            final int length = array.length();
            String lastId = Constants.EMPTY_STRING;

            for (int i = start; i < length; i++) {

                count++;

                final JSONObject object = array.getJSONObject(i);
                final String id = object.getString(QUESTION.Id.getName());
                final String parentId = object.getString(QUESTION.ParentId.getName());

                if (added >= maxQuestionsToLoad) {
                    break;
                }

                /*
                 * If the user has answered the question before skip or if there is a parent and the
                 * parent is not in the questions map and they have not already answered the parent
                 * skip this.
                 */
                if (ProfileUtil.hasAnswered(id) || !parentId.isEmpty()
                        && !questionsMap.containsKey(parentId) && !ProfileUtil.hasAnswered(id)) {
                    continue;
                }

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

                lastId = id;

            }

            if (!lastId.isEmpty()) {
                if (count == (length - 1)) {
                    ProfileUtil.addToFinishedLists(Assets.getFileNameFromId(lastId));
                }
                if (added == 0
                        && ProfileUtil.getFinishedLists().size() != File.getQuestionFilesLength()) {
                    // Try a different set of questions.
                    loadQuestions(context);
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

        final String id = object.getString(QUESTION.Id.getName());
        final String parentId = object.getString(QUESTION.ParentId.getName());
        final String questionText = object.getString(QUESTION.Question.getName());
        final String type = JsonUtil.getString(object, QUESTION.Type.getName());
        final Integer random = object.getInt(QUESTION.Random.getName());

        question.setId(id);
        question.setParentId(parentId);
        question.setRandom(random);
        question.setQuestion(questionText);
        question.setType(type);

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

            pendexRating.setPendex(JsonUtil.createStringIntMapFromJson(pendex));

            answer.setPendexRating(pendexRating);

            answersList.add(answer);

        }

        question.setAnswers(answersList);

        return question;
    }


    private static void safeQuestion(final Question question) {
        question.setQuestion(SafeUtil.secureString(question.getQuestion()));
        for (final Answer answer : question.getAnswers()) {
            answer.setAnswer(SafeUtil.secureString(answer.getAnswer()));
        }
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
        if (ProfileUtil.isSafe()) {
            safeQuestion(selectedQuestion);
        }
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
     * @throws QuestionsLoadException - Thrown if cant find the question.
     * 
     */
    public static void setQuestionById(final Context context, final String id)
            throws QuestionsLoadException {
        Question question = questionsMap.get(id);

        if (question == null) {
            loadQuestions(context, id);
            question = questionsMap.get(id);
        }

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

        removed.add(id);

        if (question == null) {
            Log.e(TAG, "This id is not in the questions list. [id=" + id + "]");
            return removed;
        }

        removeQuestion(question);


        for (final Answer answer : question.getAnswers()) {
            if (!answer.getLinked().isEmpty()) {
                removed.addAll(removeQuestionById(answer.getLinked()));
            }
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
