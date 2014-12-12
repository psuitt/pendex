package pg.android.pendex.utils.anim;

import pg.android.pendex.interfaces.IPendexAnimationCallbacks;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.RelativeLayout;

/**
 * Animation factory. Creates things used for animation.
 * 
 * @author Sora
 * 
 */
public final class AnimationFactory {

    /**
     * Hidden constructor.
     */
    private AnimationFactory() {

    }

    /**
     * Creates a start animation set to visible. If there is a callback this is called on the
     * animation start and if there is a fade out this is called on the animation end.
     * 
     * @param view - {@link View} - View to show.
     * @param callback - {@link IPendexAnimationCallbacks} - Callback to call animation started.
     * @param fadeOutAnimationObject - {@link AlphaAnimation} - Animation to chain.
     * 
     * @return {@link AnimationListener} - Animation listener with the start and end animation set.
     * 
     */
    public static AnimationListener createStartAnimationListener(final View view,
            final IPendexAnimationCallbacks callback, final AlphaAnimation fadeOutAnimationObject) {
        return new AnimationListener() {

            @Override
            public void onAnimationStart(final Animation animation) {
                // Show the text;
                view.setVisibility(View.VISIBLE);
                if (callback != null) {
                    callback.animationStarted();
                }
            }

            @Override
            public void onAnimationRepeat(final Animation animation) {

            }

            @Override
            public void onAnimationEnd(final Animation animation) {
                if (fadeOutAnimationObject != null) {
                    view.startAnimation(fadeOutAnimationObject);
                }
            }
        };
    }

    /**
     * End animation listener.
     * 
     * @param layout - {@link RelativeLayout} - Layout to remove the input text view from.
     * @param view - {@link View} - View to remove from layout.
     * @param callback - {@link IPendexAnimationCallbacks} - Call back to call when the animation
     *        finishes.
     * 
     * @return {@link AnimationListener} - Animation listener with the end animation set.
     */
    public static AnimationListener createEndAnimationListener(final RelativeLayout layout,
            final View view, final IPendexAnimationCallbacks callback) {
        return new AnimationListener() {

            @Override
            public void onAnimationStart(final Animation animation) {

            }

            @Override
            public void onAnimationRepeat(final Animation animation) {

            }

            @Override
            public void onAnimationEnd(final Animation animation) {
                layout.removeView(view);
                if (callback != null) {
                    callback.animationFinished();
                }
            }
        };
    }

}
