import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Events {
    private ArrayList<Event> memoInPeriod = new ArrayList<>();
    private ArrayList<Event> memoInDay = new ArrayList<>();

    Events() {
    }

    Events(Event event) {
        if (event.isPeriod()) memoInPeriod.add(event);
        else memoInDay.add(event);
    }

    public boolean add(Event event) {
        if (null == event) return false;
        else {
            if (event.isPeriod()) memoInPeriod.add(event);
            else memoInDay.add(event);
            return true;
        }
    }

    public boolean delete(Event event) {
        if (null == event) return false;
        else {
            if (event.isPeriod()) memoInPeriod.remove(event);
            else memoInDay.remove(event);
            return true;
        }
    }

    public ArrayList<Event> queryInPeriod(CalendarDate beginDate, CalendarDate endDate) {
        if (null == beginDate || null == endDate) return null;
        ArrayList<Event> result = new ArrayList<>();
        for (Event event : memoInDay) {
            if (dateBetweenTwoDate(event.getDate(), beginDate, endDate)) result.add(event);
        }
        for (Event event : memoInPeriod) {
            if (eventBetweenTwoDate(event, beginDate, endDate)) result.add(event);
        }
        return result;
    }


    public ArrayList<Event> queryInDay(CalendarDate date) {
        if (null == date) return null;
        ArrayList<Event> result = new ArrayList<>();

        for (Event event : memoInDay) {
            if (inOneDay(event.getDate(), date)) result.add(event);
        }
        for (Event event : memoInPeriod) {
            if (dateBetweenTwoDate(date, event.getBeginDate(), event.getEndDate())) result.add(event);
        }
        return result;
    }

    public ArrayList<Event> getMemoInPeriod() {
        return memoInPeriod;
    }

    public ArrayList<Event> getMemoInDay() {
        return memoInDay;
    }

    public boolean inOneDay(CalendarDate eventDate, CalendarDate date) {
        return eventDate.getYear() == date.getYear() && eventDate.getMonth() == date.getMonth() && eventDate.getDay() == date.getDay();
    }

    public boolean dateBetweenTwoDate(CalendarDate date, CalendarDate beginDate, CalendarDate endDate) {
        String dateStr1 = ""+date.getYear()+"-"+date.getMonth()+"-"+date.getDay()+" 00:00";
        String dateStr2 = ""+beginDate.getYear()+"-"+beginDate.getMonth()+"-"+beginDate.getDay()+" 00:00";
        String dateStr3 = ""+endDate.getYear()+"-"+endDate.getMonth()+"-"+endDate.getDay()+" 00:00";
        return compare(dateStr1,dateStr2) && (compare(dateStr3,dateStr1));
    }

    public boolean eventBetweenTwoDate(Event event, CalendarDate beginDate, CalendarDate endDate) {
        CalendarDate eventBeginDate = event.getBeginDate();
        CalendarDate eventEndDate = event.getEndDate();
        String dateStr1 = ""+eventBeginDate.getYear()+"-"+eventBeginDate.getMonth()+"-"+eventBeginDate.getDay()+" "+eventBeginDate.getHour()+":"+eventBeginDate.getMinute();
        String dateStr2 = ""+eventEndDate.getYear()+"-"+eventEndDate.getMonth()+"-"+eventEndDate.getDay()+" "+eventEndDate.getHour()+":"+eventEndDate.getMinute();
        String dateStr3 = ""+beginDate.getYear()+"-"+beginDate.getMonth()+"-"+beginDate.getDay()+" "+beginDate.getHour()+":"+beginDate.getMinute();
        String dateStr4 = ""+endDate.getYear()+"-"+endDate.getMonth()+"-"+endDate.getDay()+" "+endDate.getHour()+":"+endDate.getMinute();

        return compare(dateStr4,dateStr1) && compare(dateStr2,dateStr3);

    }

    private boolean compare(String beginDateString, String endDateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date beginDate = sdf.parse(beginDateString);
            Date endDate = sdf.parse(endDateString);
            return beginDate.getTime()>=endDate.getTime();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
