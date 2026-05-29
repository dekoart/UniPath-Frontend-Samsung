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
        try {
            val btnBack = findViewById<ImageView>(R.id.btnBackArrow)
            btnBack.setOnClickListener {
                goToProfile()
            }
            val btnMain = findViewById<LinearLayout>(R.id.btnMain)
            btnMain.setOnClickListener {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            val btnFav = findViewById<LinearLayout>(R.id.btnFavorite)
            btnFav.setOnClickListener {
                startActivity(Intent(this, FavoriteActivity::class.java))
            }
            val btnNotif = findViewById<ImageView>(R.id.btnNotification)
            btnNotif.setOnClickListener {
                startActivity(Intent(this, NotificationActivity::class.java))
            }
            val btnProfileNav = findViewById<LinearLayout>(R.id.btnProfileNav)
            btnProfileNav?.setOnClickListener {
                finish()
            }

        } catch (e: Exception) {
            Toast.makeText(this, "Ошибка: ${e.message}", Toast.LENGTH_LONG).show()
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