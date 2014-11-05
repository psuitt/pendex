package pg.android.pendex;

import pg.android.pendex.adapters.TraitsListViewAdapter;
import pg.android.pendex.utils.ProfileUtil;
import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class Traits extends ActionBarActivity {

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
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

		final TraitsListViewAdapter adapter = new TraitsListViewAdapter(this,
				ProfileUtil.getPendexTraits());

		myListView.setAdapter(adapter);

	}

	public void onSectionAttached(final int number) {
		switch (number) {
		case 1:
			mTitle = getString(R.string.title_section1);
			break;
		case 2:
			mTitle = getString(R.string.title_activity_profile);
			break;
		case 3:
			mTitle = getString(R.string.title_activity_traits);
			break;
		}
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
