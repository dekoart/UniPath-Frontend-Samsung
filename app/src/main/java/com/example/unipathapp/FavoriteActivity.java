package com.example.unipathapp;

import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;

public class FavoriteActivity extends AppCompatActivity {

    ImageButton btnMain, btnProfile, btnNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        btnMain = findViewById(R.id.btnMain);
        btnProfile = findViewById(R.id.btnProfile);
        btnNotification = findViewById(R.id.btnNotification);

        btnMain.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
        });

        btnProfile.setOnClickListener(v -> {
            startActivity(new Intent(this, ProfileActivity.class));
        });
        btnNotification.setOnClickListener(v -> {
            startActivity(new Intent(this, NotificationActivity.class));
        });
    }
}
