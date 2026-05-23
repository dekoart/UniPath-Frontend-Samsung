package com.example.unipathapp;

import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
public class ProfileActivity extends AppCompatActivity {

    ImageButton btnMain, btnFavorite, btnAbout, btnNotification, btnLogout;
    Button btnPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnMain = findViewById(R.id.btnMain);
        btnFavorite = findViewById(R.id.btnFavorite);
        btnAbout = findViewById(R.id.btnAbout);
        btnNotification = findViewById(R.id.btnNotification);
        btnLogout = findViewById(R.id.btnLogout);
        btnPoint = findViewById(R.id.btnPoint);

        btnMain.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
        });

        btnFavorite.setOnClickListener(v -> {
            startActivity(new Intent(this, FavoriteActivity.class));
        });
        btnAbout.setOnClickListener(v -> {
            startActivity(new Intent(this, AboutProjectActivity.class));
        });
        btnNotification.setOnClickListener(v -> {
            startActivity(new Intent(this, NotificationActivity.class));
        });
        btnLogout.setOnClickListener(v -> {
            startActivity(new Intent(this, LogInActivity.class));
        });
        btnPoint.setOnClickListener(v -> {
            startActivity(new Intent(this, PointsActivity.class));
        });
    }
}
