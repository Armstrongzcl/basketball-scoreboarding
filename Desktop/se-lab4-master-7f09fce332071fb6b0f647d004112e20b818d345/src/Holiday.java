public class Holiday {
    private String holidayName;
    private String holidayNameZh;
    private CalendarDate holidayTime;
    private CalendarDate startTime;
    private CalendarDate endTime;

    public Holiday(String holidayName, String holidayNameZh, CalendarDate holidayTime, CalendarDate startTime, CalendarDate endTime) {
        this.holidayName = holidayName;
        this.holidayNameZh = holidayNameZh;
        this.holidayTime = holidayTime;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getHolidayName() {
        return this.holidayName;
    }

    public String getHolidayNameZh() {
        return this.holidayNameZh;
    }

    public CalendarDate getHolidayDate() {
        return this.holidayTime;
    }

    public CalendarDate getStartDate() {
        return this.startTime;
    }

    public CalendarDate getEndDate() {
        return this.endTime;
    }

}
