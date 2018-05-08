import java.io.File;
import java.util.ArrayList;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class Holidays {
//    private ArrayList<Holiday> holidays = new ArrayList<Holiday>();
//    private ArrayList<CalendarDate> workdays = new ArrayList<CalendarDate>();
    private Holiday[] holidays;
    private CalendarDate[] workdays;

    public void readFile(String filename) {
        JSONArray jsonArr_holiday;
        JSONArray jsonArr_workday;
        try {
            File file = new File(filename);
            String data = FileUtils.readFileToString(file, "UTF-8");
            JSONObject jsonObj = new JSONObject(data);
            String year = jsonObj.getString("year");
            jsonArr_holiday = jsonObj.getJSONArray("holiday");
            jsonArr_workday = jsonObj.getJSONArray("workday");
            int holidayNum = jsonArr_holiday.length();
            holidays = new Holiday[holidayNum];
            int workdayNum = jsonArr_workday.length();
            workdays = new CalendarDate[workdayNum];
            for (int i = 0; i < holidayNum; i++) {
                JSONObject holidayTemp = (JSONObject)jsonArr_holiday.getJSONObject(i);
                String name = holidayTemp.getString("name");
                String zh_name = holidayTemp.getString("zh_name");
                CalendarDate holiday_time = new CalendarDate(holidayTemp.getString("holiday_time"));
                CalendarDate start_time = new CalendarDate(holidayTemp.getString("start_time"));
                CalendarDate end_time = new CalendarDate(holidayTemp.getString("end_time"));
                Holiday holiday = new Holiday(name, zh_name, holiday_time, start_time, end_time);
                holidays[i] = holiday;
            }
            for (int i = 0; i < workdayNum; i++) {
                JSONObject workdayTemp = (JSONObject)jsonArr_workday.getJSONObject(i);
                workdays[i] = new CalendarDate(workdayTemp.toString());
            }
                    } catch (java.io.IOException e) {
            System.out.println("The file doesn't exist.");
        } catch (FormatException e) {
            System.out.println("The date is not formatted.");
        }
    }

    public boolean isWorkday(CalendarDate date) {
        for (CalendarDate workday: workdays) {
            if (workday.getYear() == date.getYear() && workday.getMonth() == date.getMonth() && workday.getDay() == date.getDay()) {
                return true;
            }
        }
        return false;
    }

    public boolean isFestival(CalendarDate date) {
        for (Holiday holiday: holidays) {
            CalendarDate holidayTime = holiday.getHolidayDate();
            if (holidayTime.getYear() == date.getDay() && holidayTime.getMonth() == date.getMonth() && holidayTime.getDay() == date.getDay()) {
                return true;
            }
        }
        return false;
    }

    public boolean isHoliday(CalendarDate date) {
        Events events = new Events();
        for (Holiday holiday: holidays) {
            CalendarDate startTime = holiday.getStartDate();
            CalendarDate endTime = holiday.getEndDate();
            if (events.dateBetweenTwoDate(date, startTime, endTime)) {
                return true;
            }
        }
        return false;
    }
}