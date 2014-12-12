package pg.android.pendex.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    private static final String REGEX_IGNORE_CASE = "(?i)";
    private static final String REGEX_NOT_CHARACTER = "([^a-z]|$)";
    private static final String REGEX_4_CHARACTER = "[a-z][*]{3}";
    private static final String REGEX_5_CHARACTER = "[a-z][*]{4}";

    static {

        ArrayList<String> arrayList = new ArrayList<String>();

        arrayList.add("Dangit! ");
        arrayList.add("Blast! ");
        arrayList.add("Water Bearing Shield! ");

        map.put("damn" + REGEX_NOT_CHARACTER, arrayList);

        arrayList = new ArrayList<String>();

        arrayList.add("Ugh! ");
        arrayList.add("Lame! ");
        arrayList.add("Aw! ");

        map.put("shit" + REGEX_NOT_CHARACTER, arrayList);

        arrayList = new ArrayList<String>();

        arrayList.add("Thor! ");
        arrayList.add("Odin! ");
        arrayList.add("Shamu! ");

        map.put("god" + REGEX_NOT_CHARACTER, arrayList);

        arrayList = new ArrayList<String>();

        arrayList.add("Thor! ");
        arrayList.add("Odin! ");
        arrayList.add("Shamu! ");

        map.put("hell" + REGEX_NOT_CHARACTER, arrayList);

        arrayList = new ArrayList<String>();

        arrayList.add("Thor! ");
        arrayList.add("Odin! ");
        arrayList.add("Shamu! ");

        map.put("kill" + REGEX_NOT_CHARACTER, arrayList);

        arrayList = new ArrayList<String>();

        arrayList.add("fished! ");
        arrayList.add("Odin! ");
        arrayList.add("Shamu! ");

        map.put(REGEX_4_CHARACTER, arrayList);

        arrayList = new ArrayList<String>();

        arrayList.add("Thor! ");
        arrayList.add("Odin! ");
        arrayList.add("Shamu! ");

        map.put(REGEX_5_CHARACTER, arrayList);

    }

    /**
     * Hidden constructor.
     */
    private SafeUtil() {

    }

    public static String secureString(final String str) {

        String newString = str;

        for (final Entry<String, List<String>> badWordEntry : map.entrySet()) {
            newString =
                    newString.replaceAll(REGEX_IGNORE_CASE + badWordEntry.getKey(),
                            Utils.randomFromList(badWordEntry.getValue()));

        }

        return newString;

    }

}
