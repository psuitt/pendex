package pg.android.pendex;

import java.util.ArrayList;
import java.util.List;

import pg.android.pendex.beans.PendexAnimation;
import pg.android.pendex.beans.ProfileAnswered;
import pg.android.pendex.beans.Question;
import pg.android.pendex.constants.Constants;
import pg.android.pendex.constants.Messages;
import pg.android.pendex.exceptions.OutOfQuestionsException;
import pg.android.pendex.exceptions.QuestionsLoadException;
import pg.android.pendex.exceptions.profile.ProfileCreateNewException;
import pg.android.pendex.exceptions.profile.ProfileLoadException;
import pg.android.pendex.exceptions.profile.ProfileSaveException;
import pg.android.pendex.interfaces.INavigationDrawerCallbacks;
import pg.android.pendex.interfaces.IPendexAnimationCallbacks;
import pg.android.pendex.utils.ProfileUtil;
import pg.android.pendex.utils.QuestionUtil;
import pg.android.pendex.utils.anim.AnimationUtil;
import pg.android.pendex.utils.anim.AnimationUtil.AnimationType;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Pendex extends ActionBarActivity implements INavigationDrawerCallbacks {

    private static final String TAG = "PendexActivity";

    private static final String CHOOSE_WISELY = "Choose Wisely!";

    private RelativeLayout mainRelativeLayout;

    private boolean save = true;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            ProfileUtil.loadProfile(getApplicationContext());
        } catch (final ProfileLoadException e) {
            Log.e(TAG, e.getMessage());
        } catch (final ProfileSaveException e) {
            Log.e(TAG, e.getMessage());
        } catch (final ProfileCreateNewException e) {
            save = false;
            this.startActivity(new Intent(getBaseContext(), Welcome.class));
            return;
        }

        setContentView(R.layout.activity_pendex);

        mainRelativeLayout = (RelativeLayout) findViewById(R.id.pendex_container);
        mNavigationDrawerFragment =
                (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(
                        R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        final Button button1 = (Button) findViewById(R.id.button1);
        final TextView questionTextView = (TextView) findViewById(R.id.textView1);

        button1.setText(R.string.pendex_click_to_begin);
        questionTextView.setText(R.string.pendex_default_start_text);

        button1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(final View v) {

                final RelativeLayout container =
                        (RelativeLayout) findViewById(R.id.pendex_container);
                final TextView questionTextView = (TextView) findViewById(R.id.textView1);
                final Button button1 = (Button) findViewById(R.id.button1);
                final Button button2 = (Button) findViewById(R.id.button2);

                final int height = container.getHeight();
                final int buttonHeight = height / 4;
                final int textHeight = height - 2 * buttonHeight;

                questionTextView.setLayoutParams(new RelativeLayout.LayoutParams(
                        LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                questionTextView.setHeight(textHeight);
                final RelativeLayout.LayoutParams params =
                        new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                                LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.BELOW, R.id.textView1);
                button1.setLayoutParams(params);
                button1.setHeight(buttonHeight);
                button2.setHeight(buttonHeight);

                button2.setVisibility(View.VISIBLE);

                init();

            }
        });

    }

    private void init() {

        setUpButtonListeners();
        nextQuestion();

    }

    private void setUpButtonListeners() {

        final Button button1 = (Button) findViewById(R.id.button1);
        final Button button2 = (Button) findViewById(R.id.button2);

        button1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(final View v) {
                processNextQuestion(0);
            }
        });

        button2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(final View v) {
                processNextQuestion(1);
            }
        });
    }

    /**
     * Sets the next question and answers the input.
     * 
     * @param answer - int - Index of answer.
     */
    private void processNextQuestion(final int answer) {

        final TextView questionTextView = (TextView) findViewById(R.id.textView1);
        final Button button1 = (Button) findViewById(R.id.button1);
        final Button button2 = (Button) findViewById(R.id.button2);

        try {

            final ProfileAnswered answered =
                    ProfileUtil.answerQuestion(getApplicationContext(), answer);

            final List<PendexAnimation> animations = new ArrayList<PendexAnimation>();

            final PendexAnimation pendexAnimation = new PendexAnimation();

            animations.add(pendexAnimation);

            animations.addAll(AnimationUtil.createPendexAnimationList(answered.getPendexList(),
                    AnimationType.Pendex));
            animations.addAll(AnimationUtil.createPendexAnimationList(answered.getAchievements(),
                    AnimationType.Achievement));

            final TextView answerTextView =
                    AnimationUtil.animateTextFadeIn(Pendex.this, mainRelativeLayout, R.id.button1,
                            AnimationType.Answer, answered.getAnsweredText());

            AnimationUtil.chainAnimateText(Pendex.this, mainRelativeLayout, R.id.button1,
                    animations, new IPendexAnimationCallbacks() {

                        @Override
                        public void animationStarted() {
                            AnimationUtil.animateViewSlideOutTop(Pendex.this, questionTextView);
                            AnimationUtil.animateViewSlideOutRight(Pendex.this, button1);
                            AnimationUtil.animateViewSlideOutLeft(Pendex.this, button2);
                        }

                        @Override
                        public void animationFinished() {
                            nextQuestion();
                            AnimationUtil.animateTextFadeOutAndRemove(mainRelativeLayout,
                                    answerTextView);
                            AnimationUtil.animateViewSlideInTop(Pendex.this, questionTextView);
                            AnimationUtil.animateViewSlideInRight(Pendex.this, button1);
                            AnimationUtil.animateViewSlideInLeft(Pendex.this, button2);
                        }
                    });


        } catch (final QuestionsLoadException e) {
            Log.e(TAG, "Error answering a question. [answerIndex=" + answer + "]");
        }


    }

    private void nextQuestion() {

        final Button button1 = (Button) findViewById(R.id.button1);
        final Button button2 = (Button) findViewById(R.id.button2);
        final TextView questionTextViw = (TextView) findViewById(R.id.textView1);

        try {

            final Question question = QuestionUtil.getRandomQuestion(getApplicationContext());

            if (Constants.EMPTY_STRING.equals(question.getQuestion())) {
                questionTextViw.setText(CHOOSE_WISELY);
            } else {
                questionTextViw.setText(question.getQuestion());
            }
            button1.setText(question.getAnswers().get(0).getAnswer());
            button2.setText(question.getAnswers().get(1).getAnswer());

        } catch (final QuestionsLoadException e) {

            e.printStackTrace();

        } catch (final OutOfQuestionsException e) {
            questionTextViw.setText(Messages.COMPLETED_MESSAGE);
            button1.setText("");
            button2.setText("");
        }

    }

    @Override
    public void onNavigationDrawerItemSelected(final int position) {
        // update the main content by replacing fragments
        final FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.pendex_container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(final int number) {

        switch (number) {
            case 1:
                break;
            case 2:
                startActivity(new Intent(getBaseContext(), ChangeProfile.class));
                break;
            case 3:
                startActivity(new Intent(getBaseContext(), Profile.class));
                break;
            case 4:
                startActivity(new Intent(getBaseContext(), Likes.class));
                break;
            case 5:
                startActivity(new Intent(getBaseContext(), Traits.class));
                break;
            case 6:
                startActivity(new Intent(getBaseContext(), Achievements.class));
                break;
            case 7:
                startActivity(new Intent(getBaseContext(), About.class));
                break;
        }
    }

    public void restoreActionBar() {
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(getString(R.string.menu_activity_pendex));
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.pendex, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        final int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        try {
            if (save) {
                ProfileUtil.saveProfile(getApplicationContext());
            }
        } catch (final ProfileSaveException e) {
            Log.e(TAG, "Unable to save.");
        }
        super.onStop();
    }

    /**
     * This should save the profile.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section number.
         */
        public static PlaceholderFragment newInstance(final int sectionNumber) {
            final PlaceholderFragment fragment = new PlaceholderFragment();
            final Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                final Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_pendex, container, false);
            return rootView;
        }

        @Override
        public void onAttach(final Activity activity) {
            super.onAttach(activity);
            ((Pendex) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    @Override
    public void skip() {
        try {
            ProfileUtil.skipQuestion(getApplicationContext());
        } catch (final QuestionsLoadException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        nextQuestion();
    }

}
