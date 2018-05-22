/**
 * We have finished part of this class yet, you should finish the rest.
 * 1. A constructor that can return a CalendarDate object through the given string.
 * 2. A method named getDayOfWeek() that can get the index of a day in a week.
 */
public class CalendarDate {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int[] dayEachMonth = {31, 0, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};


    public CalendarDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;

    }

    public CalendarDate(int year,int month,int day,int hour,int minute){
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;

    }

    /**
     * a constructor that can return a CalendarDate object through the given string.
     *
     * @param dateString format: 2018-3-18
     */
    public CalendarDate(String dateString) throws FormatException {
        if (dateString.length() < 11) {
            if (DateUtil.isFormatted(dateString)) {
                String[] date = dateString.split("-");
                setYear(Integer.parseInt(date[0]));
                setMonth(Integer.parseInt(date[1]));
                setDay(Integer.parseInt(date[2]));

            } else throw new FormatException("输入的日期格式不正确.");
        } else {
            if(DateUtil.isFormatted(dateString)){
                String[] date = dateString.split("-");
                setYear(Integer.parseInt(date[0]));
                setMonth(Integer.parseInt(date[1]));
                String[] date1 = date[2].split(" ");
                setDay(Integer.parseInt(date1[0]));
                String[] time = date1[1].split(":");
                setHour(Integer.parseInt(time[0]));
                setMinute(Integer.parseInt(time[1]));

            }else throw new FormatException("输入的日期格式不正确.");
        }
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return this.hour;
    }

    public void setHour(int hour){ this.hour = hour;}
    public int getMinute() {
        return this.minute;
    }

    public void setMinute(int minute){
        this.minute = minute;
    }



    /**
     * Get index of the day in a week for one date.
     * <p>
     * Don't use the existing implement like Calendar.setTime(),
     * try to implement your own algorithm.
     *
     * @return 1-7, 1 stands for Monday and 7 stands for Sunday
     */
    public int getDayOfWeek() {
        if (!DateUtil.isValid(this)) return -1;
        int days = 0;
        for (int i = 1800; i < year; i++) {
            if (DateUtil.isLeapYear(i)) days += 366;
            else days += 365;
        }
        for (int j = 1; j < month; j++) {
            switch (j) {
                case 2:
                    if (DateUtil.isLeapYear(year)) days += 29;
                    else days += 28;
                    break;
                default:
                    days += dayEachMonth[j - 1];
            }
        }
        days += day - 1;
        return (3 + days % 7) % 7;
    }

}
