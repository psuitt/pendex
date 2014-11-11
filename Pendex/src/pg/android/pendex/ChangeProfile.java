package pg.android.pendex;

import java.util.List;

import pg.android.pendex.constants.Preferences;
import pg.android.pendex.dialogs.ChangeProfileAdd;
import pg.android.pendex.exceptions.ProfileLoadException;
import pg.android.pendex.exceptions.ProfileSaveException;
import pg.android.pendex.utils.ProfileUtil;
import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ChangeProfile extends ActionBarActivity {

    private List<String> allProfiles;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);

        allProfiles = ProfileUtil.getProfilesList(this);

        setUpProfiles();
    }

    private void setUpProfiles() {

        final ListView myListView = (ListView) findViewById(R.id.change_profile_listview);

        final ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1,
                        allProfiles);

        myListView.setAdapter(adapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view,
                    final int position, final long id) {
                try {
                    ProfileUtil.loadProfile(ChangeProfile.this, allProfiles.get(position));
                    updateLoadedUser();
                } catch (final ProfileLoadException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (final ProfileSaveException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.change_profile, menu);
        menu.findItem(R.id.change_profile_action_add).setIcon(R.drawable.ic_action_new);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        final int id = item.getItemId();
        if (id == R.id.change_profile_action_add) {
            final DialogFragment addDialog = new ChangeProfileAdd(null);
            addDialog.show(getFragmentManager(), "dialog");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();

        updateLoadedUser();

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
