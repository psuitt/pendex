package pg.android.pendex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pg.android.pendex.adapters.AboutExpandableListViewAdapter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

/**
 * Likes view for showing profile favorites.
 * 
 * @author Sora
 * 
 */
public class About extends ActionBarActivity {

    private ExpandableListView expandableListView;

    private static final List<String> aboutParents = new ArrayList<String>();
    private static final Map<String, List<String>> aboutMap = new HashMap<String, List<String>>();

    static {

        aboutParents.add("Pendex");
        aboutParents.add("Change Profile");
        aboutParents.add("Profile");
        aboutParents.add("Likes");
        aboutParents.add("Traits");
        aboutParents.add("Achievements");

        aboutMap.put(
                "Pendex",
                Arrays.asList("The game! You will be given 2 choices, choose your favorite from the 2 choices. Each choice could lead to new traits and achievements."));
        aboutMap.put(
                "Change Profile",
                Arrays.asList("Create or add new profiles just for multiple people on one phone or see if you can unlock different things on each profile."));
        aboutMap.put(
                "Profile",
                Arrays.asList("Change your profile options or reset your profile. Reseting a profile will only reset your traits, likes and answered questions."));
        aboutMap.put("Likes",
                Arrays.asList("Your favorite things based on your answers to questions."));
        aboutMap.put("Traits", Arrays.asList("Your traits based on your answers to questions."));
        aboutMap.put("Achievements",
                Arrays.asList("Achievements can be unlocked by answering questions a certain way."));

    }


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        expandableListView = (ExpandableListView) findViewById(R.id.about_expandable_list_view);

        setUpListView();

    }

    private void setUpListView() {


        final BaseExpandableListAdapter expListAdapter =
                new AboutExpandableListViewAdapter(About.this, aboutParents, aboutMap);
        expandableListView.setAdapter(expListAdapter);

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
