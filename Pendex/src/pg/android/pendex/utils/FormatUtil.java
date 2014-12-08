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

    /**
     * Hidden constructor.
     */
    private FormatUtil() {

    }

    public static final String spaceDelimiter(final Object... obj) {

        final StringBuilder builder = new StringBuilder();

        for (final Object s : obj) {
            builder.append(s);
            builder.append(Constants.SPACE);
        }

        return builder.toString().trim();

    }

    public static final String getDateSimple(final String stringDate) {
        if (stringDate == null || stringDate.isEmpty()) {
            return Constants.EMPTY_STRING;
        }
        return getDateSimple(getDateFromSimple(stringDate));
    }

    public static final String getDateSimple(final Date date) {
        return getDateSimple(date, Locale.getDefault());
    }

    public static final String getDateSimple(final Date date, final Locale locale) {
        if (date == null) {
            return Constants.EMPTY_STRING;
        }
        return new SimpleDateFormat(Constants.DATE_FORMAT, locale).format(date);
    }

    public static final Date getDateFromSimple(final String stringDate) {
        return getDateFromSimple(stringDate, Locale.getDefault());
    }

    public static final Date getDateFromSimple(final String stringDate, final Locale locale) {
        try {
            return new SimpleDateFormat(Constants.DATE_FORMAT, locale).parse(stringDate);
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return new Date(System.currentTimeMillis());
    }

}
