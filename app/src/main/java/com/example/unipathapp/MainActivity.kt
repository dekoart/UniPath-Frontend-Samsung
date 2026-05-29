package com.example.unipathapp

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.lifecycleScope
import com.example.unipathapp.api.RetrofitClient
import com.example.unipathapp.models.UniversityFilterRequest
import com.example.unipathapp.models.UniversityResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var btnFilters: AppCompatButton
    private lateinit var btnSearch: AppCompatButton
    private lateinit var etSearch: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnFilters = findViewById(R.id.btnFilters)
        btnSearch = findViewById(R.id.btnSearch)
        etSearch = findViewById(R.id.etSearch)
        findViewById<View>(R.id.btnFavorite)?.setOnClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java))
        }

        findViewById<View>(R.id.btnProfile)?.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        btnFilters.setOnClickListener {
            startActivity(Intent(this, FilterActivity::class.java))
        }

        btnSearch.setOnClickListener {
            val query = etSearch.text.toString().trim()
            if (query.isEmpty()) {
                Toast.makeText(this, "Введите название вуза", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            searchUniversities(name = query, city = null, direction = null)
        }

        setupDirectionTags()
    }

    private fun setupDirectionTags() {
        val tagIds = listOf(
            R.id.tagIT,
            R.id.tagMedicine,
            R.id.tagEconomic,
            R.id.tagJurisprudence,
            R.id.tagDesign
        )

        val directionMap = mapOf(
            "IT и программирование" to "IT и программирование",
            "Медицина" to "Медицина",
            "Экономика" to "Экономика",
            "Юриспруденция" to "Юриспруденция",
            "Дизайн" to "Дизайн"
        )

        tagIds.forEach { id ->
            findViewById<View>(id)?.setOnClickListener {
                val tag = findViewById<TextView>(id)
                val direction = directionMap[tag?.text.toString()]
                if (direction != null) {
                    searchUniversities(direction = direction)
                }
            }
        }
    }

    private fun searchUniversities(
        name: String? = null,
        direction: String? = null,
        city: String? = null
    ) {
        lifecycleScope.launch {
            try {
                val request = UniversityFilterRequest(
                    name = name,
                    direction = direction,
                    city = city
                )

                val response = RetrofitClient.universityApi.searchUniversities(request)

                if (response.isSuccessful && response.body() != null) {
                    val results = response.body()!!

                    if (results.isEmpty()) {
                        Toast.makeText(this@MainActivity, "Вузы не найдены", Toast.LENGTH_SHORT)
                            .show()
                        return@launch
                    }

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
                Toast.makeText(this@MainActivity, "Нет связи", Toast.LENGTH_SHORT).show()
            }
        }
    }
}