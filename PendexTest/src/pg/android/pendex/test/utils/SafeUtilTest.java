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

    public void testSecureStringNotCompound() {
        final String str = SafeUtil.secureString("damnam i'm good.");
        assertTrue(str.contains("damn"));
    }

    public void testSecureStringEndLine() {
        final String str = SafeUtil.secureString("damn i'm good damn");
        assertFalse(str.contains("damn"));
    }

    public void testSecureStringEndLinePunctuation() {
        final String str = SafeUtil.secureString("damn i'm good damn.");
        assertFalse(str.contains("damn"));
    }

    public void testSecureStringFStar() {
        final String str = SafeUtil.secureString("damn i'm good f***ed something");
        assertFalse(str.contains("f***ed"));
    }

    public void testSecureStringBStar() {
        final String str = SafeUtil.secureString("damn i'm good B**** something");
        assertFalse(str.contains("B****"));
    }

}
