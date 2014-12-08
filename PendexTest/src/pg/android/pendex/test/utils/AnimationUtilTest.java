package pg.android.pendex.test.utils;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
import pg.android.pendex.beans.PendexAnimation;
import pg.android.pendex.utils.AnimationUtil;
import pg.android.pendex.utils.AnimationUtil.AnimationType;

public class AnimationUtilTest extends TestCase {

    public void testCreatePendexAnimationList() {

        final List<String> listToConvert = Arrays.asList("Test", "Test2", "Test 3");

        final List<PendexAnimation> test =
                AnimationUtil.createPendexAnimationList(listToConvert, AnimationType.Pendex);

        int count = 0;

        for (final PendexAnimation anim : test) {
            assertEquals(listToConvert.get(count), anim.getText());
            assertEquals(AnimationType.Pendex, anim.getAnimationType());
            count++;
        }

        assertEquals(listToConvert.size(), test.size());

    }

}
