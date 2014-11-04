package pg.android.pendex;

import pg.android.pendex.beans.Question;
import pg.android.pendex.constants.Messages;
import pg.android.pendex.exceptions.OutOfQuestionsException;
import pg.android.pendex.exceptions.ProfileLoadException;
import pg.android.pendex.exceptions.ProfileSaveException;
import pg.android.pendex.exceptions.QuestionsLoadException;
import pg.android.pendex.interfaces.INavigationDrawerCallbacks;
import pg.android.pendex.utils.ProfileUtil;
import pg.android.pendex.utils.QuestionUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Pendex extends ActionBarActivity implements
		INavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pendex);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));

		setUpButtonListeners();

		try {
			ProfileUtil.loadProfile(getApplicationContext(), "default");
		} catch (final ProfileLoadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final ProfileSaveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		nextQuestion();

	}

	private void setUpButtonListeners() {

		final Button p1_button = (Button) findViewById(R.id.button1);
		final Button p2_button = (Button) findViewById(R.id.button2);

		p1_button.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(final View v) {
				ProfileUtil.answerQuestion(0);
				nextQuestion();
			}
		});

		p2_button.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(final View v) {
				ProfileUtil.answerQuestion(1);
				nextQuestion();
			}
		});
	}

	private void nextQuestion() {

		final Button p1_button = (Button) findViewById(R.id.button1);
		final Button p2_button = (Button) findViewById(R.id.button2);
		final TextView text_view = (TextView) findViewById(R.id.textView1);

		try {

			final Question question = QuestionUtil
					.getRandomQuestion(getApplicationContext());

			text_view.setText(question.getQuestion());
			p1_button.setText(question.getAnswers().get(0).getAnswer());
			p2_button.setText(question.getAnswers().get(1).getAnswer());

		} catch (final QuestionsLoadException e) {

			e.printStackTrace();

		} catch (final OutOfQuestionsException e) {
			text_view.setText(Messages.COMPLETED_MESSAGE);
			p1_button.setText("");
			p2_button.setText("");
		}

	}

	@Override
	public void onNavigationDrawerItemSelected(final int position) {
		// update the main content by replacing fragments
		final FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager
				.beginTransaction()
				.replace(R.id.container,
						PlaceholderFragment.newInstance(position + 1)).commit();
	}

	public void onSectionAttached(final int number) {
		switch (number) {
		case 1:
			mTitle = getString(R.string.title_section1);
			break;
		case 2:
			mTitle = getString(R.string.title_section2);
			break;
		case 3:
			mTitle = getString(R.string.title_section3);
			final Intent intent = new Intent(getBaseContext(), Profile.class);
			startActivity(intent);
			break;
		case 4:
			mTitle = getString(R.string.title_section4);
			break;
		}
	}

	public void restoreActionBar() {
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
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
		public View onCreateView(final LayoutInflater inflater,
				final ViewGroup container, final Bundle savedInstanceState) {
			final View rootView = inflater.inflate(R.layout.fragment_pendex,
					container, false);
			return rootView;
		}

		@Override
		public void onAttach(final Activity activity) {
			super.onAttach(activity);
			((Pendex) activity).onSectionAttached(getArguments().getInt(
					ARG_SECTION_NUMBER));
		}
	}

}
