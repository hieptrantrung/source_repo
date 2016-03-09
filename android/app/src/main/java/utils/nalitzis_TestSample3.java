public class TestSample extends AndroidTestCase{
    private int mFinalValue;    
    private static final int EXPECTED_VALUE = 10;
    
    private final HandlerThread mHandlerThread;
    
    private SampleClass mInstance;
 
    public void testDoAsync(){
        mInstance = new SampleClass();
        Listener l = new ListenerImpl();
        
        mHandlerThread = new MyHandler("handlerThread");
        
        Semaphore semaphore = new Semaphore(0);
        mHandlerThread.start();
        
        semaphore.acquire();
        mHandlerThread.quit();
        assertEquals(mFinalValue, EXPECTED_VALUE);
    }
    
    private class MyHandler extends HandlerThread{
        
        
        public void run() {
            mInstance.doAsync();
        }
    }
    
    private class ListenerImpl implements Listener{
        public void onValueChanged(int i){
            mFinalValue = i;
            semaphore.release();
        }
    }
}