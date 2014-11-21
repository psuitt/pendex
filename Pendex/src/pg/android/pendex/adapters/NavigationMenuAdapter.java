package pg.android.pendex.adapters;

import java.util.ArrayList;
import java.util.List;

import pg.android.pendex.R;
import pg.android.pendex.beans.Menu;
import pg.android.pendex.beans.Menu.MenuType;
import pg.android.pendex.utils.ProfileUtil;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Adapter for the menu.
 * 
 * @author Sora
 * 
 */
public class NavigationMenuAdapter extends BaseAdapter {

    private final Context context;
    private final List<Menu> menuItems;

    public NavigationMenuAdapter(final Context context) {
        this.context = context;
        menuItems = new ArrayList<Menu>();

        final Resources r = context.getResources();

        menuItems.add(new Menu(r.getString(R.string.menu_activity_pendex), MenuType.Title));
        menuItems.add(new Menu(ProfileUtil.getProfileId(), MenuType.Profile));
        menuItems.add(new Menu(r.getString(R.string.menu_activity_profile), MenuType.Sublist,
                R.drawable.ic_action_person));
        menuItems.add(new Menu(r.getString(R.string.menu_activity_traits), MenuType.Sublist,
                R.drawable.ic_action_view_as_list));
        menuItems.add(new Menu(r.getString(R.string.menu_activity_achievements), MenuType.Sublist,
                R.drawable.ic_action_important));
        menuItems.add(new Menu(r.getString(R.string.menu_activity_about)));

    }

    @Override
    public int getCount() {
        return menuItems.size();
    }

    @Override
    public Object getItem(final int position) {
        return menuItems.get(position);
    }

    @Override
    public long getItemId(final int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        View row = convertView;
        TraitHolder holder = null;

        final Menu menuItem = menuItems.get(position);

        if (row == null) {
            final LayoutInflater inflater = ((Activity) context).getLayoutInflater();

            holder = new TraitHolder();

            switch (menuItem.getType()) {
                case Profile:
                    row =
                            inflater.inflate(R.layout.navigation_menu_listview_item_profile,
                                    parent, false);
                    holder.textView =
                            (TextView) row.findViewById(R.id.navigation_menu_listview_text);

                    break;
                case Title:
                    row = inflater.inflate(R.layout.navigation_menu_listview_item, parent, false);
                    holder.textView =
                            (TextView) row.findViewById(R.id.navigation_menu_listview_text);
                    holder.textView.setTextSize(30f);
                    break;
                case Sublist:
                    row =
                            inflater.inflate(R.layout.navigation_menu_listview_item_sub, parent,
                                    false);

                    holder.textView =
                            (TextView) row.findViewById(R.id.navigation_menu_listview_text);
                    holder.button =
                            (ImageButton) row.findViewById(R.id.navigation_menu_listview_icon);
                    holder.button.setBackgroundResource(menuItem.getIcon());
                    break;
                default:
                    row = inflater.inflate(R.layout.navigation_menu_listview_item, parent, false);
                    holder.textView =
                            (TextView) row.findViewById(R.id.navigation_menu_listview_text);
                    break;
            }

            row.setTag(holder);
        } else {
            holder = (TraitHolder) row.getTag();
        }

        holder.textView.setText(menuItem.getText());

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
        TextView textView;
        ImageButton button;
    }


}
