package com.example.unipathapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ImageButton btnFavorite, btnAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_dropdown);

        //btnFavorite = findViewById(R.id.btnFavorite);
        //btnAbout = findViewById(R.id.btnAbout);

        btnFavorite.setOnClickListener(v -> {
            startActivity(new Intent(this, FavoriteActivity.class));
        });

        btnAbout.setOnClickListener(v -> {
            startActivity(new Intent(this, AboutProjectActivity.class));
        });
    }
}