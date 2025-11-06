package com.tuapp.programacion2.ui;

import static com.tuapp.programacion2.util.ZipUtils.zipUris;
import static com.tuapp.programacion2.util.FileUtils.toZipPart;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.tuapp.programacion2.R;
import com.tuapp.programacion2.data.Logger;
import com.tuapp.programacion2.net.ApiClient;
import com.tuapp.programacion2.net.ApiService;

public class MultiUploadActivity extends AppCompatActivity {
    private final List<Uri> seleccionados = new ArrayList<>();
    private EditText etCi;

    private final ActivityResultLauncher<String> picker =
            registerForActivityResult(new ActivityResultContracts.GetMultipleContents(), uris -> {
                if (uris != null) {
                    seleccionados.clear();
                    seleccionados.addAll(uris);
                    Toast.makeText(this, uris.size()+" archivo(s) seleccionado(s)", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_upload);

        etCi = findViewById(R.id.etCiZip);
        Button sel = findViewById(R.id.btnSeleccionar);
        Button subir = findViewById(R.id.btnComprimirSubir);

        sel.setOnClickListener(v -> picker.launch("*/*"));
        subir.setOnClickListener(v -> subirZip());
    }

    private void subirZip() {
        try {
            if (seleccionados.isEmpty()) {
                Toast.makeText(this, "Selecciona al menos 1 archivo", Toast.LENGTH_SHORT).show();
                return;
            }
            File zip = zipUris(this, seleccionados);
            RequestBody ci = RequestBody.create(etCi.getText().toString(), MediaType.parse("text/plain"));

            ApiService api = ApiClient.get();
            Call<ResponseBody> call = api.uploadZip(toZipPart(zip, "filesZip"), ci);
            call.enqueue(new Callback<ResponseBody>() {
                @Override public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Toast.makeText(MultiUploadActivity.this, "ZIP enviado! code="+response.code(), Toast.LENGTH_LONG).show();
                }
                @Override public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Logger.log(MultiUploadActivity.this, t.getMessage(), "MultiUploadActivity");
                    Toast.makeText(MultiUploadActivity.this, "Error: "+t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            Logger.log(this, e.getMessage(), "MultiUploadActivity");
            Toast.makeText(this, "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
