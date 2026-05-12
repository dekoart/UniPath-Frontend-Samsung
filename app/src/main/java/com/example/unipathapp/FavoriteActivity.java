package com.example.unipathapp;

import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;

public class FavoriteActivity extends AppCompatActivity {

    ImageButton btnMain, btnAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        btnMain = findViewById(R.id.btnMain);
        btnAbout = findViewById(R.id.btnAbout);

        btnMain.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
        });

        btnAbout.setOnClickListener(v -> {
            startActivity(new Intent(this, AboutProjectActivity.class));
        });
    }
}
