package pg.android.pendex.test.utils;

import junit.framework.TestCase;
import pg.android.pendex.utils.FormatUtil;

public class FormatUtilTest extends TestCase {

    public void testSpaceDelimiter() {
        final String test = FormatUtil.spaceDelimiter("test", "test", "test");
        assertEquals("test test test", test);
    }

}
