package pg.android.pendex.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;

import pg.android.pendex.beans.Answer;
import pg.android.pendex.beans.PendexRating;
import pg.android.pendex.beans.Question;
import pg.android.pendex.beans.Trait;
import pg.android.pendex.comparators.TraitsAlphaComparator;
import pg.android.pendex.db.File;
import pg.android.pendex.db.enums.Profile;
import pg.android.pendex.exceptions.ProfileLoadException;
import pg.android.pendex.exceptions.ProfileResetException;
import pg.android.pendex.exceptions.ProfileSaveException;
import android.content.Context;

/**
 * Utility for loading profiles.
 * 
 * @author Sora
 * 
 */
public final class ProfileUtil {

	private static String loadedProfileId = "default";
	private static String loadedProfileName = "default";
	private static Map<String, Integer> answeredQuestions = new HashMap<String, Integer>();
	private static Map<String, Integer> pendex = new HashMap<String, Integer>();

	private static final String PROFILE_FILENAME_SUFFIX = "-profile.json";

	public static void reset() {
		loadedProfileId = "default";
		loadedProfileName = "default";
		answeredQuestions = new HashMap<String, Integer>();
		pendex = new HashMap<String, Integer>();
	}

	public static void loadProfile(final Context context, final String profile)
			throws ProfileLoadException, ProfileSaveException {

		loadedProfileId = profile;
		answeredQuestions = new HashMap<String, Integer>();
		pendex = new HashMap<String, Integer>();

		try {

			final JSONObject profileObj = new JSONObject(
					File.loadInternalFileJSON(context,
							ProfileUtil.getProfileFileName(loadedProfileId)));

			loadedProfileName = profileObj.getString(Profile.Name.name);

			final JSONObject answers = profileObj
					.getJSONObject(Profile.Answers.name);

			answeredQuestions.putAll(JsonUtil.createPendexMapFromJson(answers));

			final JSONObject pendexJson = profileObj
					.getJSONObject(Profile.Pendex.name);

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

	}

	/**
	 * Saves the profiles throws an IO exception when anything fails.
	 * 
	 * @param context
	 *            - {@link Context} - Usually the application context.
	 * 
	 * @throws IOException
	 *             - {@link IOException} - when saving fails.
	 */
	public static void saveProfile(final Context context)
			throws ProfileSaveException {

		final Map<String, Object> map = new HashMap<String, Object>();

		map.put(Profile.Id.name, loadedProfileId);
		map.put(Profile.Name.name, loadedProfileName);

		final JSONObject jsonObject = new JSONObject(map);

		// Add the answers and pendex.
		try {

			jsonObject.put(Profile.Answers.name, new JSONObject(
					answeredQuestions));
			jsonObject.put(Profile.Pendex.name, new JSONObject(pendex));

		} catch (final JSONException e) {

			e.printStackTrace();
			throw new ProfileSaveException();
		}

		// Now save.
		try {

			File.storeInternalFileJSON(context,
					ProfileUtil.getProfileFileName(loadedProfileId), jsonObject);

		} catch (final IOException e) {

			e.printStackTrace();
			throw new ProfileSaveException();

		}

	}

	/**
	 * Resets the loaded profile and saves it.
	 * 
	 * @param context
	 *            - {@link Context} - Usually the application context.
	 * 
	 * @throws ProfileResetException
	 *             - Thrown if the profile fails to save.
	 */
	public static void resetLoadedProfile(final Context context)
			throws ProfileResetException {

		reset();
		try {
			saveProfile(context);
		} catch (final ProfileSaveException e) {

			e.printStackTrace();
			throw new ProfileResetException();

		}

	}

	public static void answerQuestion(final int indexOfAnswer) {

		final Question selectedQuestion = QuestionUtil.getSelectedQuestion();

		// No question to answer.
		if (selectedQuestion == null) {
			return;
		}

		final Answer answer = selectedQuestion.getAnswers().get(indexOfAnswer);
		final PendexRating pendexRating = answer.getPendexRating();

		answeredQuestions.put(selectedQuestion.getId(), indexOfAnswer);

		for (final Entry<String, Integer> entry : pendexRating.getPendex()
				.entrySet()) {

			if (pendex.containsKey(entry.getKey())) {
				pendex.put(entry.getKey(),
						pendex.get(entry.getKey()) + entry.getValue());
			} else {
				pendex.put(entry.getKey(), entry.getValue());
			}

		}

	}

	public static List<Trait> getPendexTraits() {

		final List<Trait> traits = new ArrayList<Trait>();

		for (final Entry<String, Integer> entry : pendex.entrySet()) {
			final Trait trait = new Trait();

			trait.setTrait(entry.getKey());
			trait.setTraitValue(entry.getValue());
			trait.setSummary(TraitUtil.getTraitSummay(entry.getKey()));

			traits.add(trait);

		}

		Collections.sort(traits, new TraitsAlphaComparator());

		return traits;

	}

	public static String getProfileName() {
		return loadedProfileId;
	}

	public static boolean hasAnswered(final String s) {
		return answeredQuestions.containsKey(s);
	}

	public static String getProfileFileName(final String s) {
		return s.trim() + PROFILE_FILENAME_SUFFIX;
	}

}
