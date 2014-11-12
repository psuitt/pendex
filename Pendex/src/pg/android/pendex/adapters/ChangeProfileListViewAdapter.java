package pg.android.pendex.adapters;

import java.util.List;

import pg.android.pendex.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ChangeProfileListViewAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final List<String> list;

    public ChangeProfileListViewAdapter(final Context context, final List<String> list) {
        super(context, R.layout.change_profile_listview_item, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        View row = convertView;
        ChangeProfileHolder holder = null;

        if (row == null) {
            final LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.change_profile_listview_item, parent, false);

            holder = new ChangeProfileHolder();
            holder.profileName = (TextView) row.findViewById(R.id.change_profile_listview_name);

            row.setTag(holder);
        } else {
            holder = (ChangeProfileHolder) row.getTag();
        }

        final String profileName = list.get(position);
        holder.profileName.setText(profileName);

        return row;

    }

    static class ChangeProfileHolder {
        TextView profileName;
    }

}
