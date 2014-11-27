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

    private static List<Achievement> achievements = new ArrayList<Achievement>();

    private static final Map<String, String> achievementSummaryMap = new HashMap<String, String>();

    private static final String NO_SUMMAY_TEXT =
            "A description of this achievement is not available please add one.";

    private static final String ACHIEVEMENT_FILENAME_SUFFIX = "-achievement.json";

    public static void loadAchievements(final Context context) throws AchievementLoadException,
            AchievementSaveException {

        achievements = new ArrayList<Achievement>();

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


        final JSONArray jsonObject = new JSONArray(achievements);

        // Now save.
        try {

            File.storeInternalFileJSON(context, ProfileUtil.getProfileFileName(), jsonObject);

        } catch (final IOException e) {

            e.printStackTrace();
            throw new AchievementSaveException();

        }

    }

    public static String getAchievementSummary(final String achievement) {

        if (achievementSummaryMap.containsKey(achievement)) {
            return achievementSummaryMap.get(achievement);
        }

        return NO_SUMMAY_TEXT;

    }

    public static void addAchievements(final String... achievements) {

        for (final String achievement : achievements) {

            final Achievement achievementToAdd = new Achievement();

            achievementToAdd.setAchievement(achievement);
            achievementToAdd.setDate(FormatUtil.getDateSimple(new Date()));
            achievementToAdd.setValue(1);

        }

    }

    public static List<Achievement> getAchievements(final Context context) {
        return achievements;
    }

    public static String getAchievementFileName() {
        return ProfileUtil.getProfileId() + ACHIEVEMENT_FILENAME_SUFFIX;
    }

}
