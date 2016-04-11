package xm.bibibirado.responsibilitychain;

public class ChainItemDecorator implements ResponsibilityChain {
    protected ResponsibilityChain next;
    protected ResponsibilityChain real;
    
    public ChainItemDecorator(Object real) throws Exception{
        if(real instanceof ResponsibilityChain){
            this.real = (ResponsibilityChain) real;
        }
    }
    
    @Override
    public int nextChain(Object t) {
        // TODO Auto-generated method stub
        if(next != null){
            int ret = real.nextChain(t);
        }
        return 0;
    }
    @Override
    public void setNext(ResponsibilityChain next) {
        // TODO Auto-generated method stub
        this.next = next;
    }

}
