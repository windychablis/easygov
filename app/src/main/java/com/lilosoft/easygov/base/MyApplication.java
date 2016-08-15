package com.lilosoft.easygov.base;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by chablis on 2016/8/10.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppConfig.getAppConfig(this);
        Fresco.initialize(this);
    }
}
