package com.example.unipathapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.unipathapp.api.RetrofitClient
import com.example.unipathapp.models.UniversityFilterRequest
import kotlinx.coroutines.launch

class FilterActivity : AppCompatActivity() {
    private lateinit var etCity: EditText
    private lateinit var directionDropdown: AutoCompleteTextView
    private lateinit var educationDropdown: AutoCompleteTextView
    private lateinit var universityDropdown: AutoCompleteTextView
    private lateinit var hostelCheck: CheckBox
    private lateinit var militaryCheck: CheckBox
    private lateinit var exchangeCheck: CheckBox
    private lateinit var btnMain: LinearLayout
    private lateinit var btnFavorite: LinearLayout
    private lateinit var btnProfile: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        etCity = findViewById(R.id.etCity)
        directionDropdown = findViewById(R.id.directionDropdown)
        educationDropdown = findViewById(R.id.educationDropdown)
        universityDropdown = findViewById(R.id.universityDropdown)
        hostelCheck = findViewById(R.id.hostelCheck)
        militaryCheck = findViewById(R.id.militaryCheck)
        exchangeCheck = findViewById(R.id.exchangeCheck)
        btnMain = findViewById(R.id.btnMain)
        btnFavorite = findViewById(R.id.btnFavorite)
        btnProfile = findViewById(R.id.btnProfile)

        setupDropdowns()
        setupNavigation()
        // сброс фильтров
        findViewById<Button>(R.id.resetButton).setOnClickListener { clearFilters() }
        // применение фильтров
        findViewById<Button>(R.id.applyButton).setOnClickListener { applyFilters() }
    }

    private fun setupDropdowns() {
        val directions = arrayOf(
            "Все направления",
            "IT и программирование",
            "Экономика и финансы",
            "Медицина",
            "Юриспруденция",
            "Инженерия",
            "Дизайн и искусство"
        )
        val forms = arrayOf("Все формы", "Очная", "Заочная", "Очно-заочная", "Дистанционная")
        val types = arrayOf("Все типы", "Государственный", "Частный")
        directionDropdown.setAdapter(
            ArrayAdapter(
                this,
                android.R.layout.simple_dropdown_item_1line,
                directions
            )
        )
        educationDropdown.setAdapter(
            ArrayAdapter(
                this,
                android.R.layout.simple_dropdown_item_1line,
                forms
            )
        )
        universityDropdown.setAdapter(
            ArrayAdapter(
                this,
                android.R.layout.simple_dropdown_item_1line,
                types
            )
        )
        directionDropdown.isFocusableInTouchMode = true
        educationDropdown.isFocusableInTouchMode = true
        universityDropdown.isFocusableInTouchMode = true
    }

    private fun clearFilters() {
        etCity.text.clear()
        directionDropdown.setText("Все направления", false)
        educationDropdown.setText("Все формы", false)
        universityDropdown.setText("Все типы", false)
        hostelCheck.isChecked = false
        militaryCheck.isChecked = false
        exchangeCheck.isChecked = false
    }

    private fun applyFilters() {
        // собираем фильтры если пустые = null
        val city = etCity.text.toString().trim().ifBlank { null }
        val type = universityDropdown.text.toString().takeIf { it.isNotBlank() && it != "Все типы" }
        val hasDorm = if (hostelCheck.isChecked) true else null
        val hasMilitary = if (militaryCheck.isChecked) true else null
        val hasExchange = if (exchangeCheck.isChecked) true else null
        val request = UniversityFilterRequest(city, type, hasDorm, hasMilitary, hasExchange)

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.universityApi.searchUniversities(request)
                if (response.isSuccessful && response.body() != null) {
                    val results = response.body()!!
                    // передаём данные в ResultActivity
                    val intent = Intent(this@FilterActivity, ResultActivity::class.java).apply {
                        putExtra("FILTERS_CITY", city)
                        putExtra("FILTERS_TYPE", type)
                        putExtra("FILTERS_DORM", hasDorm == true)  // конвертим в boolean
                        putExtra("FILTERS_MILITARY", hasMilitary == true)
                        putExtra("FILTERS_EXCHANGE", hasExchange == true)
                        putExtra("UNIVERSITIES", ArrayList(results))  // сериализуем список
                    }
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this@FilterActivity,
                        "Ошибка: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                // ловим ошибки сети
                Toast.makeText(this@FilterActivity, "Нет связи", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupNavigation() {
        btnMain.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        btnFavorite.setOnClickListener { startActivity(Intent(this, FavoriteActivity::class.java)) }
        btnProfile.setOnClickListener { startActivity(Intent(this, ProfileActivity::class.java)) }
    }
}