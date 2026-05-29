package com.example.unipathapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var btnMain: ImageButton
    private lateinit var btnFavorite: ImageButton
    private lateinit var btnAbout: Button
    private lateinit var btnNotification: ImageButton
    private lateinit var btnLogout: ImageButton
    private lateinit var btnPoint: Button

    private lateinit var btnFavoriteStroke: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        btnMain = findViewById(R.id.btnMain)
        btnFavorite = findViewById(R.id.btnFavorite)
        btnAbout = findViewById(R.id.btnAbout)
        btnNotification = findViewById(R.id.btnNotification)
        btnLogout = findViewById(R.id.btnLogout)
        btnPoint = findViewById(R.id.btnPoint)
        btnFavoriteStroke = findViewById(R.id.btnFavoriteStroke)

        btnMain.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        btnFavorite.setOnClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java))
        }

        btnFavoriteStroke.setOnClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java))
        }

        btnAbout.setOnClickListener {
            startActivity(Intent(this, AboutProjectActivity::class.java))
        }

        btnNotification.setOnClickListener {
            startActivity(Intent(this, NotificationActivity::class.java))
        }

        btnLogout.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish() // закрыть профиль
        }

        btnPoint.setOnClickListener {
            startActivity(Intent(this, PointsActivity::class.java))
        }
    }
}