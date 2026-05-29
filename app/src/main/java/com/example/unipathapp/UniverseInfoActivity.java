package com.example.unipathapp;

import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class UniverseInfoActivity extends AppCompatActivity {
    ImageView imgFavorite;

    boolean isLiked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imgFavorite.setOnClickListener(v -> {

            if (isLiked) {

                imgFavorite.setImageResource(R.drawable.favoriteuni);
                isLiked = false;

            } else {

                imgFavorite.setImageResource(R.drawable.favoriteuni_);
                isLiked = true;

            }

        });

    }
}
