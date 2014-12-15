package pg.android.pendex;

import pg.android.pendex.exceptions.profile.ProfileCreateException;
import pg.android.pendex.exceptions.profile.ProfileExistsException;
import pg.android.pendex.utils.ProfileUtil;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Likes view for showing profile favorites.
 * 
 * @author Sora
 * 
 */
public class Welcome extends ActionBarActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        addButtonListeners();

    }

    private void addButtonListeners() {

        final Button buttonCreate = (Button) findViewById(R.id.welcome_create);
        final EditText userinput = (EditText) findViewById(R.id.welcome_edittext_name);

        buttonCreate.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final String userInputString = userinput.getText().toString();
                try {
                    ProfileUtil.createProfile(getApplicationContext(), userInputString);
                    startActivity(new Intent(getBaseContext(), Pendex.class));
                } catch (final ProfileExistsException e) {
                    Toast.makeText(getApplicationContext(),
                            "Profile " + userInputString + " already exists.", Toast.LENGTH_LONG)
                            .show();
                } catch (final ProfileCreateException e) {
                    Toast.makeText(getApplicationContext(), "Profile failed to create",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

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
