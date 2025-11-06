package com.tuapp.programacion2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnClient = findViewById(R.id.btnClientForm);
        Button btnMulti = findViewById(R.id.btnMultiUpload);

        btnClient.setOnClickListener(v -> startActivity(new Intent(this, com.tuapp.programacion2.ui.ClientFormActivity.class)));
        btnMulti.setOnClickListener(v -> startActivity(new Intent(this, com.tuapp.programacion2.ui.MultiUploadActivity.class)));
    }
}
