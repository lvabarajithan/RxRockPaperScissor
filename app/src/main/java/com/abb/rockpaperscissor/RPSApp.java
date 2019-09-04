package com.abb.rockpaperscissor;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by Abarajithan
 */
public class RPSApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
    }

}
