package pg.android.pendex.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

/**
 * Utility methods.
 * 
 * @author Sora
 * 
 */
public final class Utils {

    /** Seeded random is thread safe. */
    private static final Random random = new Random();

    /**
     * Hidden constructor.
     */
    private Utils() {

    }

    public static <T> T randomFromList(final List<T> list) {

        final int index = random.nextInt(list.size());

        return list.get(index);

    }


    /**
     * Takes in a map of string to their values and returns the top value or multiple if all are the
     * same.
     * 
     * @param map - Map&lt;String, Integer&gt; - List of values to find highest keys.
     * 
     * @return List&lt;String&gt; - Top values multiple if their values are the same.
     */
    public static List<String> getHighestKeysFromMap(final Map<String, Integer> map) {

        final List<String> favorites = new ArrayList<String>();

        if (map != null) {

            int highest = 0;
            for (final Entry<String, Integer> likeEntry : map.entrySet()) {

                if (likeEntry.getValue() > highest) {
                    // Only show highest.
                    favorites.clear();
                    favorites.add(likeEntry.getKey());
                    highest = likeEntry.getValue();
                } else if (likeEntry.getValue() == highest) {
                    // Same as highest so show.
                    favorites.add(likeEntry.getKey());
                }

            }

        }

        return favorites;

    }

}
