package pg.android.pendex.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pg.android.pendex.beans.Like;
import pg.android.pendex.db.File;
import pg.android.pendex.db.enums.LIKE;
import pg.android.pendex.exceptions.like.LikeLoadException;
import pg.android.pendex.exceptions.like.LikeSaveException;
import android.content.Context;
import android.util.Log;

/**
 * Achievement util for pulling back achievments.
 * 
 * @author Sora
 * 
 */
public class LikeUtil {

    private static final String TAG = "LikeUtil";

    private static List<Like> likes = new ArrayList<Like>();

    private static final String LIKE_FILENAME_SUFFIX = "-likes.json";

    /**
     * Loads the likes.
     * 
     * @param context - {@link Context} - Load context for the files.
     * 
     * @throws LikeLoadException - Thrown if the load fails.
     * @throws LikeSaveException - Thrown if there is not a save file and the initial save fails.
     */
    public static void loadLikes(final Context context) throws LikeLoadException, LikeSaveException {

        likes = new ArrayList<Like>();

        try {

            final JSONArray array =
                    new JSONArray(File.loadInternalFileJSON(context, getLikeFileName()));

            for (int i = 0; i < array.length(); i++) {

                final Like like = new Like();

                final JSONObject object = array.getJSONObject(i);

                like.setLike(JsonUtil.getString(object, LIKE.Like.getName()));
                like.setLikeMap(JsonUtil.createStringIntMapFromJson(object, LIKE.LikeMap.getName()));

                likes.add(like);

            }

        } catch (final FileNotFoundException e) {

            saveLikes(context);

        } catch (final JSONException e) {

            Log.e(TAG, "Field did not load correctly.");
            throw new LikeLoadException();

        } catch (final IOException e) {

            Log.e(TAG, "File failed to load.");
            throw new LikeLoadException();

        }

    }

    /**
     * Save the likes.
     * 
     * @param context - {@link Context} - Load context for the files.
     * 
     * @throws LikeSaveException - Thrown if the save fails.
     */
    public static void saveLikes(final Context context) throws LikeSaveException {


        final JSONArray jsonArray = new JSONArray();

        // Now save.
        try {

            for (final Like like : likes) {

                final Map<String, Object> map = new HashMap<String, Object>();

                map.put(LIKE.Like.getName(), like.getLike());

                final JSONObject obj = new JSONObject(map);

                // This could throw a json exception.
                obj.put(LIKE.LikeMap.getName(), new JSONObject(like.getLikeMap()));

                jsonArray.put(obj);

            }

            File.storeInternalFileJSON(context, getLikeFileName(), jsonArray);

        } catch (final IOException e) {

            Log.e(TAG, "File failed to save.");
            throw new LikeSaveException();

        } catch (final JSONException e) {

            Log.e(TAG, "File failed convert the like map to json object");
            throw new LikeSaveException();

        }

    }

    /**
     * Increments the like map for the like.
     * 
     * @param likeType - String - Type of like.
     * @param value - String - Value that is preferred to others.
     */
    public static void addLike(final String likeType, final String value) {

        Like like = new Like();

        like.setLike(likeType);

        final int indexOf = likes.indexOf(like);

        if (indexOf != -1) {

            like = likes.get(indexOf);
            final Map<String, Integer> likeMap = like.getLikeMap();

            if (likeMap.containsKey(value)) {
                likeMap.put(value, likeMap.get(value) + 1);
            } else {
                likeMap.put(value, 1);
            }

        } else {
            final Map<String, Integer> likeMap = new HashMap<String, Integer>();
            likeMap.put(value, 1);
            like.setLikeMap(likeMap);
            likes.add(like);
        }

    }

    /**
     * Deletes the like file.
     * 
     * @param context - {@link Context} - Context of file.
     * @param profileId - String - Profile the file is for.
     */
    public static void removeLikes(final Context context, final String profileId) {
        File.deleteInternalFile(context, getLikeFileName(profileId));
    }

    public static List<Like> getLikes(final Context context) {
        return likes;
    }

    public static String getLikeFileName(final String profileId) {
        return profileId + LIKE_FILENAME_SUFFIX;
    }

    public static String getLikeFileName() {
        return ProfileUtil.getProfileId() + LIKE_FILENAME_SUFFIX;
    }

}
