package pg.android.pendex.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Class for editing display text.
 * 
 * @author Sora
 * 
 */
public final class SafeUtil {

    private static final Map<String, List<String>> map = new HashMap<String, List<String>>();

    static {
        final ArrayList<String> arrayList = new ArrayList<String>();

        arrayList.add("Dangit!");
        arrayList.add("Blast!");
        arrayList.add("Water Bearing Shield!");

        map.put("damn", arrayList);
    }

    /**
     * Hidden constructor.
     */
    private SafeUtil() {

    }

    public static String secureString(final String str) {

        String newString = str;
        final String lowerCase = str.toLowerCase(Locale.getDefault());

        for (final Entry<String, List<String>> badWordEntry : map.entrySet()) {
            if (lowerCase.contains(badWordEntry.getKey())) {
                final String replacement = Utils.randomFromList(badWordEntry.getValue());
                newString = newString.replaceAll("(?i)" + badWordEntry.getKey(), replacement);
            }
        }

        return newString;

    }

}
