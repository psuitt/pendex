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

        menuItems.add(new Menu(r.getString(R.string.title_section1), MenuType.Title));
        menuItems.add(new Menu(ProfileUtil.getProfileId(), MenuType.Profile));
        menuItems.add(new Menu(r.getString(R.string.title_activity_profile)));
        menuItems.add(new Menu(r.getString(R.string.title_activity_traits)));
        menuItems.add(new Menu(r.getString(R.string.title_section4)));

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

            if (menuItem.getType() == MenuType.Profile) {
                row =
                        inflater.inflate(R.layout.navigation_menu_listview_item_profile, parent,
                                false);
            } else {
                row = inflater.inflate(R.layout.navigation_menu_listview_item, parent, false);
            }

            holder.textView = (TextView) row.findViewById(R.id.navigation_menu_listview_text);

            switch (menuItem.getType()) {
                case Profile:
                    break;
                case Title:
                    holder.textView.setTextSize(25f);
                    break;
                default:
                    holder.textView.setPadding(50, 0, 0, 0);
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
