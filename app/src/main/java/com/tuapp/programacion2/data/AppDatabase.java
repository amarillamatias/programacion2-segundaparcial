package com.tuapp.programacion2.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {LogApp.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;
    public abstract LogDao logDao();
    public static AppDatabase get(Context ctx){
        if(INSTANCE==null){
            synchronized (AppDatabase.class){
                if(INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(ctx.getApplicationContext(), AppDatabase.class, "app_logs.db").build();
                }
            }
        }
        return INSTANCE;
    }
}
