package pg.android.pendex.db;

import java.io.FileNotFoundException;
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
	private static final String FILE_NAME_SETTINGS = "settings.json";

	public static void storeSettingsToFile(final Context context, final JSONObject jsonObject) throws IOException {

		// MODE_PRIVATE will create the file (or replace a file of the same name) and make it private to your application.
		final FileOutputStream fos = context.openFileOutput(File.FILE_NAME_SETTINGS, Context.MODE_PRIVATE);
		fos.write(jsonObject.toString().getBytes());
		fos.close();

	}

	public static String loadSettingsFromFile(final Context context) throws FileNotFoundException {

		//FileInputStream fis = context.openFileInput(FILE_NAME_SETTINGS);
		//fis.
		//fos.close();
		return null;

	}

	public static String loadQuestionsFromFile(final Context context) {

		String json = null;

		try {

			final InputStream is = context.getAssets().open(File.FILE_NAME_JSON);

			final int size = is.available();

			final byte[] buffer = new byte[size];

			is.read(buffer);

			is.close();

			json = new String(buffer, "UTF-8");


		} catch (final IOException e) {
			e.printStackTrace();
			return null;
		}

		return json;

	}

}
