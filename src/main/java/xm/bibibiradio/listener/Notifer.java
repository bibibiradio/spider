package xm.bibibiradio.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Notifer {
    private Map<Integer,ArrayList<Listener>> listenerMap;
    
    public Notifer(){
        listenerMap = new HashMap<Integer,ArrayList<Listener>>();
    }
    
    public void register(int eventId,Listener listener){
        ArrayList<Listener> listenerList = listenerMap.get(eventId);
        if(listenerList != null){
            listenerList.add(listener);
        }else{
            listenerList = new ArrayList<Listener>();
            listenerList.add(listener);
            listenerMap.put(eventId, listenerList);
        }
    }
    
    protected void notify(int eventId,Object notifyBody){
        ArrayList<Listener> listenerList = listenerMap.get(eventId);
        if(listenerList != null){
            for(Listener listen:listenerList){
                listen.notifyListener(new Message(eventId,notifyBody));
            }
        }
    }
}
