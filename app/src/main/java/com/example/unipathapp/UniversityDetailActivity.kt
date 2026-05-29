package com.example.unipathapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.unipathapp.models.UniversityResponse

class UniversityDetailActivity : AppCompatActivity() {

    private lateinit var university: UniversityResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_university_detail)

        university = intent.getSerializableExtra("UNIVERSITY") as? UniversityResponse ?: run {
            Toast.makeText(this, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        findViewById<TextView>(R.id.name).text = university.name
        findViewById<TextView>(R.id.address).text = university.address
        findViewById<TextView>(R.id.budget_score).text =
            university.minBudgetScore?.let { "от $it баллов" } ?: "Информация уточняется"
        findViewById<TextView>(R.id.budget_places).text =
            university.budgetPlaces?.toString() ?: "—"
        findViewById<TextView>(R.id.paid_places).text =
            university.paidPlaces?.toString() ?: "—"
        findViewById<TextView>(R.id.price).text =
            university.pricePerYear?.let { "от ${it}₽" } ?: "Информация уточняется"
        findViewById<TextView>(R.id.has_dormitory).text =
            if (university.hasDormitory == true) "Да" else "Нет"
        findViewById<TextView>(R.id.type).text = university.type
        findViewById<TextView>(R.id.phone).text = university.phone ?: "Не указан"
        findViewById<TextView>(R.id.email).text = university.email ?: "Не указан"
        findViewById<TextView>(R.id.website).text = university.website ?: "Не указан"
        findViewById<TextView>(R.id.admission_hours).text =
            university.admissionHours ?: "Не указаны"
        findViewById<TextView>(R.id.admission_phone).text =
            university.admissionPhone ?: "Не указан"
        findViewById<TextView>(R.id.admission_website).text =
            university.admissionWebsite ?: "Не указан"
        val photoImageView = findViewById<ImageView>(R.id.photo)
        if (!university.logo.isNullOrEmpty()) {
            Glide.with(this)
                .load(university.logo)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .centerCrop()
                .into(photoImageView)
        } else {
            photoImageView.setImageResource(R.drawable.ic_launcher_background)
        }
        findViewById<ImageView>(R.id.back).setOnClickListener {
            finish()
        }
        setupNavigation()
    }

    private fun setupNavigation() {
        findViewById<View>(R.id.btnMain)?.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        findViewById<View>(R.id.btnFavorite)?.setOnClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java))
        }
        findViewById<View>(R.id.btnProfile)?.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}