package com.tuapp.programacion2.work;

import android.content.Context;
import java.util.concurrent.TimeUnit;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class LogSyncScheduler {
    private static final String UNIQUE = "log_sync_chain";
    public static void schedule(Context ctx) {
        OneTimeWorkRequest req = new OneTimeWorkRequest.Builder(LogSyncWorker.class)
                .setInitialDelay(5, TimeUnit.MINUTES)
                .build();
        WorkManager.getInstance(ctx).enqueueUniqueWork(UNIQUE, ExistingWorkPolicy.KEEP, req);
    }
}
