package pg.android.pendex.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pg.android.pendex.beans.Achievement;
import pg.android.pendex.constants.Assets;
import pg.android.pendex.db.File;
import pg.android.pendex.db.enums.ACHIEVEMENT;
import pg.android.pendex.exceptions.achievement.AchievementLoadException;
import pg.android.pendex.exceptions.achievement.AchievementSaveException;
import android.content.Context;

/**
 * Achievement util for pulling back achievments.
 * 
 * @author Sora
 * 
 */
public class AchievementUtil {

    private static boolean loaded = false;

    private static int totalAchievementPoints = 0;

    private static List<Achievement> achievements = new ArrayList<Achievement>();

    private static final Map<String, String> achievementSummaryMap = new HashMap<String, String>();

    private static final String NO_SUMMAY_TEXT = "";

    private static final String ACHIEVEMENT_FILENAME_SUFFIX = "-achievement.json";

    private static void loadAchievementSummaryMap(final Context context)
            throws AchievementLoadException {

        try {

            final JSONArray array =
                    new JSONArray(File.loadAssetsFileJSON(context, Assets.ACHIEVEMENTS_JSON));

            for (int i = 0; i < array.length(); i++) {

                final JSONObject object = array.getJSONObject(i);
                final String trait = JsonUtil.getString(object, "achievement");
                final String summary = JsonUtil.getString(object, "summary");

                if (summary.length() > 0) {
                    achievementSummaryMap.put(trait, summary);
                }

            }

        } catch (final JSONException e) {

            e.printStackTrace();
            throw new AchievementLoadException();

        } catch (final IOException e) {

            e.printStackTrace();
            throw new AchievementLoadException();

        }

    }

    public static void loadAchievements(final Context context) throws AchievementLoadException,
            AchievementSaveException {

        achievements = new ArrayList<Achievement>();
        totalAchievementPoints = 0;

        try {

            final JSONArray array =
                    new JSONArray(File.loadInternalFileJSON(context, getAchievementFileName()));

            for (int i = 0; i < array.length(); i++) {

                final Achievement achievement = new Achievement();

                final JSONObject object = array.getJSONObject(i);

                final String name = object.getString(ACHIEVEMENT.Achievement.getName());
                final String date = object.getString(ACHIEVEMENT.Date.getName());
                final int value = object.getInt(ACHIEVEMENT.Value.getName());

                achievement.setAchievement(name);
                achievement.setDate(date);
                achievement.setValue(value);

                achievements.add(achievement);
                totalAchievementPoints += value;

            }

        } catch (final FileNotFoundException e) {

            saveAchievement(context);

        } catch (final JSONException e) {

            e.printStackTrace();
            throw new AchievementLoadException();

        } catch (final IOException e) {

            e.printStackTrace();
            throw new AchievementLoadException();

        }

    }

    /**
     * Saves the profiles throws an IO exception when anything fails.
     * 
     * @param context - {@link Context} - Usually the application context.
     * 
     * @throws IOException - {@link IOException} - when saving fails.
     */
    public static void saveAchievement(final Context context) throws AchievementSaveException {


        final JSONArray jsonArray = new JSONArray();

        // Now save.
        try {

            for (final Achievement achieve : achievements) {

                final Map<String, Object> map = new HashMap<String, Object>();

                map.put(ACHIEVEMENT.Achievement.getName(), achieve.getAchievement());
                map.put(ACHIEVEMENT.Date.getName(), achieve.getDate());
                map.put(ACHIEVEMENT.Value.getName(), achieve.getValue());

                final JSONObject obj = new JSONObject(map);

                jsonArray.put(obj);

            }

            File.storeInternalFileJSON(context, getAchievementFileName(), jsonArray);

        } catch (final IOException e) {

            e.printStackTrace();
            throw new AchievementSaveException();

        }

    }

    /**
     * Returns the summary and loads the summary from the context if not loaded.
     * 
     * @param context - {@link Context} - Where to get the summary.
     * @param achievement - String - Which achievements to get the summary for.
     * 
     * @return String - Summary.
     * @throws AchievementLoadException - Thrown if achievements summary fails to load.
     */
    public static String getAchievementSummary(final Context context, final String achievement)
            throws AchievementLoadException {

        if (!loaded) {
            loadAchievementSummaryMap(context);
        }

        if (achievementSummaryMap.containsKey(achievement)) {
            return achievementSummaryMap.get(achievement);
        }

        return NO_SUMMAY_TEXT;

    }

    public static void addAchievements(final String... arr) {

        for (final String achievement : arr) {

            final Achievement achievementToAdd = new Achievement();

            achievementToAdd.setAchievement(achievement);

            if (achievements.contains(achievementToAdd)) {
                continue;
            }

            achievementToAdd
                    .setDate(FormatUtil.getDateSimple(new Date(System.currentTimeMillis())));
            achievementToAdd.setValue(1);

            achievements.add(achievementToAdd);

        }

    }

    public static void removeAchievements(final Context context, final String profileId) {

        File.deleteInternalFile(context, getAchievementFileName(profileId));

    }

    public static List<Achievement> getAchievements(final Context context) {
        return achievements;
    }

    public static int getTotalAchievementPoints() {
        return totalAchievementPoints;
    }

    public static String getAchievementFileName(final String profileId) {
        return profileId + ACHIEVEMENT_FILENAME_SUFFIX;
    }

    public static String getAchievementFileName() {
        return ProfileUtil.getProfileId() + ACHIEVEMENT_FILENAME_SUFFIX;
    }

}
