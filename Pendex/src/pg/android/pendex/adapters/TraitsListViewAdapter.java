package pg.android.pendex.adapters;

import java.util.List;

import pg.android.pendex.R;
import pg.android.pendex.beans.Trait;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TraitsListViewAdapter extends ArrayAdapter<Trait> {

    private final Context context;
    private final List<Trait> list;

    public TraitsListViewAdapter(final Context context, final List<Trait> list) {
        super(context, R.layout.traits_layout, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        View row = convertView;
        TraitHolder holder = null;

        if (row == null) {
            final LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.traits_layout, parent, false);

            holder = new TraitHolder();
            holder.traitTitle = (TextView) row.findViewById(R.id.trait_title);
            holder.traitValue = (TextView) row.findViewById(R.id.trait_value);
            holder.summary = (TextView) row.findViewById(R.id.trait_summary);

            row.setTag(holder);
        } else {
            holder = (TraitHolder) row.getTag();
        }

        final Trait trait = list.get(position);
        holder.traitTitle.setText(trait.getTrait());
        holder.traitValue.setText(String.valueOf(trait.getTraitValue()));
        holder.summary.setText(trait.getSummary());

        return row;

    }

    static class TraitHolder {
        TextView traitTitle;
        TextView traitValue;
        TextView summary;
    }

}
