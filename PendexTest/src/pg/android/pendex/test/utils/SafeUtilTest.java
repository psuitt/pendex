package pg.android.pendex.test.utils;

import junit.framework.TestCase;
import pg.android.pendex.utils.SafeUtil;

public class SafeUtilTest extends TestCase {

    public void testSecureString() {
        final String str = SafeUtil.secureString("Damn i'm good.");
        assertFalse(str.contains("Damn"));
    }

    public void testSecureStringLowerCase() {
        final String str = SafeUtil.secureString("damn i'm good.");
        assertFalse(str.contains("damn"));
    }

}
