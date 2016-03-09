public class TestSample extends AndroidTestCase{
    private int mFinalValue;    
    private static final int EXPECTED_VALUE = 10;

    public void testDoAsync(){
        SampleClass s = new SampleClass();
        Listener l = new ListenerImpl();
        Semaphore semaphore = new Semaphore(0);
        s.doAsync();
        semaphore.acquire();

        assertEquals(mFinalValue, EXPECTED_VALUE);
    }
    
    private class ListenerImpl implements Listener{
        public void onValueChanged(int i){
            mFinalValue = i;
            semaphore.release();
        }
    }
}