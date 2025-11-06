package com.tuapp.programacion2.work;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import com.tuapp.programacion2.data.AppDatabase;
import com.tuapp.programacion2.data.LogApp;
import com.tuapp.programacion2.net.ApiClient;
import com.tuapp.programacion2.net.ApiService;

public class LogSyncWorker extends Worker {
    public LogSyncWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            List<LogApp> logs = AppDatabase.get(getApplicationContext()).logDao().getAll();
            if (logs.isEmpty()) return Result.success();

            ApiService api = ApiClient.get();
            Call<ResponseBody> call = api.sendLogs(logs);
            Response<ResponseBody> resp = call.execute();
            if (resp.isSuccessful()) {
                AppDatabase.get(getApplicationContext()).logDao().clear();
            }
            return Result.success();
        } catch (Exception e) {
            return Result.retry();
        }
    }
}
