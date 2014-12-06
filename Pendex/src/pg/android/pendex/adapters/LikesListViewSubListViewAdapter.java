package pg.android.pendex.adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import pg.android.pendex.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Likes list view adapter.
 * 
 * @author Sora
 * 
 */
public class LikesListViewSubListViewAdapter extends BaseAdapter {

    private final Context context;
    private final List<String> likes;

    public LikesListViewSubListViewAdapter(final Context context, final Map<String, Integer> map) {
        this.context = context;
        likes = new ArrayList<String>();

        if (map != null) {

            int highest = 0;
            for (final Entry<String, Integer> likeEntry : map.entrySet()) {

                if (likeEntry.getValue() > highest) {
                    // Only show highest.
                    likes.clear();
                    likes.add(likeEntry.getKey());
                    highest = likeEntry.getValue();
                } else if (likeEntry.getValue() == highest) {
                    // Same as highest so show.
                    likes.add(likeEntry.getKey());
                }

            }

        }

    }

    @Override
    public int getCount() {
        return likes.size();
    }

    @Override
    public Object getItem(final int position) {
        return likes.get(position);
    }

    @Override
    public long getItemId(final int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        View row = convertView;
        TraitHolder holder = null;

        final String like = likes.get(position);

        if (row == null) {
            final LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.likes_listview_layout, parent, false);

            holder = new TraitHolder();

            holder.title = (TextView) row.findViewById(R.id.likes_row_listview_title);

            row.setTag(holder);

        } else {
            holder = (TraitHolder) row.getTag();
        }

        holder.title.setText(like);

        return row;

    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(final int position) {
        return true;
    }

    static class TraitHolder {
        TextView title;
    }
}
