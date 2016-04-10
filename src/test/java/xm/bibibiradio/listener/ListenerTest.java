package xm.bibibiradio.listener;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ListenerTest {
    private class L1 implements Listener{
        private void doSth(Message message){
            System.out.println(message.getEventId()+" "+message.getMsg());
        }
        @Override
        public void notifyListener(Message message) {
            // TODO Auto-generated method stub
            doSth(message);
        }
        
    }
    
    private class L2 implements Listener{
        private void doSth(Message message){
            System.out.println(message.getEventId()+" "+message.getMsg());
        }
        @Override
        public void notifyListener(Message message) {
            // TODO Auto-generated method stub
            doSth(message);
        }
        
    }
    
    private class N1 extends Notifer{
        public void doASth(){
            notify(1,"N1 doASth event1");
        }
        
        public void doBSth(){
            notify(2,"N1 doBSth event2");
        }
    }
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        L1 l1 = new L1();
        L2 l2 = new L2();
        N1 n1 = new N1();
        
        n1.register(1, l1);
        n1.register(2, l2);
        
        n1.doASth();
        n1.doBSth();
        n1.doBSth();
        n1.doASth();
    }

}
