package com.example.unipathapp

import android.content.Intent
import android.os.Bundle
import android.view.View
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
    private lateinit var btnMain: View
    private lateinit var btnFavorite: View
    private lateinit var btnProfile: View

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
        btnMain = findViewById<View>(R.id.btnMain)
        btnFavorite = findViewById<View>(R.id.btnFavorite)
        btnProfile = findViewById<View>(R.id.btnProfile)

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
        val city = etCity.text.toString().trim().ifBlank { null }
        val direction = directionDropdown.text.toString()
            .takeIf { it.isNotBlank() && it != "Все направления" }
        val type = universityDropdown.text.toString()
            .takeIf { it.isNotBlank() && it != "Все типы" }
        val hasDormitory = if (hostelCheck.isChecked) true else null
        val hasMilitary = if (militaryCheck.isChecked) true else null
        val hasExchange = if (exchangeCheck.isChecked) true else null
        val request = UniversityFilterRequest(
            city = city,
            name = null,
            direction = direction,
            type = type,
            hasDormitory = hasDormitory,
            hasMilitary = hasMilitary,
            hasExchange = hasExchange
        )

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.universityApi.searchUniversities(request)
                if (response.isSuccessful && response.body() != null) {
                    val results = response.body()!!
                    val intent = Intent(this@FilterActivity, ResultActivity::class.java).apply {
                        putExtra("FILTERS_CITY", city)
                        putExtra("FILTERS_TYPE", type)
                        putExtra("FILTERS_DORM", hasDormitory == true)   // ← Исправлено
                        putExtra("FILTERS_MILITARY", hasMilitary == true) // ← Исправлено
                        putExtra("FILTERS_EXCHANGE", hasExchange == true)
                        putExtra("UNIVERSITIES", ArrayList(results))
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
                Toast.makeText(this@FilterActivity, "Нет связи: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
                e.printStackTrace()
            }
        }
    }

    private fun setupNavigation() {
        btnMain.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        btnFavorite.setOnClickListener { startActivity(Intent(this, FavoriteActivity::class.java)) }
        btnProfile.setOnClickListener { startActivity(Intent(this, ProfileActivity::class.java)) }
    }
}