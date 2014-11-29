package pg.android.pendex.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;

import pg.android.pendex.beans.Answer;
import pg.android.pendex.beans.PendexRating;
import pg.android.pendex.beans.Question;
import pg.android.pendex.beans.Trait;
import pg.android.pendex.comparators.TraitsAlphaComparator;
import pg.android.pendex.constants.Constants;
import pg.android.pendex.constants.Preferences;
import pg.android.pendex.db.File;
import pg.android.pendex.db.enums.PROFILE;
import pg.android.pendex.exceptions.TraitLoadException;
import pg.android.pendex.exceptions.achievement.AchievementLoadException;
import pg.android.pendex.exceptions.achievement.AchievementSaveException;
import pg.android.pendex.exceptions.profile.ProfileExistsException;
import pg.android.pendex.exceptions.profile.ProfileLoadException;
import pg.android.pendex.exceptions.profile.ProfileResetException;
import pg.android.pendex.exceptions.profile.ProfileSaveException;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Utility for loading profiles.
 * 
 * @author Sora
 * 
 */
public final class ProfileUtil {

    private static boolean profileReload = true;
    private static String loadedProfileId = "default";
    private static String loadedProfileName = "default";
    private static Date created = new Date(System.currentTimeMillis());
    private static String lastAnswered = "Start answering questions!";
    private static Map<String, Integer> answeredQuestions = new HashMap<String, Integer>();
    private static Map<String, Integer> pendex = new HashMap<String, Integer>();

    private static final String PROFILE_FILENAME_SUFFIX = "-profile.json";

    public static List<String> getProfilesList(final Context context) {

        final List<String> list = new ArrayList<String>();

        for (final String fileName : context.fileList()) {
            if (!fileName.contains(PROFILE_FILENAME_SUFFIX)) {
                continue;
            }
            final String profileName = fileName.replace(PROFILE_FILENAME_SUFFIX, "");
            if (!fileName.equals(getProfileFileName())) {
                list.add(profileName);
            } else {
                list.add(0, profileName);
            }

        }

        return list;

    }

    public static void reset() {
        reset(loadedProfileId);
    }

    /**
     * This only resets the profile id, name, answered questions, and pendex.
     * 
     * @param profileId - String - Profile id.
     */
    public static void reset(final String profileId) {
        loadedProfileId = profileId;
        loadedProfileName = profileId;
        answeredQuestions = new HashMap<String, Integer>();
        pendex = new HashMap<String, Integer>();
    }

    public static void createProfile(final Context context, final String profile)
            throws ProfileExistsException, ProfileSaveException {

        if (getProfilesList(context).contains(profile.trim())) {
            throw new ProfileExistsException();
        }

        reset(profile);
        created = new Date(System.currentTimeMillis());
        saveProfile(context);

    }

    public static void loadProfile(final Context context) throws ProfileLoadException,
            ProfileSaveException {

        if (!profileReload) {
            return;
        }

        final SharedPreferences settings =
                context.getSharedPreferences(Preferences.PENDEX_PREFERENCES, Context.MODE_PRIVATE);

        final String loadedProfileId =
                settings.getString(Preferences.LAST_PROFILE_ID_STRING, "default");

        loadProfile(context, loadedProfileId);

    }

    public static void loadProfile(final Context context, final String profile)
            throws ProfileLoadException, ProfileSaveException {

        loadedProfileId = profile;
        answeredQuestions = new HashMap<String, Integer>();
        pendex = new HashMap<String, Integer>();

        try {

            final JSONObject profileObj =
                    new JSONObject(File.loadInternalFileJSON(context,
                            ProfileUtil.getProfileFileName()));

            loadedProfileName = profileObj.getString(PROFILE.Name.getName());
            created =
                    FormatUtil.getDateFromSimple(profileObj.getString(PROFILE.Created.getName()),
                            Locale.getDefault());
            lastAnswered = profileObj.getString(PROFILE.LastAnswered.getName());

            final JSONObject answers = profileObj.getJSONObject(PROFILE.Answers.getName());

            answeredQuestions.putAll(JsonUtil.createPendexMapFromJson(answers));

            final JSONObject pendexJson = profileObj.getJSONObject(PROFILE.Pendex.getName());

            pendex.putAll(JsonUtil.createPendexMapFromJson(pendexJson));

        } catch (final FileNotFoundException e) {

            saveProfile(context);

        } catch (final JSONException e) {

            e.printStackTrace();
            throw new ProfileLoadException();

        } catch (final IOException e) {

            e.printStackTrace();
            throw new ProfileLoadException();

        }

        try {

            AchievementUtil.loadAchievements(context);

        } catch (final AchievementLoadException e) {

            e.printStackTrace();
            throw new ProfileLoadException();

        } catch (final AchievementSaveException e) {

            e.printStackTrace();
            throw new ProfileLoadException();

        }

        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        final SharedPreferences settings =
                context.getSharedPreferences(Preferences.PENDEX_PREFERENCES, 0);
        final SharedPreferences.Editor editor = settings.edit();
        editor.putString(Preferences.LAST_PROFILE_ID_STRING, ProfileUtil.getProfileId());

        // Commit the edits!
        editor.commit();

        // Reset the loaded questions as to change it up.
        QuestionUtil.resetQuestions();

    }

    /**
     * Saves the profiles throws an IO exception when anything fails.
     * 
     * @param context - {@link Context} - Usually the application context.
     * 
     * @throws IOException - {@link IOException} - when saving fails.
     */
    public static void saveProfile(final Context context) throws ProfileSaveException {

        final Map<String, Object> map = new HashMap<String, Object>();

        map.put(PROFILE.Id.getName(), loadedProfileId);
        map.put(PROFILE.Name.getName(), loadedProfileName);
        map.put(PROFILE.Created.getName(), FormatUtil.getDateSimple(created));
        map.put(PROFILE.LastAnswered.getName(), lastAnswered);

        final JSONObject jsonObject = new JSONObject(map);

        // Add the answers and pendex.
        try {

            jsonObject.put(PROFILE.Answers.getName(), new JSONObject(answeredQuestions));
            jsonObject.put(PROFILE.Pendex.getName(), new JSONObject(pendex));

        } catch (final JSONException e) {

            e.printStackTrace();
            throw new ProfileSaveException();
        }

        // Now save.
        try {

            File.storeInternalFileJSON(context, ProfileUtil.getProfileFileName(), jsonObject);

        } catch (final IOException e) {

            e.printStackTrace();
            throw new ProfileSaveException();

        }

        try {

            AchievementUtil.saveAchievement(context);

        } catch (final AchievementSaveException e) {

            e.printStackTrace();
            throw new ProfileSaveException();

        }

    }

    /**
     * Resets the loaded profile and saves it.
     * 
     * @param context - {@link Context} - Usually the application context.
     * 
     * @throws ProfileResetException - Thrown if the profile fails to save.
     */
    public static void resetLoadedProfile(final Context context) throws ProfileResetException {

        reset();
        try {
            saveProfile(context);
        } catch (final ProfileSaveException e) {

            e.printStackTrace();
            throw new ProfileResetException();

        }

        QuestionUtil.resetQuestions();

    }

    public static void removeProfile(final Context context, final String profileId) {

        if (profileId.contains(".json")) {
            File.deleteInternalFile(context, profileId);
        } else {
            File.deleteInternalFile(context, getProfileFileName(profileId));
        }

        AchievementUtil.removeAchievements(context);

    }

    /**
     * Skips the current question by answering with the skip constant.
     */
    public static void skipQuestion() {
        answerQuestion(Constants.SKIP);
    }

    /**
     * Answers the question with the index of the answer 0 for the first answer.
     * 
     * @param indexOfAnswer - int - 0 Index means the first answer. -1 is the value to skip.
     */
    public static void answerQuestion(final int indexOfAnswer) {

        final Question selectedQuestion = QuestionUtil.getSelectedQuestion();

        // No question to answer.
        if (selectedQuestion == null) {
            return;
        }

        if (Constants.SKIP == indexOfAnswer) {
            answeredQuestions.put(selectedQuestion.getId(), indexOfAnswer);
            return;
        }

        final Answer answer = selectedQuestion.getAnswers().get(indexOfAnswer);
        Answer notAnswered;
        final PendexRating pendexRating = answer.getPendexRating();
        final String question = selectedQuestion.getQuestion();

        if (indexOfAnswer == 0) {
            notAnswered = selectedQuestion.getAnswers().get(1);
        } else {
            notAnswered = selectedQuestion.getAnswers().get(0);
        }


        final StringBuilder lastAnsweredSB = new StringBuilder();

        if (question.isEmpty()) {
            lastAnsweredSB.append("You choose ");
            lastAnsweredSB.append(answer.getAnswer());
            lastAnsweredSB.append(" over ");
            lastAnsweredSB.append(notAnswered.getAnswer());
        } else {
            lastAnsweredSB.append(question);
            lastAnsweredSB.append(" ");
            lastAnsweredSB.append(answer.getAnswer());
        }

        lastAnswered = lastAnsweredSB.toString();

        answeredQuestions.put(selectedQuestion.getId(), indexOfAnswer);

        for (final Entry<String, Integer> entry : pendexRating.getPendex().entrySet()) {

            if (pendex.containsKey(entry.getKey())) {
                pendex.put(entry.getKey(), pendex.get(entry.getKey()) + entry.getValue());
            } else {
                pendex.put(entry.getKey(), entry.getValue());
            }

        }

        // Set the next question to the subsequent question
        if (!answer.getLinked().isEmpty()) {
            QuestionUtil.setQuestionById(answer.getLinked());
        } else {
            QuestionUtil.removeLinked();
        }

        // Skip the not answered.
        if (!notAnswered.getLinked().isEmpty()) {
            final List<String> removed = QuestionUtil.removeQuestionById(notAnswered.getLinked());
            for (final String removedId : removed) {
                answeredQuestions.put(removedId, Constants.SKIP);
            }
        }

        if (!answer.getAchievement().isEmpty()) {
            AchievementUtil.addAchievements(answer.getAchievement());
        }

    }

    public static List<Trait> getPendexTraits(final Context context) throws TraitLoadException {

        final List<Trait> traits = new ArrayList<Trait>();

        for (final Entry<String, Integer> entry : pendex.entrySet()) {
            final Trait trait = new Trait();

            trait.setTrait(entry.getKey());
            trait.setTraitValue(entry.getValue());
            trait.setSummary(TraitUtil.getTraitSummary(context, entry.getKey()));

            traits.add(trait);

        }

        Collections.sort(traits, new TraitsAlphaComparator());

        return traits;

    }

    public static void triggerProfileReload() {
        profileReload = true;
    }

    public static String getProfileId() {
        return loadedProfileId;
    }

    public static String getProfileName() {
        return loadedProfileName;
    }

    public static String getCreatedSimple() {
        return FormatUtil.getDateSimple(created, Locale.getDefault());
    }

    public static String getLastAnswered() {
        return lastAnswered;
    }

    public static boolean hasAnswered(final String s) {
        return answeredQuestions.containsKey(s);
    }

    private static String getProfileFileName() {
        return loadedProfileId + PROFILE_FILENAME_SUFFIX;
    }

    public static String getProfileFileName(final String s) {
        return s.trim() + PROFILE_FILENAME_SUFFIX;
    }

}
