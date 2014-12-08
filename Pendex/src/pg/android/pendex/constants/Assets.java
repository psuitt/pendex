package pg.android.pendex.constants;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

/**
 * Asset filenames.
 * 
 * @author Sora
 * 
 */
public final class Assets {

    private static final String TAG = "Assets";

    private static final String ID_PATTERN = "(\\D+)(\\d+)";
    public static final String QUESTIONS_JSON = "questions.json";
    public static final String ACHIEVEMENTS_JSON = "achievements.json";
    public static final String TRAITS_JSON = "traits.json";

    private static int MAX_ROWS_PER_FILE = 10000;

    private static final String DELIMITER = "-";

    public static final String PATH_DELIMITER = "/";
    public static final String PATH_QUESTIONS = "questions";

    /**
     * Hidden constructor.
     */
    private Assets() {

    }

    public static int getIndexFromId(final String str) {

        final Matcher match = Pattern.compile(ID_PATTERN).matcher(str);

        if (!match.matches()) {
            Log.e(TAG, "This id doesn't match the pattern. [id=" + str + "]");
        }

        final int rowIndex = (Integer.valueOf(match.group(2)) - 1) % MAX_ROWS_PER_FILE;

        return rowIndex;

    }

    public static String getFileNameFromId(final String str) {

        final Matcher match = Pattern.compile(ID_PATTERN).matcher(str);

        if (!match.matches()) {
            Log.e(TAG, "This id doesn't match the pattern. [id=" + str + "]");
        }

        final String filePrefix = match.group(1).toLowerCase(Locale.getDefault());
        final int fileIndex = (Integer.valueOf(match.group(2)) - 1) / MAX_ROWS_PER_FILE + 1;

        final StringBuilder sb = new StringBuilder();

        sb.append(filePrefix);
        sb.append(DELIMITER);
        sb.append(fileIndex);
        sb.append(Constants.JSON_FILE_ENDING);

        return sb.toString();
    }

}
