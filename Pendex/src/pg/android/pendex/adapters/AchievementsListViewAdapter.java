package pg.android.pendex.adapters;

import java.util.ArrayList;
import java.util.List;

import pg.android.pendex.R;
import pg.android.pendex.beans.Achievement;
import pg.android.pendex.exceptions.achievement.AchievementLoadException;
import pg.android.pendex.utils.AchievementUtil;
import pg.android.pendex.utils.FormatUtil;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AchievementsListViewAdapter extends BaseAdapter {

    private final Context context;
    private final List<Achievement> achievements;

    public AchievementsListViewAdapter(final Context context) {
        this.context = context;
        achievements = new ArrayList<Achievement>();
        achievements.addAll(AchievementUtil.getAchievements(context));

        if (achievements.isEmpty()) {
            final Achievement achievement = new Achievement();
            achievement.setAchievement("You have no achievements start answering!");
            achievements.add(achievement);
        }

    }

    @Override
    public int getCount() {
        return achievements.size();
    }

    @Override
    public Object getItem(final int position) {
        return achievements.get(position);
    }

    @Override
    public long getItemId(final int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        View row = convertView;
        TraitHolder holder = null;

        final Achievement achievement = achievements.get(position);

        if (row == null) {
            final LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.achievements_layout, parent, false);

            holder = new TraitHolder();

            holder.title = (TextView) row.findViewById(R.id.achievement_title);
            holder.date = (TextView) row.findViewById(R.id.achievement_date);
            holder.value = (TextView) row.findViewById(R.id.achievement_value);
            holder.summary = (TextView) row.findViewById(R.id.achievement_summary);

            row.setTag(holder);

        } else {
            holder = (TraitHolder) row.getTag();
        }

        holder.title.setText(achievement.getAchievement());
        holder.date.setText(FormatUtil.getDateSimple(achievement.getDate()));
        holder.value.setText(String.valueOf(achievement.getValue()));
        try {
            holder.summary.setText(AchievementUtil.getAchievementSummary(context,
                    achievement.getAchievement()));
        } catch (final AchievementLoadException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
        TextView value;
        TextView date;
        TextView summary;
    }
}
