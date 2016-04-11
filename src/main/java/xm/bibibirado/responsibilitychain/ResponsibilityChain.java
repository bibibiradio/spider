package xm.bibibirado.responsibilitychain;

public interface ResponsibilityChain {
    final static public int CONTINUE = 1;
    final static public int STOP = 2;
    
    public int nextChain(Object t);
    public void setNext(ResponsibilityChain next);
}
