package businessLayer;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateUtils {
    public static LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public static LocalDate getToLocalDate(Date date)
    {
        return convertToLocalDateTimeViaInstant(date).toLocalDate();
    }

    public static int getHour(Date date)
    {
        return convertToLocalDateTimeViaInstant(date).getHour();
    }

    public static int getMinute(Date date)
    {
        return convertToLocalDateTimeViaInstant(date).getMinute();
    }

    public static DayOfWeek getDayOfWeek(Date date)
    {
        return convertToLocalDateTimeViaInstant(date).getDayOfWeek();
    }

    public static DayOfWeek getDayOfWeek(String day) throws Exception
    {
        if (DayOfWeek.MONDAY.toString().compareToIgnoreCase(day) == 0)
            return DayOfWeek.MONDAY;
        if (DayOfWeek.TUESDAY.toString().compareToIgnoreCase(day) == 0)
            return DayOfWeek.TUESDAY;
        if (DayOfWeek.WEDNESDAY.toString().compareToIgnoreCase(day) == 0)
            return DayOfWeek.WEDNESDAY;
        if (DayOfWeek.THURSDAY.toString().compareToIgnoreCase(day) == 0)
            return DayOfWeek.THURSDAY;
        if (DayOfWeek.FRIDAY.toString().compareToIgnoreCase(day) == 0)
            return DayOfWeek.FRIDAY;
        if (DayOfWeek.SATURDAY.toString().compareToIgnoreCase(day) == 0)
            return DayOfWeek.SATURDAY;
        if (DayOfWeek.SUNDAY.toString().compareToIgnoreCase(day) == 0)
            return DayOfWeek.SUNDAY;

        throw new Exception("Invalid day name use:\n [monday] / [tuesday] / [wednesday] / [thursday] / [saturday] / [sunday]");
    }
}
