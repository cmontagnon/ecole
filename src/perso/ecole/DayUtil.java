package perso.ecole;

import java.util.Calendar;
import java.util.TimeZone;

public class DayUtil {

  public static String getCurrentDayAsString() {
    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
    return cal.get(Calendar.YEAR) + "-" + padWithZeroIfNecessary(cal.get(Calendar.MONTH) + 1) + "-"
        + padWithZeroIfNecessary(cal.get(Calendar.DAY_OF_MONTH));
  }

  //TODO : pad with zero method??
  private static String padWithZeroIfNecessary(int monthOrDay) {
    if (monthOrDay < 10) {
      return "0" + monthOrDay;
    } else {
      return String.valueOf(monthOrDay);
    }
  }
}
