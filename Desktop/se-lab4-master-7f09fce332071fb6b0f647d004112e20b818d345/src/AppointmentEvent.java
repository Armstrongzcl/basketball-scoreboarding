public class AppointmentEvent extends Event {
    private CalendarDate date;
    private CalendarDate beginDate;
    private CalendarDate endDate;
    private String people;
    private String message;
    private boolean period;
    private String type = "appointment";
    public AppointmentEvent(CalendarDate date,String people,String message){
        this.date = date;
        this.people = people;
        this.message = message;
        period = false;
    }

    public AppointmentEvent(CalendarDate beginDate,CalendarDate endDate,String people, String message) {
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.people = people;
        this.message = message;
        period = true;
    }


    @Override
    public String getOverview(){
        if(isPeriod()){
            return ""+beginDate.getYear()+"-"+beginDate.getMonth()+"-"+beginDate.getDay()+" "+beginDate.getHour()+":"+beginDate.getMinute() +"到" +endDate.getYear()+"-"+endDate.getMonth()+"-"+endDate.getDay()+" "+endDate.getHour()+":"+endDate.getMinute() +"：与"+people+"约会，内容是："+message;
        }else{
            return ""+date.getYear()+"-"+date.getMonth()+"-"+date.getDay()+"：与"+people+"约会，内容是："+message;
        }
    }
}

