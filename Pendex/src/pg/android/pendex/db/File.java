package pg.android.pendex.db;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONObject;

import pg.android.pendex.constants.Assets;
import pg.android.pendex.constants.Constants;
import android.content.Context;

/**
 * Reads files from the local storage.
 * 
 * @author Sora
 * 
 */
public final class File {

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

    public static String loadQuestionsFromFile(final Context context) throws IOException {

        return File.loadAssetsFileJSON(context, Assets.QUESTIONS_JSON);

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


}
