package pg.android.pendex.adapters;

import java.util.ArrayList;
import java.util.List;

import pg.android.pendex.R;
import pg.android.pendex.beans.Like;
import pg.android.pendex.utils.LikeUtil;
import pg.android.pendex.utils.Utils;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Likes list view adapter.
 * 
 * @author Sora
 * 
 */
public class LikesListViewAdapter extends BaseAdapter {

    private final Context context;
    private final List<Like> likes;

    public LikesListViewAdapter(final Context context) {
        this.context = context;
        likes = new ArrayList<Like>();
        likes.addAll(LikeUtil.getLikes(context));

        if (likes.isEmpty()) {
            final Like like = new Like();
            like.setLike("You have no likes start answering!");
            likes.add(like);
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

        final Like like = likes.get(position);
        final LayoutInflater inflater = ((Activity) context).getLayoutInflater();

        if (row == null) {

            row = inflater.inflate(R.layout.likes_layout, parent, false);

            holder = new TraitHolder();

            holder.title = (TextView) row.findViewById(R.id.likes_row_title);
            holder.subLayout = (LinearLayout) row.findViewById(R.id.likes_row_linearlayout);

            row.setTag(holder);

        } else {
            holder = (TraitHolder) row.getTag();
        }

        holder.title.setText(like.getLike());

        holder.subLayout.removeAllViews();

        final List<String> highest = Utils.getHighestKeysFromMap(like.getLikeMap());

        for (final String str : highest) {
            final View subListItem =
                    inflater.inflate(R.layout.likes_listview_layout, holder.subLayout, false);
            final TextView subListItemView =
                    (TextView) subListItem.findViewById(R.id.likes_row_listview_title);
            subListItemView.setText(str);
            holder.subLayout.addView(subListItem);
        }

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
        LinearLayout subLayout;
    }
}
