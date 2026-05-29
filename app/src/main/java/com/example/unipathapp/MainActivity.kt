package com.example.unipathapp

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.lifecycleScope
import com.example.unipathapp.api.RetrofitClient
import com.example.unipathapp.models.UniversityFilterRequest
import com.example.unipathapp.models.UniversityResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var btnFavorite: ImageButton
    private lateinit var btnProfile: ImageButton
    private lateinit var btnFilters: AppCompatButton
    private lateinit var btnSearch: AppCompatButton
    private lateinit var etSearch: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Биндим вьюхи
        btnFavorite = findViewById(R.id.btnFavorite)
        btnProfile = findViewById(R.id.btnProfile)
        btnFilters = findViewById(R.id.btnFilters)
        btnSearch = findViewById(R.id.btnSearch)
        etSearch = findViewById(R.id.etSearch)

        // Кнопки навигации
        btnProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        btnFilters.setOnClickListener {
            startActivity(Intent(this, FilterActivity::class.java))
        }

        btnFavorite.setOnClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java))
        }

        // Кнопка "Найти" (поиск по названию)
        btnSearch.setOnClickListener {
            val query = etSearch.text.toString().trim()
            if (query.isEmpty()) {
                Toast.makeText(this, "Введите название вуза", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            searchUniversities(name = query)
        }

        // === КЛИКАБЕЛЬНЫЕ ТЕГИ (НАПРАВЛЕНИЯ) ===
        setupDirectionTags()
    }

    private fun setupDirectionTags() {
        val tags = listOf<TextView>(
            findViewById(R.id.tagIT),
            findViewById(R.id.tagMedicine),
            findViewById(R.id.tagEconomic),
            findViewById(R.id.tagJurisprudence),
            findViewById(R.id.tagDesign)
        )

        // Сопоставляем текст тега с направлением
        val directionMap = mapOf(
            "IT и программирование" to "IT и программирование",
            "Медицина" to "Медицина",
            "Экономика" to "Экономика",
            "Юриспруденция" to "Юриспруденция",
            "Дизайн" to "Дизайн"
        )

        tags.forEach { tag ->
            tag.setOnClickListener {
                val direction = directionMap[tag.text.toString()]
                if (direction != null) {
                    searchUniversities(direction = direction)
                }
            }
        }
    }

    private fun searchUniversities(name: String? = null, direction: String? = null) {
        lifecycleScope.launch {
            try {
                val request = UniversityFilterRequest(
                    name = name,
                    direction = direction
                )

                val response: Response<List<UniversityResponse>> =
                    RetrofitClient.universityApi.searchUniversities(request)

                if (response.isSuccessful && response.body() != null) {
                    val results = response.body()!!

                    val intent = Intent(this@MainActivity, ResultActivity::class.java).apply {
                        putExtra("UNIVERSITIES", ArrayList(results))
                    }
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Ошибка: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@MainActivity,
                    "Нет связи: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
                e.printStackTrace()
            }
        }
    }
}