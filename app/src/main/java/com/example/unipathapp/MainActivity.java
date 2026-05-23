package com.example.unipathapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ImageButton btnFavorite, btnProfile;
    androidx.appcompat.widget.AppCompatButton btnFilters, btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnFavorite = findViewById(R.id.btnFavorite);
        btnProfile = findViewById(R.id.btnProfile);
        btnFilters = findViewById(R.id.btnFilters);
        btnSearch = findViewById(R.id.btnSearch);

        btnFavorite.setOnClickListener(v -> {
            startActivity(new Intent(this, FavoriteActivity.class));
        });

        btnProfile.setOnClickListener(v -> {
            startActivity(new Intent(this, ProfileActivity.class));
        });

        btnFilters.setOnClickListener(v -> {
            startActivity(new Intent(this, FiltersActivity.class));
        });
        btnSearch.setOnClickListener(v -> {
            startActivity(new Intent(this, ResultActivity.class));
        });
    }
}