package pg.android.pendex.adapters;

import java.util.List;
import java.util.Map;

import pg.android.pendex.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

/**
 * About expandable list view adapter.
 * 
 * @author Sora
 * 
 */
public class AboutExpandableListViewAdapter extends BaseExpandableListAdapter {

    private final Context context;
    private final List<String> parents;
    private final Map<String, List<String>> parentToChildren;

    /**
     * Constructor sets all needed data to create an expandable list view.
     * 
     * @param context - {@link Context} - Activity for view creation.
     * @param parents - List&lt;String&gt; - List of parent objects.
     * @param parentToChildren - List&;tString, List&lt;String&gt;&gt; - List of parents to
     *        children.
     * 
     */
    public AboutExpandableListViewAdapter(final Context context, final List<String> parents,
            final Map<String, List<String>> parentToChildren) {

        this.context = context;
        this.parents = parents;
        this.parentToChildren = parentToChildren;

    }

    @Override
    public Object getChild(final int groupPosition, final int childPosition) {
        return parentToChildren.get(parents.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getChildId(final int groupPosition, final int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
            final boolean isLastChild, final View convertView, final ViewGroup parent) {

        View row = convertView;

        final String childText = (String) getChild(groupPosition, childPosition);

        if (row == null) {
            final LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.about_exlistview_layout_child, parent, false);
        }

        final TextView txtListChild = (TextView) row.findViewById(R.id.about_child_text);

        txtListChild.setText(childText);

        return row;

    }

    @Override
    public int getChildrenCount(final int groupPosition) {
        return parentToChildren.get(parents.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(final int groupPosition) {
        return parents.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return parents.size();
    }

    @Override
    public long getGroupId(final int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded,
            final View convertView, final ViewGroup parent) {

        View row = convertView;

        final String childText = (String) getGroup(groupPosition);

        if (row == null) {
            final LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.about_exlistview_layout_parent, parent, false);
        }

        final TextView parentText = (TextView) row.findViewById(R.id.about_parent_text);

        parentText.setText(childText);

        return row;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(final int groupPosition, final int childPosition) {
        return false;
    }

}
