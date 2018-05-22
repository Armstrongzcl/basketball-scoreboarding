public class Event {
    private CalendarDate date;
    private CalendarDate beginDate;
    private CalendarDate endDate;
    private String message;
    private boolean period;
    private String type = "normal";


    public Event(){}
    public Event(CalendarDate date,String message){
        this.date = date;
        this.message = message;
        period = false;
    }

    public Event(CalendarDate beginDate,CalendarDate endDate, String message) {
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.message = message;
        period = true;
    }

    public CalendarDate getDate() {
        return date;
    }

    public CalendarDate getBeginDate() {
        return beginDate;
    }

    public CalendarDate getEndDate() {
        return endDate;
    }

    public String getMessage() {
        return message;
    }

    public boolean isPeriod() {
         return period;
    }

    public String getDayString(){
        return ""+date.getYear()+"-"+date.getMonth()+"-"+date.getDay();
    }

    public String getBeginDateString(){
        return ""+beginDate.getYear()+"-"+beginDate.getMonth()+"-"+beginDate.getDay()+" "+beginDate.getHour()+":"+beginDate.getMinute();
    }

    public String getEndDateString(){
        return ""+endDate.getYear()+"-"+endDate.getMonth()+"-"+endDate.getDay()+" "+endDate.getHour()+":"+endDate.getMinute();
    }

    public String getType(){
        return type;
    }

    public  String getOverview(){
        if(isPeriod()){
            return  ""+beginDate.getYear()+"-"+beginDate.getMonth()+"-"+beginDate.getDay()+" "+beginDate.getHour()+":"+beginDate.getMinute() +"到" +endDate.getYear()+"-"+endDate.getMonth()+"-"+endDate.getDay()+" "+endDate.getHour()+":"+endDate.getMinute() +"内容为："+message;
        }else{
            return ""+date.getYear()+"-"+date.getMonth()+"-"+date.getDay()+"内容为："+message;
        }
    }
}
