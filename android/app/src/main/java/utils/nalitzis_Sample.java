public interface SampleClass {
    void doAsync(Listener l);
    
    public interface Listener{
        void onValueChanged(int i);
    }
}