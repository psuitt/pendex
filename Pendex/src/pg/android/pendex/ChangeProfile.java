package pg.android.pendex;

import java.util.List;

import pg.android.pendex.adapters.ChangeProfileListViewAdapter;
import pg.android.pendex.constants.Preferences;
import pg.android.pendex.dialogs.ChangeProfileAdd;
import pg.android.pendex.exceptions.ProfileLoadException;
import pg.android.pendex.exceptions.ProfileSaveException;
import pg.android.pendex.interfaces.IChangeProfileAddDialogCallbacks;
import pg.android.pendex.utils.ProfileUtil;
import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ChangeProfile extends ActionBarActivity {

    private ListView profileListView;
    private List<String> allProfiles;
    private ChangeProfileListViewAdapter adapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);

        allProfiles = ProfileUtil.getProfilesList(this);

        setUpProfiles();
    }

    private void setUpProfiles() {

        profileListView = (ListView) findViewById(R.id.change_profile_listview);

        adapter = new ChangeProfileListViewAdapter(this, allProfiles);

        profileListView.setAdapter(adapter);
        profileListView.setSelector(R.drawable.selector_change_profile);

        profileListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view,
                    final int position, final long id) {

                try {
                    final String profileId = allProfiles.get(position);
                    // Load the selected profile.
                    ProfileUtil.loadProfile(ChangeProfile.this, profileId);
                    // Save the new default in the preferences.
                    updateLoadedUser();
                    // Move to parent.
                    NavUtils.navigateUpFromSameTask(ChangeProfile.this);
                } catch (final ProfileLoadException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (final ProfileSaveException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });

        adapter.setSelected(ProfileUtil.getProfileId());

    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.change_profile, menu);
        menu.findItem(R.id.change_profile_action_add).setIcon(R.drawable.ic_action_new);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        final int id = item.getItemId();

        switch (id) {

            case R.id.change_profile_action_add:
                final DialogFragment addDialog = createAddUserDialog();
                addDialog.show(getFragmentManager(), "dialog");
                return true;

            case R.id.change_profile_action_remove:
                adapter.toggleMode();
                break;

            default:
                break;

        }

        return super.onOptionsItemSelected(item);

    }

    private ChangeProfileAdd createAddUserDialog() {

        final ChangeProfileAdd dialog =
                new ChangeProfileAdd(null, new IChangeProfileAddDialogCallbacks() {

                    @Override
                    public void createdUser(final String newUserId) {
                        allProfiles.clear();
                        allProfiles.addAll(ProfileUtil.getProfilesList(ChangeProfile.this));
                        adapter.setSelected(newUserId);
                    }

                });

        return dialog;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void updateLoadedUser() {
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        final SharedPreferences settings = getSharedPreferences(Preferences.PENDEX_PREFERENCES, 0);
        final SharedPreferences.Editor editor = settings.edit();
        editor.putString(Preferences.LAST_PROFILE_ID_STRING, ProfileUtil.getProfileId());

        // Commit the edits!
        editor.commit();
    }

}
