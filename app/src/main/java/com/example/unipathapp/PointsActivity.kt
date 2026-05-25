package com.example.unipathapp;

import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
public class PointsActivity extends AppCompatActivity {

    ImageButton btnMain, btnFavorite, btnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points);

        btnMain = findViewById(R.id.btnMain);
        btnFavorite = findViewById(R.id.btnFavorite);
        btnProfile = findViewById(R.id.btnProfile);

        btnMain.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
        });

        btnFavorite.setOnClickListener(v -> {
            startActivity(new Intent(this, FavoriteActivity.class));
        });
        btnProfile.setOnClickListener(v -> {
            startActivity(new Intent(this, ProfileActivity.class));
        });
    }
}