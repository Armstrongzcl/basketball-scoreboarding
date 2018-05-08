public class MeetingEvent extends Event {
    private CalendarDate date;
    private CalendarDate beginDate;
    private CalendarDate endDate;
    private String place;
    private String title;
    private String message;
    private boolean period;
    private String type = "meeting";
    public MeetingEvent(CalendarDate date,String place,String title,String message){
        this.date = date;
        this.message = message;
        this.place = place;
        this.title = title;
        period = false;
    }

    public MeetingEvent(CalendarDate beginDate,CalendarDate endDate, String place,String title,String message) {
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.place = place;
        this.title = title;
        this.message = message;
        period = true;
    }


    @Override
    public String getOverview(){
        if(isPeriod()){
            return ""+beginDate.getYear()+"-"+beginDate.getMonth()+"-"+beginDate.getDay()+" "+beginDate.getHour()+":"+beginDate.getMinute() +"到" +endDate.getYear()+"-"+endDate.getMonth()+"-"+endDate.getDay()+" "+endDate.getHour()+":"+endDate.getMinute() +"：在"+place+"参加会议，会议主题是："+title+"内容是："+message;
        }else{
            return ""+date.getYear()+"-"+date.getMonth()+"-"+date.getDay()+"：在"+place+"参加会议，会议主题是："+title+"内容是："+message;
        }
    }
}
