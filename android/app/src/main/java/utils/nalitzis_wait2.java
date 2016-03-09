boolean signal = false;

synchMethod(){
  // do something...
  
  callAsycnMethod(callback);
 
 synchronized(this){
   while(!signal){
       try{
         this.wait();
       }catch(InterruptedException e){}  
    }
  }

  //return after the callback finished
}
 
private class CallbackImpl implements Callback{
  public void onComplete(){
    synchronized(this){
      this.signal = true;
      this.notify();
    }
  }
  
}