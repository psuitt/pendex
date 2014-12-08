package pg.android.pendex.beans;

import pg.android.pendex.utils.AnimationUtil.AnimationType;

/**
 * String animation.
 * 
 * @author Sora
 * 
 */
public class PendexAnimation {

    private String text;
    /** THe default type is Pendex. */
    private AnimationType animationType = AnimationType.Pendex;

    public PendexAnimation() {

    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public AnimationType getAnimationType() {
        return animationType;
    }

    public void setAnimationType(final AnimationType animationType) {
        this.animationType = animationType;
    }


}
