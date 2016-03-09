synchMethod(){
  // do something...
  
  callAsycnMethod(callback);
  
  //we want to stop here and wait for the callback before returning
}

private class CallbackImpl implements Callback{
  public void onComplete(){
    //this thread should signal the waiting thread that finally the execution is completed
    //and synchMethod() can return
  }
  
}