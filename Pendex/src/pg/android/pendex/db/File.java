package pg.android.pendex.db;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import pg.android.pendex.constants.Assets;
import pg.android.pendex.constants.Constants;
import pg.android.pendex.utils.Utils;
import android.content.Context;

/**
 * Reads files from the local storage.
 * 
 * @author Sora
 * 
 */
public final class File {

    private static String[] questionFiles;

    public static void storeInternalFileJSON(final Context context, final String fileName,
            final JSONObject jsonObject) throws IOException {

        // MODE_PRIVATE will create the file (or replace a file of the same
        // name) and make it private to your application.
        final FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
        fos.write(jsonObject.toString().getBytes());
        fos.close();

    }

    public static void storeInternalFileJSON(final Context context, final String fileName,
            final JSONArray jsonArray) throws IOException {

        // MODE_PRIVATE will create the file (or replace a file of the same
        // name) and make it private to your application.
        final FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
        fos.write(jsonArray.toString().getBytes());
        fos.close();

    }

    /**
     * This will pick a random file and pull all data.
     * 
     * @param context - {@link Context} - Context where assets are.
     * @return String - String object from file this is usually a jsonObject.
     * @throws IOException
     */
    public static String loadQuestionsFromFile(final Context context) throws IOException {

        return loadQuestionsFromFile(context, new ArrayList<String>());

    }

    /**
     * This will pick a random file and pull all data.
     * 
     * @param context - {@link Context} - Context where assets are.
     * @param toSkip - List&lt;String&gt; - Question filenames to skip.
     * 
     * @return String - String object from file this is usually a jsonObject.
     * 
     * @throws IOException
     */
    public static String loadQuestionsFromFile(final Context context, final List<String> toSkip)
            throws IOException {

        // TODO: Track question files when completed.
        loadFiles(context);

        final List<String> filesToRandom = new ArrayList<String>();

        for (final String fileName : questionFiles) {
            if (!toSkip.contains(fileName)) {
                filesToRandom.add(fileName);
            }
        }

        final StringBuilder sb = new StringBuilder();

        sb.append(Assets.PATH_QUESTIONS);
        sb.append(Assets.PATH_DELIMITER);
        sb.append(Utils.randomFromList(filesToRandom));

        return File.loadAssetsFileJSON(context, sb.toString());

    }

    private static void loadFiles(final Context context) throws IOException {
        if (questionFiles == null) {
            questionFiles = context.getAssets().list(Assets.PATH_QUESTIONS);
        }
    }

    public static String loadInternalFileJSON(final Context context, final String fileName)
            throws IOException {

        // Get the file input steam.
        final InputStream is = context.openFileInput(fileName);

        final int size = is.available();

        final byte[] buffer = new byte[size];

        // Read to buffer
        is.read(buffer);

        // Close the input stream.
        is.close();

        return new String(buffer, Constants.STRING_UTF_8);

    }

    public static String loadAssetsFileJSON(final Context context, final String fileName)
            throws IOException {

        // Get the file input steam.
        final InputStream is = context.getAssets().open(fileName);

        final int size = is.available();

        final byte[] buffer = new byte[size];

        // Read to buffer
        is.read(buffer);

        // Close the input stream.
        is.close();

        return new String(buffer, Constants.STRING_UTF_8);

    }

    public static void deleteInternalFile(final Context context, final String fileName) {

        context.deleteFile(fileName);

    }

    /**
     * Returns the length of the questions assets.
     * 
     * @return int - Length of the question files.
     */
    public static int getQuestionFilesLength() {
        return questionFiles.length;
    }


}
