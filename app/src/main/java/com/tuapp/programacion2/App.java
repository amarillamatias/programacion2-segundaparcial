package com.tuapp.programacion2;

import android.app.Application;
import com.tuapp.programacion2.work.LogSyncScheduler;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LogSyncScheduler.schedule(this);
    }
}
