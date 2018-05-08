import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class EventsTest {

    @Test
    public void TestAddFalse() {
        Event event = new Event(new CalendarDate(2018,6,5),"666");
        Events events = new Events(event);
        assertTrue(events.add(new Event(new CalendarDate(2021,5,6),"888")));
        assertFalse(events.add(null));
    }

    @Test
    public void delete() {
        Event event1 = new Event(new CalendarDate(2018, 1, 1), "111");
        Event event2 = new Event(new CalendarDate(2018, 1, 2), "222");
        Event event3 = new Event(new CalendarDate(2018, 1, 3), "333");
        Event event4 = new Event(new CalendarDate(2018, 1, 3), "444");
        Event event5 = new Event(new CalendarDate(2018, 3, 4), new CalendarDate(2018, 4, 5), "666");
        Event event6 = new Event(new CalendarDate(2018, 10, 8), new CalendarDate(2018, 12, 7), "gfwsergw");
        Event event7 = new Event(new CalendarDate(2020, 1, 4), new CalendarDate(2020, 5, 6), "fwetw");

        Events events = new Events();
        events.add(event1);
        events.add(event2);
        events.add(event3);
        events.add(event4);
        events.add(event5);
        events.add(event6);
        events.add(event7);

        assertTrue(events.delete(event1));
        assertFalse(events.getMemoInDay().contains(event1));

        assertTrue(events.delete(event7));
        assertFalse(events.getMemoInPeriod().contains(event7));


        assertFalse(events.delete(null));
    }

    @Test
    public void TestQueryInPeriodNotNull() {
        Event event1 = new Event(new CalendarDate(2018,1,1),"111");
        Event event2 = new Event(new CalendarDate(2018,1,2),"222");
        Event event3 = new Event(new CalendarDate(2018,1,3),"333");
        Event event4 = new Event(new CalendarDate(2018,1,3),"444");
        Event event5 = new Event(new CalendarDate(2018,3,4),new CalendarDate(2018,4,5),"666");
        Event event6 = new Event(new CalendarDate(2018,10,8),new CalendarDate(2018,12,7),"gfwsergw");
        Event event7 = new Event(new CalendarDate(2020,1,4),new CalendarDate(2020,5,6),"fwetw");

        Events events = new Events();
        events.add(event1);
        events.add(event2);
        events.add(event3);
        events.add(event4);
        events.add(event5);
        events.add(event6);
        events.add(event7);

        assertTrue(events.queryInPeriod(new CalendarDate(2018,1,3),new CalendarDate(2018,1,4)).contains(event3));
        assertTrue(events.queryInPeriod(new CalendarDate(2018,1,3),new CalendarDate(2018,1,4)).contains(event4));
        assertFalse(events.queryInPeriod(new CalendarDate(2018,1,3),new CalendarDate(2018,1,4)).contains(event5));

        assertFalse(events.queryInPeriod(new CalendarDate(2019,11,20),new CalendarDate(2020,12,4)).contains(event6));
        assertTrue(events.queryInPeriod(new CalendarDate(2019,11,20),new CalendarDate(2020,12,4)).contains(event7));


    }

    @Test
    public void TestQueryInPeriodNull(){
        Event event1 = new Event(new CalendarDate(2018,1,1),"111");
        Event event2 = new Event(new CalendarDate(2018,1,2),"222");
        Event event3 = new Event(new CalendarDate(2018,1,3),"333");
        Event event4 = new Event(new CalendarDate(2018,1,3),"444");
        Event event5 = new Event(new CalendarDate(2018,3,4),new CalendarDate(2018,4,5),"666");
        Event event6 = new Event(new CalendarDate(2018,10,8),new CalendarDate(2018,12,7),"gfwsergw");
        Event event7 = new Event(new CalendarDate(2020,1,4),new CalendarDate(2020,5,6),"fwetw");

        Events events = new Events();
        events.add(event1);
        events.add(event2);
        events.add(event3);
        events.add(event4);
        events.add(event5);
        events.add(event6);
        events.add(event7);


        assertTrue(events.queryInPeriod(new CalendarDate(2030,1,1),new CalendarDate(2030,1,2)).isEmpty());

        assertNull(events.queryInPeriod(new CalendarDate(2010,1,1),null));

        assertNull(events.queryInPeriod(null,new CalendarDate(2010,1,1)));
    }

    @Test
    public void TestQueryInDay() {
        Event event1 = new Event(new CalendarDate(2018,1,1),"111");
        Event event2 = new Event(new CalendarDate(2018,1,2),"222");
        Event event3 = new Event(new CalendarDate(2018,1,3),"333");
        Event event4 = new Event(new CalendarDate(2018,1,3),"444");
        Event event5 = new Event(new CalendarDate(2019,3,4),new CalendarDate(2019,4,5),"666");

        Events events = new Events();
        events.add(event1);
        events.add(event2);
        events.add(event3);
        events.add(event4);
        events.add(event5);
        assertTrue(events.queryInDay(new CalendarDate(2018,1,1)).contains(event1));

        assertTrue(events.queryInDay(new CalendarDate(2018,1,3)).contains(event4));

        assertTrue(events.queryInDay(new CalendarDate(2019,3,7)).contains(event5));
    }

    @Test
    public void TestQueryInDayNull(){
        Event event1 = new Event(new CalendarDate(2018,1,1),"111");
        Event event2 = new Event(new CalendarDate(2018,1,2),"222");
        Event event3 = new Event(new CalendarDate(2018,1,3),"333");
        Event event4 = new Event(new CalendarDate(2019,3,4),new CalendarDate(2018,4,5),"666");
        Events events = new Events();
        events.add(event1);
        events.add(event2);
        events.add(event3);
        events.add(event4);
        assertTrue(events.queryInDay(new CalendarDate(2018,1,4)).isEmpty());

        assertNull(events.queryInDay(null));
    }

    @Test
    public void testInOneDay(){
        CalendarDate date1 = new CalendarDate(2018,3,4);
        CalendarDate date2 = new CalendarDate(2018,3,4);
        CalendarDate date3 = new CalendarDate(2018,4,5);
        Events events = new Events();
        assertFalse(events.inOneDay(date1,date3));
        assertTrue(events.inOneDay(date1,date2));
    }

    @Test
    public void testDateBetweenTwoDate(){
        CalendarDate date1 = new CalendarDate(2018,3,4);
        CalendarDate date2 = new CalendarDate(2018,7,6);
        CalendarDate date3 = new CalendarDate(2018,4,5);
        CalendarDate date4 = new CalendarDate(2018,1,1);
        Events events = new Events();
        assertTrue(events.dateBetweenTwoDate(date3,date1,date2));
        assertFalse(events.dateBetweenTwoDate(date4,date1,date3));
    }

    @Test
    public void TestEventBetweenTwoDate(){
        CalendarDate date1 = new CalendarDate(2018,3,4,12,12);
        CalendarDate date2 = new CalendarDate(2018,7,6,12,12);
        CalendarDate date3 = new CalendarDate(2018,4,5,12,12);
        CalendarDate date5 = new CalendarDate(2018,5,6,12,12);
        CalendarDate date6 = new CalendarDate(2018,8,9,12,12);
        CalendarDate date4 = new CalendarDate(2019,1,1,12,12);
        Event event = new Event(date1,date2,"gvergerger");
        Events events = new Events();
        assertTrue(events.eventBetweenTwoDate(event,date3,date4));
        assertTrue(events.eventBetweenTwoDate(event,date3,date5));
        assertFalse(events.eventBetweenTwoDate(event,date4,date6));
    }
}