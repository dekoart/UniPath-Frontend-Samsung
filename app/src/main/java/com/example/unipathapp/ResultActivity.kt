package com.example.unipathapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.unipathapp.api.RetrofitClient
import com.example.unipathapp.models.UniversityFilterRequest
import com.example.unipathapp.models.UniversityResponse
import kotlinx.coroutines.launch

class ResultActivity : AppCompatActivity() {
    private lateinit var container: LinearLayout
    private var universities: List<UniversityResponse> = emptyList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        container = findViewById(R.id.container)
        universities =
            intent.getSerializableExtra("UNIVERSITIES") as? List<UniversityResponse> ?: emptyList()
        if (universities.isEmpty()) {
            loadUniversities()
        } else {
            showUniversities(universities)
        }
        setupNavigation()
    }

    private fun loadUniversities() {
        val city = intent.getStringExtra("FILTERS_CITY")
        val type = intent.getStringExtra("FILTERS_TYPE")
        val hasDorm = intent.getBooleanExtra("FILTERS_DORM", false)
        val hasMil = intent.getBooleanExtra("FILTERS_MILITARY", false)
        val hasExch = intent.getBooleanExtra("FILTERS_EXCHANGE", false)
        val request = UniversityFilterRequest(
            city = city?.takeIf { it.isNotBlank() },
            type = type?.takeIf { it != "Все типы" },
            hasDormitory = if (hasDorm) true else null,
            hasMilitary = if (hasMil) true else null,
            hasExchange = if (hasExch) true else null,
        )

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.universityApi.searchUniversities(request)
                if (response.isSuccessful && response.body() != null) {
                    universities = response.body()!!
                    showUniversities(universities)
                } else {
                    Toast.makeText(
                        this@ResultActivity,
                        "Ошибка: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@ResultActivity, "Нет связи", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showUniversities(universities: List<UniversityResponse>) {
        container.removeAllViews()
        universities.forEach { uni ->
            val card = LayoutInflater.from(this)
                .inflate(R.layout.item_program, container, false)
            card.findViewById<TextView>(R.id.name).text = uni.name
            card.findViewById<TextView>(R.id.type).text = uni.type
            card.findViewById<TextView>(R.id.city).text = uni.city
            card.findViewById<TextView>(R.id.programs).text = "${uni.programsCount ?: 0} программ"
            card.findViewById<TextView>(R.id.score).text =
                if (uni.minBudgetScore != null) "от ${uni.minBudgetScore} баллов" else ""
            val logo = card.findViewById<ImageView>(R.id.logo)
            if (!uni.logo.isNullOrEmpty()) {
                Glide.with(this)
                    .load(uni.logo)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .centerCrop()
                    .into(logo)
            }
            card.setOnClickListener {
                val intent = Intent(this, UniversityDetailActivity::class.java)
                intent.putExtra("UNIVERSITY", uni)
                startActivity(intent)
            }
            container.addView(card)
            container.addView(View(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 12
                )
            })
        }
    }

    private fun setupNavigation() {
        findViewById<View>(R.id.btnMain)?.apply {
            isClickable = true
            isFocusable = true
            setOnClickListener {
                startActivity(Intent(this@ResultActivity, MainActivity::class.java))
                finish()
            }
        }
        findViewById<View>(R.id.btnFavorite)?.apply {
            isClickable = true
            isFocusable = true
            setOnClickListener {
                startActivity(Intent(this@ResultActivity, FavoriteActivity::class.java))
            }
        }
        findViewById<View>(R.id.btnProfile)?.apply {
            isClickable = true
            isFocusable = true
            setOnClickListener {
                startActivity(Intent(this@ResultActivity, ProfileActivity::class.java))
            }
        }
    }
}