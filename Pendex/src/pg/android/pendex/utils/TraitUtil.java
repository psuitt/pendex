package pg.android.pendex.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pg.android.pendex.constants.Assets;
import pg.android.pendex.db.File;
import pg.android.pendex.exceptions.TraitLoadException;
import android.content.Context;

/**
 * Traits utils.
 * 
 * @author Sora
 * 
 */
public final class TraitUtil {

    private static boolean loaded = false;

    private static final Map<String, String> traitSummaryMap = new HashMap<String, String>();

    private static final String NO_SUMMAY_TEXT =
            "A description of this trait is not available please add one.";

    private static void loadTraitSummaryMap(final Context context) throws TraitLoadException {

        try {

            final JSONArray array =
                    new JSONArray(File.loadAssetsFileJSON(context, Assets.TRAITS_JSON));

            for (int i = 0; i < array.length(); i++) {

                final JSONObject object = array.getJSONObject(i);
                final String trait = JsonUtil.getString(object, "trait");
                final String summary = JsonUtil.getString(object, "summary");

                if (summary.length() > 0) {
                    traitSummaryMap.put(trait, summary);
                }

            }

        } catch (final JSONException e) {

            e.printStackTrace();
            throw new TraitLoadException();

        } catch (final IOException e) {

            e.printStackTrace();
            throw new TraitLoadException();

        }

    }

    /**
     * Returns the trait summary text.
     * 
     * @param context - {@link Context} - Context.
     * @param trait - String - Trait to get summary for.
     * 
     * @return String - Summary of trait.
     * @throws TraitLoadException - If the traits can't load.
     */
    public static String getTraitSummay(final Context context, final String trait)
            throws TraitLoadException {

        if (!loaded) {
            loadTraitSummaryMap(context);
        }

        if (traitSummaryMap.containsKey(trait)) {
            return traitSummaryMap.get(trait);
        }
        return NO_SUMMAY_TEXT;
    }

}
