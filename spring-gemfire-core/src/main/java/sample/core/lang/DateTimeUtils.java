package sample.core.lang;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.gemstone.gemfire.management.internal.cli.util.spring.StringUtils;

/**
 * DateTimeUtils is a abstract utility class for working with dates and time.
 *
 * @author John Blum
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public abstract class DateTimeUtils {

  public static final String BIRTH_DATE_FORMAT = "MM/dd/yyyy";
  public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd hh:mm:ss";

  public static Calendar asCalendar(final Date date) {
    if (date != null) {
      Calendar now = Calendar.getInstance();
      now.clear();
      now.setTimeInMillis(date.getTime());
      return now;
    }

    return null;
  }

  public static Date asDate(final Calendar calendar) {
    return (calendar != null ? calendar.getTime() : null);
  }

  public static String format(final Date dateTime) {
    return format(dateTime, DEFAULT_DATE_TIME_FORMAT);
  }

  public static String format(final Date dateTime, String dateFormatPattern) {
    return (dateTime != null ? new SimpleDateFormat(dateFormatPattern).format(dateTime) : null);
  }

  public static Date parse(final String dateTimeValue) {
    return parse(dateTimeValue, DEFAULT_DATE_TIME_FORMAT);
  }

  public static Date parse(final String dateTimeValue, final String dateFormatPattern) {
    try {
      return (StringUtils.hasText(dateTimeValue) ? new SimpleDateFormat(dateFormatPattern).parse(dateTimeValue) : null);
    }
    catch (ParseException e) {
      throw new IllegalArgumentException(String.format("failed to convert (%1$s) into a Date using format (%2$s)",
        dateTimeValue, dateFormatPattern));
    }
  }

}
