package com.example.unipathapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.unipathapp.api.RetrofitClient
import com.example.unipathapp.models.ProgramFilterRequest
import kotlinx.coroutines.launch

class FilterActivity : AppCompatActivity() {
    private lateinit var etCity: EditText
    private lateinit var directionDropdown: AutoCompleteTextView
    private lateinit var educationDropdown: AutoCompleteTextView
    private lateinit var universityDropdown: AutoCompleteTextView
    private lateinit var budgetCheck: CheckBox
    private lateinit var hostelCheck: CheckBox
    private lateinit var militaryCheck: CheckBox
    private lateinit var exchangeCheck: CheckBox
    private lateinit var resetButton: Button
    private lateinit var applyButton: Button
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
        budgetCheck = findViewById(R.id.budgetCheck)
        hostelCheck = findViewById(R.id.hostelCheck)
        militaryCheck = findViewById(R.id.militaryCheck)
        exchangeCheck = findViewById(R.id.exchangeCheck)
        resetButton = findViewById(R.id.resetButton)
        applyButton = findViewById(R.id.applyButton)
        btnMain = findViewById(R.id.btnMain)
        btnFavorite = findViewById(R.id.btnFavorite)
        btnProfile = findViewById(R.id.btnProfile)

        setupDropdowns()
        setupNavigation()
        resetButton.setOnClickListener { clearFilters() }
        applyButton.setOnClickListener { applyFilters() }
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

        val directionAdapter =
            ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, directions)
        val formAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, forms)
        val typeAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, types)

        directionDropdown.setAdapter(directionAdapter)
        educationDropdown.setAdapter(formAdapter)
        universityDropdown.setAdapter(typeAdapter)

        // чтобы клава не вылезала при нажатии
        directionDropdown.isFocusableInTouchMode = true
        educationDropdown.isFocusableInTouchMode = true
        universityDropdown.isFocusableInTouchMode = true
    }

    private fun clearFilters() {
        etCity.text.clear()
        directionDropdown.setText("Все направления", false)
        educationDropdown.setText("Все формы", false)
        universityDropdown.setText("Все типы", false)
        budgetCheck.isChecked = false
        hostelCheck.isChecked = false
        militaryCheck.isChecked = false
        exchangeCheck.isChecked = false
    }

    private fun applyFilters() {
        // собираем фильтры если пустые = null
        val city = etCity.text.toString().trim().ifBlank { null }
        val category = directionDropdown.text.toString()
            .takeIf { it.isNotBlank() && it != "Все направления" }
        val form = educationDropdown.text.toString()
            .takeIf { it.isNotBlank() && it != "Все формы" }
        val type = universityDropdown.text.toString()
            .takeIf { it.isNotBlank() && it != "Все типы" }
        val request = ProgramFilterRequest(
            city = city,
            category = category,
            form = form,
            type = type,
            hasBudget = if (budgetCheck.isChecked) true else null,
            hasDormitory = if (hostelCheck.isChecked) true else null,
            hasMilitary = if (militaryCheck.isChecked) true else null,
            hasExchange = if (exchangeCheck.isChecked) true else null
        )
        lifecycleScope.launch { // запрос на сервер в корутине
            try {
                val response = RetrofitClient.programApi.filterPrograms(request)
                if (response.isSuccessful && response.body() != null) {
                    val results = response.body()!!
                    // передаем данные в результат
                    val intent = Intent(this@FilterActivity, ResultActivity::class.java)
                    intent.putExtra("FILTERS_CITY", city)
                    intent.putExtra("FILTERS_CATEGORY", category)
                    intent.putExtra("FILTERS_FORM", form)
                    intent.putExtra("FILTERS_TYPE", type)
                    intent.putExtra("RESULTS_COUNT", results.size)
                    intent.putExtra("RESULTS", ArrayList(results))
                    startActivity(intent)
                } else {
                    Toast.makeText(this@FilterActivity, "Ошибка фильтрации", Toast.LENGTH_SHORT)
                        .show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@FilterActivity, "Нет связи с сервером", Toast.LENGTH_SHORT)
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