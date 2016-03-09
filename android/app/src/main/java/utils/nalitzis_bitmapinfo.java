    private void readBitmapInfo() {
        final Resources res = getActivity().getResources();
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, R.drawable.brasil, options);
        final float imageHeight = options.outHeight;
        final float imageWidth = options.outWidth;
        final String imageMimeType = options.outMimeType;
        Log.d(TAG, "w,h, type:"+imageWidth+", "+imageHeight+", "+imageMimeType);
        Log.d(TAG, "estimated memory required in MB: "+imageWidth * imageHeight * BYTES_PER_PX/MemUtils.BYTES_IN_MB);
    }