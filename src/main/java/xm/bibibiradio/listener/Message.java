package xm.bibibiradio.listener;

public class Message {
    private Object msg;
    private int eventId;
    
    public Message(int eventId,Object msg){
        this.eventId = eventId;
        this.msg = msg;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
    
    
}
