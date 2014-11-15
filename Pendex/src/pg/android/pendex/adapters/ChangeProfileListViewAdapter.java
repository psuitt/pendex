package pg.android.pendex.adapters;

import java.util.List;

import pg.android.pendex.R;
import pg.android.pendex.listeners.RemoveProfileOnClickListener;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class ChangeProfileListViewAdapter extends ArrayAdapter<String> {

    private int selected = 0;
    private final Context context;
    private final List<String> list;
    private ChangeProfileListViewAdapterMode mode = ChangeProfileListViewAdapterMode.Normal;
    private ChangeProfileHolder holder;

    public enum ChangeProfileListViewAdapterMode {
        Normal, Remove;
    }

    public ChangeProfileListViewAdapter(final Context context, final List<String> list) {
        super(context, R.layout.change_profile_listview_item, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        View row = convertView;

        if (row == null) {
            final LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.change_profile_listview_item, parent, false);

            holder = new ChangeProfileHolder();
            holder.profileName = (TextView) row.findViewById(R.id.change_profile_listview_name);
            holder.removeButton =
                    (ImageButton) row.findViewById(R.id.change_profile_listview_remove);
            holder.removeButton.setOnClickListener(new RemoveProfileOnClickListener(context, this,
                    list.get(position)));

            row.setTag(holder);
        } else {
            holder = (ChangeProfileHolder) row.getTag();
        }

        final String profileName = list.get(position);
        holder.profileName.setText(profileName);

        switch (mode) {
            case Remove:
                holder.removeButton.setVisibility(View.VISIBLE);
                break;
            default:
                holder.removeButton.setVisibility(View.GONE);
                break;
        }

        if (selected == position) {
            holder.profileName.setTextColor(context.getResources().getColor(R.color.white));
            row.setBackgroundResource(R.color.blue);
        } else {
            row.setBackgroundResource(0);
        }

        return row;

    }

    public void toggleMode() {
        switch (mode) {
            case Remove:
                changeMode(ChangeProfileListViewAdapterMode.Normal);
                break;
            default:
                changeMode(ChangeProfileListViewAdapterMode.Remove);
                break;
        }
    }

    public void setSelected(final String profileId) {
        selected = list.indexOf(profileId);
        notifyDataSetChanged();
    }

    public void changeMode(final ChangeProfileListViewAdapterMode mode) {
        this.mode = mode;
        notifyDataSetChanged();
    }

    public void removeFromList(final String profileId) {
        list.remove(profileId);
    }

    static class ChangeProfileHolder {
        TextView profileName;
        ImageButton removeButton;
    }
}
