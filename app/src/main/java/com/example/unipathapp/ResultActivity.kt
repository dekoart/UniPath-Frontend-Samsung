package com.example.unipathapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.unipathapp.api.RetrofitClient
import com.example.unipathapp.models.ProgramFilterRequest
import com.example.unipathapp.models.ProgramResponse
import kotlinx.coroutines.launch

class ResultActivity : AppCompatActivity() {
    private lateinit var programsContainer: LinearLayout
    private lateinit var tvCount: TextView
    private var programs: List<ProgramResponse> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // биндим вьюхи
        programsContainer = findViewById(R.id.programsContainer)
        tvCount = findViewById(R.id.tvCount)

        // получаем список из интента
        programs = intent.getSerializableExtra("RESULTS") as? List<ProgramResponse> ?: emptyList()

        // если пусто — грузим с сервера
        if (programs.isEmpty()) {
            loadPrograms()
        } else {
            showPrograms(programs)
        }
        setupNavigation()
    }

    private fun loadPrograms() {
        // собираем фильтры из интента
        val city = intent.getStringExtra("FILTERS_CITY")
        val category = intent.getStringExtra("FILTERS_CATEGORY")
        val form = intent.getStringExtra("FILTERS_FORM")
        val type = intent.getStringExtra("FILTERS_TYPE")

        val request = ProgramFilterRequest(
            city = city,
            category = category,
            form = form,
            type = type,
            hasBudget = null,
            hasDormitory = null,
            hasMilitary = null,
            hasExchange = null
        )

        // запрос в корутине
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.programApi.filterPrograms(request)
                if (response.isSuccessful && response.body() != null) {
                    programs = response.body()!!
                    showPrograms(programs)
                } else {
                    Toast.makeText(this@ResultActivity, "Ошибка загрузки", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@ResultActivity, "Нет связи", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showPrograms(programs: List<ProgramResponse>) {
        programsContainer.removeAllViews()

        programs.forEachIndexed { index, program ->
            // надуваем карточку из шаблона
            val card = LayoutInflater.from(this)
                .inflate(R.layout.item_program, programsContainer, false)

            // заполняем поля — айдишники маленькие как в лейауте
            card.findViewById<TextView>(R.id.Name).text = program.name
            card.findViewById<TextView>(R.id.University).text = program.universityName
            card.findViewById<TextView>(R.id.City).text = program.city
            card.findViewById<TextView>(R.id.Form).text = program.form
            card.findViewById<TextView>(R.id.Budget).text =
                if (program.budget != null && program.budget > 0)
                    "Бюджет: ${program.budget}" else "Только платное"

            // клик открывает детали
            card.setOnClickListener {
                val intent = Intent(this, UniversityDetailActivity::class.java)
                intent.putExtra("UNIVERSITY", program)
                startActivity(intent)
            }

            programsContainer.addView(card)

            // отступ между карточками
            if (index < programs.lastIndex) {
                val spacer = View(this)
                spacer.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 16
                )
                programsContainer.addView(spacer)
            }
        }
        tvCount.text = "по вашим параметрам Вам подойдёт: ${programs.size}"
    }

    private fun setupNavigation() {
        // нижнее меню
        findViewById<LinearLayout>(R.id.btnMain).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        findViewById<LinearLayout>(R.id.btnFavorite).setOnClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java))
        }
        findViewById<LinearLayout>(R.id.btnProfile).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}