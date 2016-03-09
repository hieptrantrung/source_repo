private Pair<Integer,Integer> measureButton(){
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(ViewGroup.LayoutParams.WRAP_CONTENT, View.MeasureSpec.EXACTLY);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(ViewGroup.LayoutParams.WRAP_CONTENT, View.MeasureSpec.EXACTLY);
        mButton.measure(widthMeasureSpec, heightMeasureSpec);
        int h = mButton.getMeasuredHeight();
        int w = mButton.getMeasuredWidth();
        Log.d(TAG, "height of button is :"+h+", width is :"+w);
        return new Pair<Integer, Integer>(w,h);
}