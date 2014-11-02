package pg.android.pendex.utils;

public final class StringUtil {

	public static final String BLANK_STRING = "";

	/**
	 * Converts a null to a blank.
	 * 
	 * @param s
	 *            - String - String to convert;
	 * 
	 * @return String or blank.
	 */
	public static String nullToBlank(final String s) {

		if (s != null) {
			return s;
		}

		return BLANK_STRING;
	}

	private StringUtil() {

	}

}
