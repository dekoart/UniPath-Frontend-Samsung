package com.example.unipathapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class LogInActivity extends AppCompatActivity {
    androidx.appcompat.widget.AppCompatButton btnLogin;

    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
        });
        btnRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, SignInActivity.class));
        });
    }
}
