package com.tuapp.programacion2.data;

import android.content.Context;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executors;

public class Logger {
    public static void log(Context ctx, String descripcion, String clase){
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                String ts = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                AppDatabase.get(ctx).logDao().insert(new LogApp(ts, descripcion, clase));
            } catch (Exception ignored) { }
        });
    }
}
