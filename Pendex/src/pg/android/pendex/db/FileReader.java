package pg.android.pendex.db;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;

/**
 * Reads files from the local storage.
 * 
 * @author Sora
 *
 */
public final class FileReader {

	private static final String FILE_NAME_JSON = "questions.json";

	public static String loadQuestionsFromFile(Context context) {
		
        String json = null;
        
        try {

            InputStream is = context.getAssets().open(FILE_NAME_JSON);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        
        return json;  
		
	}
	
}
