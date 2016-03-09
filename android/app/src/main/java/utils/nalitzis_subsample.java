    private void subSampleImage(int powerOf2) {
        if(powerOf2 < 1 || powerOf2 > 8) {
            Log.e(TAG, "trying to apply upscale or excessive downscale: "+powerOf2);
            return;
        }
        final Resources res = getActivity().getResources();
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inSampleSize = powerOf2;
        final Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.brasil, options);
        final BitmapDrawable bmpDrawable = new BitmapDrawable(res, bmp);
        mRootLayout.setBackground(bmpDrawable);
    }