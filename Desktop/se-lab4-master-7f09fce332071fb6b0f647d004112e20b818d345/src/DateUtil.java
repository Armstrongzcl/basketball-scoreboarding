/*
 * This class provides some utils that may help you to finish this lab.
 * getToday() is finished, you can use this method to get the current date.
 * The other four methods getDaysInMonth(), isValid(), isFormatted() and isLeapYear() are not finished,
 * you should implement them before you use.
 *
 * */

import javax.swing.border.MatteBorder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtil {
    /**
     * get a CalendarDate instance point to today
     *
     * @return a CalendarDate object
     */
    public static CalendarDate getToday() {
        Calendar calendar = Calendar.getInstance();
        return new CalendarDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * get all dates in the same month with given date
     *
     * @param date the given date
     * @return a list of days in a whole month
     */
    public static List<CalendarDate> getDaysInMonth(CalendarDate date) {
        if (null == date || !DateUtil.isValid(date)) return null;
        int year = date.getYear();
        int month = date.getMonth();
        List<CalendarDate> list = new ArrayList<>();
        for (int day = 1; day < getDaysOfMonth(date) + 1; day++) {
            list.add(new CalendarDate(year, month, day));
        }
        return list;
    }

    /**
     * Judge whether the input date is valid. For example, 2018-2-31 is not valid
     *
     * @param date the input date
     * @return true if the date is valid, false if the date is not valid.
     */
    public static boolean isValid(CalendarDate date) {
        if (null == date) return false;
        int month = date.getMonth();
        int day = date.getDay();
        int daysOfMonth = getDaysOfMonth(date);
        return month > 0 && month < 13 && day > 0 && day < daysOfMonth + 1;
    }

    /**
     * Judge whether the input is formatted.
     * For example, 2018/2/1 is not valid and 2018-2-1 is valid.
     *
     * @param dateString
     * @return true if the input is formatted, false if the input is not formatted.
     */
    public static boolean isFormatted(String dateString) {
        if (null == dateString) return false;
        if (dateString.length() < 11) {
            String regex = "[0-9]{1,4}-[0-9]{1,2}-[0-9]{1,2}";
            Pattern pattern = Pattern.compile(regex);
            Matcher m = pattern.matcher(dateString);
            return m.matches();
        } else {
            String regex = "^[1-9]\\d{3}-(0?[1-9]|1[0-2])-(0?[1-9]|[12]\\d|3[01])\\s*(0?[1-9]|1\\d|2[0-3])(:(0?[1-9]|[1-5]\\d))$";
            Pattern pattern = Pattern.compile(regex);
            Matcher m = pattern.matcher(dateString);
            return m.matches();
        }
    }


    /**
     * Judge whether the input year is a leap year or not.
     * For example, year 2000 is a leap year, and 1900 is not.
     *
     * @param year
     * @return true if the input year is a leap year, false if the input is not.
     */
    public static boolean isLeapYear(int year) {
        if (year < 0) {
            year = -year;
            if (year % 100 != 1) return year % 4 == 1;
            return (year % 400 == 1 || year % 172800 == 1) && year % 3200 != 1;
        } else return year != 0 && (year % 400 == 0) || (year % 100 != 0 && year % 4 == 0);
    }

    /**
     * get the total days of this month
     *
     * @param date
     * @return total days of this month
     */
    private static int getDaysOfMonth(CalendarDate date) {
        if (null == date) return -1;
        int days;
        int year = date.getYear();
        int month = date.getMonth();
        switch (month) {
            case 2:
                days = isLeapYear(year) ? 29 : 28;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                days = 30;
                break;
            default:
                days = 31;
                break;
        }
        return days;
    }

}

