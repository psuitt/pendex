package pg.android.pendex.dialogs;

import pg.android.pendex.R;
import pg.android.pendex.exceptions.profile.ProfileExistsException;
import pg.android.pendex.exceptions.profile.ProfileSaveException;
import pg.android.pendex.interfaces.IChangeProfileAddDialogCallbacks;
import pg.android.pendex.utils.ProfileUtil;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Dialog for adding a profile.
 * 
 * @author Sora
 * 
 */
public class ChangeProfileAdd extends DialogFragment {

    private final ViewGroup view;
    private final IChangeProfileAddDialogCallbacks callback;

    public ChangeProfileAdd(final ViewGroup view, final IChangeProfileAddDialogCallbacks callback) {
        super();
        this.view = view;
        this.callback = callback;
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_change_profile_create, view);
        final EditText userinput =
                (EditText) dialogView.findViewById(R.id.dialog_change_profile_edittext_name);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(dialogView)
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, final int id) {
                        final Dialog dialog = (Dialog) dialogInterface;
                        final Context context = dialog.getContext();
                        try {
                            final String userInputString = userinput.getText().toString();
                            ProfileUtil.createProfile(context, userInputString);
                            if (callback != null) {
                                callback.createdUser(userInputString);
                            }
                        } catch (final ProfileSaveException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (final ProfileExistsException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, final int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();

    }

}
