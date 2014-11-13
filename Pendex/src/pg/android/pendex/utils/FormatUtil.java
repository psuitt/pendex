package pg.android.pendex.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import pg.android.pendex.constants.Constants;

/**
 * Format util.
 * 
 * @author Sora
 * 
 */
public final class FormatUtil {

    public static final String getDateSimple(final Date date, final Locale locale) {
        return new SimpleDateFormat(Constants.DATE_FORMAT, locale).format(date);
    }

    public static final Date getDateFromSimple(final String stringDate, final Locale locale) {
        try {
            return new SimpleDateFormat(Constants.DATE_FORMAT, locale).parse(stringDate);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    /**
     * Hidden constructor.
     */
    private FormatUtil() {

    }

}
