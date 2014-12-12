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
    private static Question selectedQuestion;
    private static final List<Question> questions = new ArrayList<Question>();
    private static final Map<String, Question> questionsMap = new HashMap<String, Question>();

    private static final int QUESTIONS_TO_REFRESH = 8;
    private static final int MAX_QUESTIONS_LOADED = 16;

    /**
     * Resets the done flag to false and all static data.
     */
    public static void resetQuestions() {
        done = false;
        selectedQuestion = null;
        clearQuestions();
    }

    /**
     * Returns a random question be sure to {@link #removeQuestionById(String)} when answered.
     * 
     * @param context - {@link Context} - Where to get the assets.
     * 
     * @throws QuestionsLoadException - Thrown if the questions if they do not load.
     * @throws OutOfQuestionsException - Thrown if there are no more questions to answer.
     */
    public static Question getRandomQuestion(final Context context) throws QuestionsLoadException,
            OutOfQuestionsException {

        // This will only occur after finishing the questions.
        if (done) {
            throw new OutOfQuestionsException();
        }

        if (selectedQuestion != null) {
            return selectedQuestion;
        }

        reloadQuestions(context);

        int index = Constants.FIRST;
        final int max = questions.size();
        Question q = questions.get(index);

        /*
         * If there is a parent and you have not answered it yet skip this till you get to the next
         * available question.
         */
        while (!q.getParentId().isEmpty() && !ProfileUtil.hasAnswered(q.getParentId())
                && index < max) {
            index++;
            q = questions.get(index);
        }

        setQuestion(q);
        return q;

    }

    /**
     * Reloads the questions if the questions are less than the refresh number.
     * 
     * @param context - {@link Context} - Where to get the assets.
     * 
     * @throws QuestionsLoadException - Thrown if the questions if they do not load.
     * @throws OutOfQuestionsException - Thrown if there are no more questions to answer.
     */
    private static void reloadQuestions(final Context context) throws QuestionsLoadException,
            OutOfQuestionsException {
        if (questions.size() < QUESTIONS_TO_REFRESH) {

            loadQuestions(context);

            // Sanity check because you could be done.
            if (questions.isEmpty()) {
                selectedQuestion = null;
                done = true;
                throw new OutOfQuestionsException();
            }

            // Shuffle the sortable list.
            Collections.shuffle(questions);

        }
    }

    /**
     * Loads questions from the context.
     * 
     * @param context - {@link Context} - Where to get the assets.
     * 
     * @throws QuestionsLoadException - Thrown if the questions if they do not load.
     * @throws OutOfQuestionsException - Thrown if there are no more questions to answer.
     */
    private static void loadQuestions(final Context context) throws QuestionsLoadException {
        loadQuestions(context, null);
    }

    /**
     * Loads questions from the context.
     * 
     * @param context - {@link Context} - Where to get the assets.
     * @param s - String - Id if you want to load a specific question.
     * 
     * @throws QuestionsLoadException - Thrown if the questions if they do not load.
     * @throws OutOfQuestionsException - Thrown if there are no more questions to answer.
     */
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

                addQuestion(q);

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

    /**
     * Converts {@link JSONObject}s to a question bean.
     * 
     * @param object - {@link JSONObject} - Question json object.
     * 
     * @return {@link Question} - Question bean.
     * 
     * @throws JSONException - Thrown if the question can't find a field in the json object.
     */
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

    /**
     * Removes the bad words from the question and answers.
     * 
     * @param question - {@link Question} - Question to filter.
     */
    private static void removeBadWordsQuestion(final Question question) {
        question.setQuestion(SafeUtil.secureString(question.getQuestion()));
        for (final Answer answer : question.getAnswers()) {
            answer.setAnswer(SafeUtil.secureString(answer.getAnswer()));
        }
    }

    /**
     * Sets the current question.
     * 
     * @param q - {@link Question} - Questions to set.
     */
    private static void setQuestion(final Question q) {
        selectedQuestion = q;
        if (ProfileUtil.isSafe()) {
            removeBadWordsQuestion(selectedQuestion);
        }
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
    }

    /**
     * Removes the question from the map and list, and also if there are linked questions those are
     * also removed. This is to be called when a question is answered.
     * 
     * @param id - String - Id of the question.
     * 
     * @return List&lt;String&gt; - The questions if there are links this will be more than 1.
     */
    public static List<String> removeQuestionById(final String id) {

        final List<String> removed = new ArrayList<String>();
        final Question question = questionsMap.get(id);

        // Add the current id.
        removed.add(id);

        if (question == null) {
            Log.e(TAG, "This id is not in the questions list. [id=" + id + "]");
            return removed;
        }

        // Remove the question
        removeQuestion(question);

        for (final Answer answer : question.getAnswers()) {
            if (!answer.getLinked().isEmpty()) {
                removed.addAll(removeQuestionById(answer.getLinked()));
            }
        }

        return removed;

    }

    /**
     * Adds input question to both collections.
     * 
     * @param q - {@link Question} - Question to add.
     */
    private static void addQuestion(final Question q) {
        questions.add(q);
        questionsMap.put(q.getId(), q);
    }

    /**
     * Clears the question from both the map and list.
     */
    private static void clearQuestions() {
        questions.clear();
        questionsMap.clear();
    }

    /**
     * Removes the selected question from the list and map.
     * 
     */
    public static void removeSelectedQuestion() {
        removeQuestion(selectedQuestion);
    }

    /**
     * Removes the question from the map and list.
     * 
     * @param q - {@link Question} - Question to remove.
     */
    private static void removeQuestion(final Question q) {
        questions.remove(q);
        questionsMap.remove(q.getId());
    }

    /**
     * Sets the selected question to null. When null {@link #getRandomQuestion(Context)} will return
     * the next random question.
     */
    public static void clearSelectedQuestion() {
        selectedQuestion = null;
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

    /**
     * Returns the inverse of the answered question.
     * 
     * @param indexOfAnswer - int - Current question index.
     * @param selectedQuestion - {@link Answer} - Question to get the not answered.
     * 
     * @return {@link Answer} - Current not answered option in the question.
     */
    public static Answer getNotAnswered(final int indexOfAnswer, final Question selectedQuestion) {
        Answer notAnswered;
        if (indexOfAnswer == 0) {
            notAnswered = selectedQuestion.getAnswers().get(1);
        } else {
            notAnswered = selectedQuestion.getAnswers().get(0);
        }
        return notAnswered;
    }

}
