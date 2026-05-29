package com.example.unipathapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class FavoriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        val btnMain = findViewById<ImageButton>(R.id.btnMain)
        val btnProfile = findViewById<ImageButton>(R.id.btnProfile)
        val btnNotification = findViewById<ImageButton>(R.id.btnNotification)
        btnMain.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        btnProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        btnNotification.setOnClickListener {
            startActivity(Intent(this, NotificationActivity::class.java))
        }
    }
}