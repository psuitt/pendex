package pg.android.pendex.listeners;

import pg.android.pendex.adapters.ChangeProfileListViewAdapter;
import pg.android.pendex.utils.ProfileUtil;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

public class RemoveProfileOnClickListener implements OnClickListener {

    private final Context context;
    private final ChangeProfileListViewAdapter adapter;
    private final String profileId;

    public RemoveProfileOnClickListener(final Context context,
            final ChangeProfileListViewAdapter adapter, final String profileId) {
        super();
        this.context = context;
        this.adapter = adapter;
        this.profileId = profileId;
    }

    @Override
    public void onClick(final View v) {

        ProfileUtil.removeProfile(context, profileId);
        adapter.remove(profileId);
        adapter.notifyDataSetChanged();

    }

}
