package com.example.unipathapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AboutProjectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_project)
        findViewById<LinearLayout>(R.id.btnMain)?.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        findViewById<LinearLayout>(R.id.btnFavorite)?.setOnClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java))
        }
        findViewById<ImageView>(R.id.btnNotification)?.setOnClickListener {
            startActivity(Intent(this, NotificationActivity::class.java))
        }
        findViewById<LinearLayout>(R.id.btnProfileNav)?.setOnClickListener {
            finish()
        }
    }

    private fun goToProfile() {
        try {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        } catch (_: Exception) {
            finish()
        }
    }
}