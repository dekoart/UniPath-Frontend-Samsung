package com.example.unipathapp;

import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
public class FiltersActivity extends AppCompatActivity {

    ImageButton btnMain, btnFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_dropdown);
    }
}
