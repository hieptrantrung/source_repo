@Override
protected void onCreate(Bundle savedInstanceState) {
    //... init code ...

    ViewTreeObserver vto = mRootView.getViewTreeObserver();
    vto.addOnGlobalLayoutListener(new OnGlobalLayoutListenerImpl());
}

private void resizeBigView(){

    int indexOfBigView = mRootView.indexOfChild(mBigView);
    int totalNumberOFChildren = mRootView.getChildCount();

    int heightOfButton = measureRequiredHeight(mRootView, indexOfBigView + 1, totalNumberOFChildren);


    int bigViewOriginalHeight = mBigView.getHeight();
    int bigViewBottom = mBigView.getBottom();

    int rootViewHeight = mRootView.getHeight();

    int pixelsToRemove = 0;
    //if big view covers the button (even partially!)
    if(bigViewBottom > rootViewHeight - heightOfButton) {
        pixelsToRemove = bigViewBottom - (rootViewHeight - heightOfButton);
        Log.d(TAG, "pixels to remove: "+pixelsToRemove);
    }

    //setup view with the same height as before but changing height from match_parent to precise value
    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)mBigView.getLayoutParams();
    params.height = bigViewOriginalHeight - pixelsToRemove;
    mBigView.setLayoutParams(params);
}


//.. in our listener:
public void onGlobalLayout() {
    resizeBigView();
    if (android.os.Build.VERSION.SDK_INT < 16) // Use old, deprecated method
        mRootView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
    else // Use new method
        mRootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
}