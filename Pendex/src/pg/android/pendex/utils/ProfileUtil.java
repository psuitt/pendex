package pg.android.pendex.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pg.android.pendex.db.File;
import pg.android.pendex.db.enums.Profile;
import pg.android.pendex.exceptions.ProfileLoadException;
import pg.android.pendex.exceptions.ProfileSaveException;
import android.content.Context;

/**
 * Utility for loading profiles.
 * 
 * @author Sora
 *
 */
public class ProfileUtil {

	private static String loadedProfileId = "default";
	private String loadedProfileName = "default";
	private String loadedProfileFileName = "default.json";
	private static Set<String> answeredIds = new HashSet<String>();
	private Map<String, Integer> pendex = new HashMap<String, Integer>();

	private static final String PROFILE_FILENAME_SUFFIX = "-profile.json";

	public void reset() {
		ProfileUtil.loadedProfileId = "default";
		loadedProfileName = "default";
		loadedProfileFileName = ProfileUtil.getProfileFileName(ProfileUtil.loadedProfileId);
		ProfileUtil.answeredIds = new HashSet<String>();
		pendex = new HashMap<String, Integer>();
	}

	public void loadProfile(final Context context, final String profile) throws ProfileLoadException, ProfileSaveException {

		ProfileUtil.loadedProfileId = profile;
		loadedProfileFileName = ProfileUtil.getProfileFileName(ProfileUtil.loadedProfileId);
		ProfileUtil.answeredIds = new HashSet<String>();
		pendex = new HashMap<String, Integer>();

		try {

			final JSONObject profileObj = new JSONObject(File.loadFileJSON(context, loadedProfileFileName));

			loadedProfileName = profileObj.getString(Profile.Name.name);

			final JSONArray answers = profileObj.getJSONArray(Profile.Answers.name);

			for (int i = 0 ; i < answers.length(); i++) {

				final String answer = answers.getString(i);

				ProfileUtil.answeredIds.add(answer);

			}

			final JSONObject pendexJson = profileObj.getJSONObject(Profile.Pendex.name);

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
	 * @param context - {@link Context} - Usually the application context.
	 * 
	 * @throws IOException - {@link IOException} - when saving fails.
	 */
	public void saveProfile(final Context context) throws ProfileSaveException {

		final Map<String, Object> map = new HashMap<String, Object>();

		map.put(Profile.Id.name, ProfileUtil.loadedProfileId);
		map.put(Profile.Name.name, loadedProfileName);
		map.put(Profile.Answers.name, ProfileUtil.answeredIds.toArray());
		map.put(Profile.Pendex.name, pendex);

		final JSONObject jsonObject = new JSONObject(map);

		try {

			File.storeSettingsToFile(context, loadedProfileFileName, jsonObject);

		} catch (final IOException e) {

			e.printStackTrace();
			throw new ProfileSaveException();

		}

	}

	public static String getProfileName() {
		return ProfileUtil.loadedProfileId;
	}

	public static boolean hasAnswered(final String s) {
		return ProfileUtil.answeredIds.contains(s);
	}

	public static String getProfileFileName(final String s) {
		return s.trim() + ProfileUtil.PROFILE_FILENAME_SUFFIX;
	}

}
