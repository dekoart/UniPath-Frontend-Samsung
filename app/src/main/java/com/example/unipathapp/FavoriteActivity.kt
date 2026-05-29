package com.example.unipathapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavoriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_favorite)

        val recyclerView =
            findViewById<RecyclerView>(R.id.recyclerFavorites)

        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = ProgramAdapter(FavoritesManager.favoritesList)

        findViewById<ImageButton>(R.id.btnMain).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            }

        findViewById<ImageButton>(R.id.btnProfile).setOnClickListener {
                startActivity(Intent(this, ProfileActivity::class.java)
                )
            }
    }
}