package pg.android.pendex;

import pg.android.pendex.adapters.AchievementsListViewAdapter;
import pg.android.pendex.utils.AchievementUtil;
import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

public class Achievements extends ActionBarActivity {

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        setUpAchievements();

    }

    private void setUpAchievements() {

        final ListView myListView = (ListView) findViewById(R.id.achievements_view);

        final AchievementsListViewAdapter adapter = new AchievementsListViewAdapter(this);

        myListView.setAdapter(adapter);

        final TextView textView = (TextView) findViewById(R.id.achievements_title);

        textView.setText("Achievements "
                + String.valueOf(AchievementUtil.getTotalAchievementPoints()));

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
