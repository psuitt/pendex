package pg.android.pendex.test.constants;

import junit.framework.TestCase;
import pg.android.pendex.constants.Assets;

public class AssetsTest extends TestCase {

    public void testGetIndexFromId() {
        final int index = Assets.getIndexFromId("LIFE1");
        assertEquals(0, index);
    }

    public void testGetIndexFromId1000() {
        final int index = Assets.getIndexFromId("lIfE1000");
        assertEquals(999, index);
    }

    public void testGetIndexFromId10000() {
        final int index = Assets.getIndexFromId("LiFE10000");
        assertEquals(999, index);
    }

    public void testGetIndexFromId10001() {
        final int index = Assets.getIndexFromId("LIFE10001");
        assertEquals(0, index);
    }

    public void testGetFileNameFromId() {
        final String fileName = Assets.getFileNameFromId("LIFE1");
        assertEquals("life-1.json", fileName);
    }

    public void testGetFileNameFromIdSecondFile1000() {
        final String fileName = Assets.getFileNameFromId("LifE1000");
        assertEquals("life-1.json", fileName);
    }

    public void testGetFileNameFromIdSecondFile1001() {
        final String fileName = Assets.getFileNameFromId("LIFe1001");
        assertEquals("life-2.json", fileName);
    }

    public void testGetFileNameFromIdSecondFile10000() {
        final String fileName = Assets.getFileNameFromId("lIFE10000");
        assertEquals("life-10.json", fileName);
    }

}
