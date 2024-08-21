package com.example.testhook;

import android.app.Application;
import android.util.Log;

public class startApp extends Application {
    private static final String TAG = "startApp";
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "====> onCreate: ");
    }
}
