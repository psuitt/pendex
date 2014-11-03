package pg.android.pendex.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Traits utils.
 * 
 * @author Sora
 * 
 */
public final class TraitUtil {

	private static final Map<String, String> traitSummaryMap = new HashMap<String, String>();

	private static final String NO_SUMMAY_TEXT = "A description of this trait is not available please add one.";

	/**
	 * Returns the trait summary text.
	 * 
	 * @param trait
	 *            - String - Trait to get summary for.
	 * 
	 * @return String - Summary of trait.
	 */
	public static String getTraitSummay(final String trait) {
		if (traitSummaryMap.containsKey(trait)) {
			return traitSummaryMap.get(trait);
		}
		return NO_SUMMAY_TEXT;
	}

}
