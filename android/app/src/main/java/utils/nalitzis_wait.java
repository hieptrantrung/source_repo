synchMethod(){
  // do something...
  
  callAsycnMethod(callback);

  synchronized(this){
    try{
      this.wait();  
    }catch(InterruptedException e){}
    
  }
  
}
 
private class CallbackImpl implements Callback{
  public void onComplete(){
    synchronized(this){
      this.notify();
    }
  }
  
}