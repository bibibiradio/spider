package xm.bibibiradio.listener;

public interface Notifer {
    public void register(int eventId,Listener listener);
    public void notify(int eventId,Object notifyBody);
}
