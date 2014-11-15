package pg.android.pendex;

import java.util.ArrayList;
import java.util.List;

import pg.android.pendex.adapters.TraitsListViewAdapter;
import pg.android.pendex.beans.Trait;
import pg.android.pendex.exceptions.TraitLoadException;
import pg.android.pendex.utils.ProfileUtil;
import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class Traits extends ActionBarActivity {

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traits);

        setUpTraits();
    }

    private void setUpTraits() {

        final ListView myListView = (ListView) findViewById(R.id.traits_view);

        List<Trait> pendexTraits;

        try {
            pendexTraits = ProfileUtil.getPendexTraits(getApplicationContext());

        } catch (final TraitLoadException e) {
            pendexTraits = new ArrayList<Trait>();
            final Trait trait = new Trait();
            trait.setTrait("THIS FAILED TO LOAD");
            pendexTraits.add(trait);
        }

        final TraitsListViewAdapter adapter = new TraitsListViewAdapter(this, pendexTraits);
        myListView.setAdapter(adapter);

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
