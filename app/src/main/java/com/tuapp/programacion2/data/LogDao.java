package com.tuapp.programacion2.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface LogDao {
    @Insert long insert(LogApp log);
    @Query("SELECT * FROM logs_app ORDER BY id ASC") List<LogApp> getAll();
    @Query("DELETE FROM logs_app") void clear();
}
