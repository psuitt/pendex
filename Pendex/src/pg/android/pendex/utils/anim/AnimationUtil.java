package pg.android.pendex.utils.anim;

import java.util.ArrayList;
import java.util.List;

import pg.android.pendex.R;
import pg.android.pendex.beans.PendexAnimation;
import pg.android.pendex.interfaces.IPendexAnimationCallbacks;
import pg.android.pendex.utils.FormatUtil;
import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Animation utility for pendex texts.
 * 
 * @author Sora
 * 
 */
public class AnimationUtil {

    private static final float FADE_OUT_END = 0f;
    private static final float FADE_OUT_START = 1f;
    private static final int FADE_IN_DURATION = 1000;
    private static final int FADE_OUT_DURATION = 2000;

    private static final String ACHIEVEMENT_PREFIX = "Achievement:";

    public enum AnimationType {
        Achievement, Answer, Pendex;
    }

    public static void animateViewSlideInTop(final Activity activity, final View view) {

        final Animation animation = AnimationUtils.loadAnimation(activity, R.animator.slide_in_top);
        animation.setAnimationListener(createShowListener(view));
        view.startAnimation(animation);

    }

    public static void animateViewSlideOutTop(final Activity activity, final View view) {

        final Animation animation =
                AnimationUtils.loadAnimation(activity, R.animator.slide_out_top);
        animation.setAnimationListener(createHideListener(view));
        view.startAnimation(animation);

    }

    public static void animateViewSlideInLeft(final Activity activity, final View view) {

        final Animation animation =
                AnimationUtils.loadAnimation(activity, R.animator.slide_in_left);
        animation.setAnimationListener(createShowListener(view));
        view.startAnimation(animation);

    }

    public static void animateViewSlideOutLeft(final Activity activity, final View view) {

        final Animation animation =
                AnimationUtils.loadAnimation(activity, R.animator.slide_out_left);
        animation.setAnimationListener(createHideListener(view));
        view.startAnimation(animation);

    }

    public static void animateViewSlideInRight(final Activity activity, final View view) {

        final Animation animation =
                AnimationUtils.loadAnimation(activity, R.animator.slide_in_right);
        animation.setAnimationListener(createShowListener(view));
        view.startAnimation(animation);

    }

    public static void animateViewSlideOutRight(final Activity activity, final View view) {

        final Animation animation =
                AnimationUtils.loadAnimation(activity, R.animator.slide_out_right);
        animation.setAnimationListener(createHideListener(view));
        view.startAnimation(animation);

    }

    public static void animateText(final Activity activity, final RelativeLayout layout,
            final int aboveId, final String text) {
        animateText(activity, layout, aboveId, AnimationType.Pendex, text, null);
    }

    /**
     * Creates an animation for the main layout.
     * 
     * @param activity - {@link Activity} - Activity to add text view to.
     * @param layout - {@link RelativeLayout} - Layout to add the view to.
     * @param aboveId - int - Id of where to start the animation.
     * @param animType - {@link AnimationType} - Type of text animation.
     * @param text - String - Text to display.
     * @param callback - {@link IPendexAnimationCallbacks} - Callback after animation.
     */
    public static void animateText(final Activity activity, final RelativeLayout layout,
            final int aboveId, final AnimationType animType, final String text,
            final IPendexAnimationCallbacks callback) {

        final LayoutInflater inflater = activity.getLayoutInflater();

        final TextView textView =
                createTextView(activity, layout, inflater, aboveId, animType, text);

        layout.addView(textView);

        final Animation fadeInAnimation =
                AnimationUtils.loadAnimation(activity, R.animator.traits_anim);
        final AlphaAnimation fadeOutAnimation = new AlphaAnimation(FADE_OUT_START, FADE_OUT_END);
        fadeOutAnimation.setDuration(FADE_OUT_DURATION);

        fadeInAnimation.setAnimationListener(createStartAnimationListener(callback, textView,
                fadeOutAnimation));
        fadeOutAnimation
                .setAnimationListener(createEndAnimationListener(layout, callback, textView));

        textView.startAnimation(fadeInAnimation);

    }

    /**
     * Creates an animation for the main layout.
     * 
     * @param activity - {@link Activity} - Activity to add text view to.
     * @param layout - {@link RelativeLayout} - Layout to add the view to.
     * @param aboveId - int - Id of where to start the animation.
     * @param animations - List&lt;{@link PendexAnimation}&gt; - Holds the animations to chain.
     * @param callback - {@link IPendexAnimationCallbacks} - Callback after animation.
     */
    public static void chainAnimateText(final Activity activity, final RelativeLayout layout,
            final int aboveId, final List<PendexAnimation> animations,
            final IPendexAnimationCallbacks callback) {

        int offset = 0;
        long startAchieve = 0l;
        final int last = animations.size() - 1;

        for (final PendexAnimation animation : animations) {

            final LayoutInflater inflater = activity.getLayoutInflater();

            final TextView textView =
                    createTextView(activity, layout, inflater, aboveId,
                            animation.getAnimationType(), animation.getText());
            textView.setVisibility(View.INVISIBLE);
            layout.addView(textView);

            Animation fadeInAnimation = null;
            final AlphaAnimation fadeOutAnimation =
                    new AlphaAnimation(FADE_OUT_START, FADE_OUT_END);
            fadeOutAnimation.setDuration(FADE_OUT_DURATION);

            switch (animation.getAnimationType()) {

                case Achievement:

                    fadeInAnimation = new AlphaAnimation(FADE_OUT_END, FADE_OUT_START);
                    fadeInAnimation.setDuration(FADE_IN_DURATION);

                    // Start the achieves after the pendex
                    fadeInAnimation.setStartOffset(startAchieve);
                    // Set the new start after the last achieve
                    startAchieve = startAchieve + FADE_IN_DURATION;
                    break;

                case Answer:
                default:

                    fadeInAnimation =
                            AnimationUtils.loadAnimation(activity, R.animator.traits_anim);
                    final long pendexDuration = fadeInAnimation.getDuration();

                    final long startOffset = (pendexDuration / 3) * offset;
                    fadeInAnimation.setStartOffset(startOffset);
                    startAchieve = startOffset + pendexDuration;
                    break;

            }


            if (offset != 0) {
                fadeInAnimation.setAnimationListener(createStartAnimationListener(null, textView,
                        fadeOutAnimation));
            } else {
                fadeInAnimation.setAnimationListener(createStartAnimationListener(callback,
                        textView, fadeOutAnimation));
            }

            if (offset != last) {
                fadeOutAnimation.setAnimationListener(createEndAnimationListener(layout, null,
                        textView));
            } else {
                fadeOutAnimation.setAnimationListener(createEndAnimationListener(layout, callback,
                        textView));
            }

            textView.startAnimation(fadeInAnimation);

            offset++;

        }

    }

    private static AnimationListener createHideListener(final View view) {
        return new AnimationListener() {

            @Override
            public void onAnimationStart(final Animation animation) {
            }

            @Override
            public void onAnimationRepeat(final Animation animation) {

            }

            @Override
            public void onAnimationEnd(final Animation animation) {
                // Hide text after animation
                view.setVisibility(View.INVISIBLE);
            }
        };
    }

    private static AnimationListener createShowListener(final View view) {
        return new AnimationListener() {

            @Override
            public void onAnimationStart(final Animation animation) {
                // Show the text
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(final Animation animation) {

            }

            @Override
            public void onAnimationEnd(final Animation animation) {
            }
        };
    }

    private static AnimationListener createStartAnimationListener(
            final IPendexAnimationCallbacks callback, final TextView textView,
            final AlphaAnimation fadeOutAnimationObject) {
        return new AnimationListener() {

            @Override
            public void onAnimationStart(final Animation animation) {
                // Show the text;
                textView.setVisibility(View.VISIBLE);
                if (callback != null) {
                    callback.animationStarted();
                }
            }

            @Override
            public void onAnimationRepeat(final Animation animation) {

            }

            @Override
            public void onAnimationEnd(final Animation animation) {
                textView.startAnimation(fadeOutAnimationObject);
            }
        };
    }

    private static AnimationListener createEndAnimationListener(final RelativeLayout layout,
            final IPendexAnimationCallbacks callback, final TextView textView) {
        return new AnimationListener() {

            @Override
            public void onAnimationStart(final Animation animation) {

            }

            @Override
            public void onAnimationRepeat(final Animation animation) {

            }

            @Override
            public void onAnimationEnd(final Animation animation) {
                layout.removeView(textView);
                if (callback != null) {
                    callback.animationFinished();
                }
            }
        };
    }

    /**
     * Creates the text view based on the input.
     * 
     * @param activity - {@link Activity} - Activity to add text view to.
     * @param layout - {@link RelativeLayout} - Layout to add the view to.
     * @param aboveId - int - Id of where to start the animation.
     * @param callback - {@link IPendexAnimationCallbacks} - Callback after animation.
     * @param animType - {@link AnimationType} - Type of text animation.
     * @param text - String - Text to display.
     * 
     * @return {@link TextView} - View to display.
     */
    private static TextView createTextView(final Context context, final RelativeLayout layout,
            final LayoutInflater inflater, final int aboveId, final AnimationType animType,
            final String text) {

        TextView textView = null;

        switch (animType) {
            case Achievement:

                textView = (TextView) inflater.inflate(R.layout.combat_text, layout, false);

                textView.setTextSize(35);
                textView.setTextColor(context.getResources().getColor(R.color.darkpurple));
                textView.setBackgroundResource(R.color.black);

                final RelativeLayout.LayoutParams achieveParams =
                        new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT);

                textView.setLayoutParams(achieveParams);

                textView.setText(FormatUtil.spaceDelimiter(ACHIEVEMENT_PREFIX, text));

                break;

            case Answer:

                textView = (TextView) inflater.inflate(R.layout.combat_text, layout, false);

                textView.setTextSize(30);
                textView.setTextColor(context.getResources().getColor(R.color.black));

                final RelativeLayout.LayoutParams answerParams =
                        new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT);

                answerParams.addRule(RelativeLayout.ABOVE, aboveId);

                textView.setLayoutParams(answerParams);
                textView.setGravity(Gravity.CENTER);

                textView.setText(text);

                break;

            default:

                textView = (TextView) inflater.inflate(R.layout.combat_text, layout, false);

                textView.setTextSize(28);
                textView.setTextColor(context.getResources().getColor(R.color.darkorange));

                final RelativeLayout.LayoutParams params =
                        new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT);

                params.addRule(RelativeLayout.ABOVE, aboveId);

                textView.setLayoutParams(params);
                textView.setGravity(Gravity.CENTER);

                textView.setText(text);

                break;
        }

        return textView;
    }

    /**
     * Creates a list of pendex animations from the input string list and type.
     * 
     * @param list - List&lt;String&gt; - List of text strings for animated text.
     * @param animationType - {@link AnimationType} - Type of animation to create.
     * 
     * @return List&lt;{@link PendexAnimation}&gt; - List of animations and their types set to the
     *         same as the input. This can be empty but not null.
     */
    public static List<PendexAnimation> createPendexAnimationList(final List<String> list,
            final AnimationType animationType) {

        final List<PendexAnimation> pendexAnimations = new ArrayList<PendexAnimation>();

        for (final String s : list) {

            final PendexAnimation pendexAnimation = new PendexAnimation();

            pendexAnimation.setText(s);
            pendexAnimation.setAnimationType(animationType);
            pendexAnimations.add(pendexAnimation);

        }

        return pendexAnimations;

    }

}
