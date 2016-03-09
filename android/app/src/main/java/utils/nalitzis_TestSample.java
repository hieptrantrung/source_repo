public class TestSample extends AndroidTestCase{
    
    public void testDoAsync(){
        SampleClass s = new SampleClass();
        Listener l = new ListenerImpl();
        s.doAsync();
    }
    
    private class ListenerImpl implements Listener{
        public void onValueChanged(int i){
            //how to test this value?
        }
    }
}