package com.example.unipathapp;

import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
public class AboutProjectActivity extends AppCompatActivity {

    ImageButton btnNotification, btnProfile, btnFavorite, btnMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_project);

        btnProfile = findViewById(R.id.btnProfile);
        btnMain = findViewById(R.id.btnMain);
        btnFavorite = findViewById(R.id.btnFavorite);
        btnNotification = findViewById(R.id.btnNotification);

        btnMain.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
        });
        btnFavorite.setOnClickListener(v -> {
            startActivity(new Intent(this, FavoriteActivity.class));
        });
        btnProfile.setOnClickListener(v -> {
            startActivity(new Intent(this, ProfileActivity.class));
        });
        btnNotification.setOnClickListener(v -> {
            startActivity(new Intent(this, NotificationActivity.class));
        });
    }
}
