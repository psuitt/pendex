package pg.android.pendex.utils;

import java.util.List;
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

}
