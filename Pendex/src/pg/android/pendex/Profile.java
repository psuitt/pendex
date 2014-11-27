package pg.android.pendex;

import pg.android.pendex.exceptions.profile.ProfileResetException;
import pg.android.pendex.utils.ProfileUtil;
import android.app.ActionBar;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends ActionBarActivity {

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setUpTextViews();

        setUpButtonListeners();
    }

    private void setUpTextViews() {

        final TextView name = (TextView) findViewById(R.id.profile_textview_name);
        final TextView created = (TextView) findViewById(R.id.profile_textview_created);
        final TextView lastAnswered = (TextView) findViewById(R.id.profile_textview_last_answered);

        name.setText(ProfileUtil.getProfileId());
        created.setText(ProfileUtil.getCreatedSimple());
        lastAnswered.setText(ProfileUtil.getLastAnswered());

    }

    private void setUpButtonListeners() {

        final Button reset = (Button) findViewById(R.id.profile_button_reset);

        reset.setOnTouchListener(new OnTouchListener() {

            private CountDownTimer countDownTimer;

            @Override
            public boolean onTouch(final View v, final MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startTimer();
                        break;
                    case MotionEvent.ACTION_UP:
                        stopTimer();
                        v.performClick();
                        break;
                    default:
                        break;
                }

                return true;

            }

            private void startTimer() {
                reset.setText("Reset in 10 seconds.");
                countDownTimer = new CountDownTimer(5000, 1000) {

                    @Override
                    public void onTick(final long millisUntilFinished) {
                        reset.setText("Reset in " + millisUntilFinished / 1000 + " seconds.");
                    }

                    @Override
                    public void onFinish() {
                        try {
                            ProfileUtil.resetLoadedProfile(getApplicationContext());
                            Toast.makeText(getApplicationContext(), "Reset Successful",
                                    Toast.LENGTH_SHORT).show();
                        } catch (final ProfileResetException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Reset Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                        stopTimer();
                    }
                }.start();
            }

            private void stopTimer() {
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }
                countDownTimer = null;
                reset.setText(R.string.profile_button_reset);
            }

        });

    }

    public void restoreActionBar() {
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
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

}
