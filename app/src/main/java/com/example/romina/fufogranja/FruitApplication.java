package com.example.romina.fufogranja;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class FruitApplication extends Application {

    private static final String LOG_TAG = "FruitApplication";

    public static RefWatcher getRefWatcher(Context context) {
        FruitApplication application = (FruitApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        refWatcher = LeakCanary.install(this);
        Log.d(LOG_TAG, "onCreate");
    }
}
