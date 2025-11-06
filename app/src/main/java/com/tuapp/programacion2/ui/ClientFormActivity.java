package com.tuapp.programacion2.ui;

import static com.tuapp.programacion2.util.FileUtils.createTempImage;
import static com.tuapp.programacion2.util.FileUtils.getUriForFile;
import static com.tuapp.programacion2.util.FileUtils.toPart;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.tuapp.programacion2.R;
import com.tuapp.programacion2.data.Logger;
import com.tuapp.programacion2.model.Client;
import com.tuapp.programacion2.net.ApiClient;
import com.tuapp.programacion2.net.ApiService;

public class ClientFormActivity extends AppCompatActivity {
    private EditText etCi, etNombre, etDireccion, etTelefono;
    private File f1, f2, f3;
    private File lastTarget;

    private final ActivityResultLauncher<Intent> cameraLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), res -> {
                if (res.getResultCode() == RESULT_OK) {
                    if (lastTarget != null) {
                        Toast.makeText(this, "Foto guardada: " + lastTarget.getName(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_form);

        etCi = findViewById(R.id.etCi);
        etNombre = findViewById(R.id.etNombre);
        etDireccion = findViewById(R.id.etDireccion);
        etTelefono = findViewById(R.id.etTelefono);

        Button b1 = findViewById(R.id.btnFoto1);
        Button b2 = findViewById(R.id.btnFoto2);
        Button b3 = findViewById(R.id.btnFoto3);
        Button enviar = findViewById(R.id.btnEnviar);

        b1.setOnClickListener(v -> capture(1));
        b2.setOnClickListener(v -> capture(2));
        b3.setOnClickListener(v -> capture(3));
        enviar.setOnClickListener(v -> enviar());
    }

    private void capture(int idx) {
        try {
            File out = createTempImage(this);
            Uri uri = getUriForFile(this, out, getPackageName()+".fileprovider");
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            lastTarget = out;
            switch (idx) {
                case 1: f1 = out; break;
                case 2: f2 = out; break;
                case 3: f3 = out; break;
            }
            cameraLauncher.launch(intent);
        } catch (Exception e) {
            Logger.log(this, e.getMessage(), "ClientFormActivity");
            Toast.makeText(this, "Error al abrir cámara: "+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void enviar() {
        try {
            Client c = new Client(
                etCi.getText().toString(),
                etNombre.getText().toString(),
                etDireccion.getText().toString(),
                etTelefono.getText().toString()
            );
            String json = new Gson().toJson(c);
            RequestBody jsonBody = RequestBody.create(json, MediaType.parse("application/json"));

            ApiService api = ApiClient.get();
            Call<ResponseBody> call = api.uploadClient(
                toPart(f1, "fotoCasa1"),
                toPart(f2, "fotoCasa2"),
                toPart(f3, "fotoCasa3"),
                jsonBody
            );
            call.enqueue(new Callback<ResponseBody>() {
                @Override public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Toast.makeText(ClientFormActivity.this, "Enviado! code="+response.code(), Toast.LENGTH_LONG).show();
                }
                @Override public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Logger.log(ClientFormActivity.this, t.getMessage(), "ClientFormActivity");
                    Toast.makeText(ClientFormActivity.this, "Error enviando: "+t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            Logger.log(this, e.getMessage(), "ClientFormActivity");
            Toast.makeText(this, "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
