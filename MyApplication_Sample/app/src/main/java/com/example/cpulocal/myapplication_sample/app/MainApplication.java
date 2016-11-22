package com.example.cpulocal.myapplication_sample.app;

import android.app.Application;
import com.example.cpulocal.myapplication_sample.utils.Log;

public class MainApplication extends Application{
    public static String TAG = MainApplication.class.getSimpleName();
    @Override
    public void onCreate() {
        Log.w(TAG, "abc");
    }
}