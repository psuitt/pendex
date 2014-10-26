package pg.android.pendex.db;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.json.JSONObject;

import android.content.Context;

/**
 * Reads files from the local storage.
 * 
 * @author Sora
 *
 */
public final class File {

	private static final String FILE_NAME_JSON = "questions.json";
	//private static final String FILE_NAME_SETTINGS = "settings.json";

	public static void storeSettingsToFile(final Context context, final String fileName, final JSONObject jsonObject) throws IOException {

		// MODE_PRIVATE will create the file (or replace a file of the same name) and make it private to your application.
		final FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
		fos.write(jsonObject.toString().getBytes());
		fos.close();

	}

	public static String loadQuestionsFromFile(final Context context) throws IOException {

		return File.loadFileJSON(context, File.FILE_NAME_JSON);

	}

	public static String loadFileJSON(final Context context, final String fileName) throws IOException {

		// Get the file input steam.
		final InputStream is = context.getAssets().open(fileName);

		final int size = is.available();

		final byte[] buffer = new byte[size];

		// Read to buffer
		is.read(buffer);

		// Close the input stream.
		is.close();

		return new String(buffer, "UTF-8");

	}

}
