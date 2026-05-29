package com.example.unipathapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.lifecycleScope
import com.example.unipathapp.api.RetrofitClient
import com.example.unipathapp.models.ScoresRequest
import com.example.unipathapp.models.SubjectScore
import kotlinx.coroutines.launch

class PointsActivity : AppCompatActivity() {
    private lateinit var Russian: EditText
    private lateinit var Math: EditText
    private lateinit var ProfileMath: EditText
    private lateinit var Physics: EditText
    private lateinit var Informatics: EditText
    private lateinit var Chemistry: EditText
    private lateinit var Biology: EditText
    private lateinit var Social: EditText
    private lateinit var History: EditText
    private lateinit var Literature: EditText
    private lateinit var Geography: EditText
    private lateinit var English: EditText
    private lateinit var Chinese: EditText
    private lateinit var ForeignLanguage: EditText
    private lateinit var btnSaveScores: AppCompatButton
    private lateinit var btnMain: ImageButton
    private lateinit var btnFavorite: ImageButton
    private lateinit var btnProfile: ImageButton

    private lateinit var btnBackArrow: ImageButton
    private lateinit var prefs: SharedPreferences
    private var currentUserId: Long = 1L
    private lateinit var subjectInputs: Map<Long, EditText>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_points)

        prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
        currentUserId = prefs.getLong("user_id", 1L) // если юзер не найден берём 1

        initViews()
        setupNavigation()
        setupSaveButton()
        loadScores()
    }

    private fun initViews() {
        Russian = findViewById(R.id.Russian)
        Math = findViewById(R.id.Math)
        ProfileMath = findViewById(R.id.ProfileMath)
        Physics = findViewById(R.id.Physics)
        Informatics = findViewById(R.id.Informatics)
        Chemistry = findViewById(R.id.Chemistry)
        Biology = findViewById(R.id.Biology)
        Social = findViewById(R.id.Social)
        History = findViewById(R.id.History)
        Literature = findViewById(R.id.Literature)
        Geography = findViewById(R.id.Geography)
        English = findViewById(R.id.English)
        Chinese = findViewById(R.id.Chinese)
        ForeignLanguage = findViewById(R.id.ForeignLanguage)
        btnSaveScores = findViewById(R.id.btnSaveScores)

        // мапим id предмета из бд на поле ввода чтобы потом легко ходить по всем
        subjectInputs = mapOf(
            1L to Russian,
            2L to Math,
            3L to ProfileMath,
            4L to Physics,
            5L to Informatics,
            6L to Chemistry,
            7L to Biology,
            8L to Social,
            9L to History,
            10L to Literature,
            11L to Geography,
            12L to English,
            13L to Chinese,
            14L to ForeignLanguage
        )
    }

    private fun setupNavigation() {
        btnMain = findViewById(R.id.btnMain)
        btnFavorite = findViewById(R.id.btnFavorite)
        btnProfile = findViewById(R.id.btnProfile)
        btnBackArrow = findViewById(R.id.btnBackArrow)

        btnMain.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        btnFavorite.setOnClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java))
        }
        btnProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        btnBackArrow.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    private fun setupSaveButton() {
        btnSaveScores.setOnClickListener {
            saveScores()
        }
    }

    private fun saveScores() {
        var hasError = false
        var firstInvalidField: EditText? = null
        //  проверяем все поля на валидность
        for ((subjectId, editText) in subjectInputs) {
            val input = editText.text.toString().trim()
            if (input.isNotEmpty()) {
                try {
                    val value = input.toInt()
                    if (value < 0 || value > 100) {
                        // находим название предмета для сообщения
                        val subjectName = getSubjectNameById(subjectId)
                        editText.error = "От 0 до 100"
                        editText.requestFocus()

                        if (!hasError) {
                            firstInvalidField = editText
                            Toast.makeText(
                                this,
                                "Баллы по предмету \"$subjectName\" должны быть от 0 до 100",
                                Toast.LENGTH_LONG
                            ).show()
                            hasError = true
                        }
                    }
                } catch (e: NumberFormatException) {
                    editText.error = "Только числа"
                    if (!hasError) {
                        firstInvalidField = editText
                        hasError = true
                    }
                }
            }
        }

        // есть ошибки  не сохраняем
        if (hasError) {
            firstInvalidField?.requestFocus()
            return
        }
        val scoresList = mutableListOf<SubjectScore>()
        for ((subjectId, editText) in subjectInputs) {
            val input = editText.text.toString().trim()
            val score = if (input.isNotEmpty()) input.toInt() else null
            scoresList.add(SubjectScore(subjectId, score))
        }

        val request = ScoresRequest(currentUserId, scoresList)

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.scoresApi.saveScores(request)
                if (response.isSuccessful) {
                    Toast.makeText(this@PointsActivity, "Баллы сохранены!", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    val errorMsg = response.errorBody()?.string() ?: "Неизвестная ошибка"
                    Toast.makeText(this@PointsActivity, "Ошибка: $errorMsg", Toast.LENGTH_SHORT)
                        .show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@PointsActivity, "Нет связи с сервером", Toast.LENGTH_SHORT)
                    .show()
                e.printStackTrace()
            }
        }
    }

    // получаем название предмета по ID
    private fun getSubjectNameById(id: Long): String {
        return when (id) {
            1L -> "Русский язык"
            2L -> "Математика"
            3L -> "Профильная математика"
            4L -> "Физика"
            5L -> "Информатика"
            6L -> "Химия"
            7L -> "Биология"
            8L -> "Обществознание"
            9L -> "История"
            10L -> "Литература"
            11L -> "География"
            12L -> "Английский"
            13L -> "Китайский"
            14L -> "Иностранный язык"
            else -> "Предмет"
        }
    }

    private fun loadScores() {
        lifecycleScope.launch { // тоже в корутине чтобы не фризить интерфейс
            try {
                val response = RetrofitClient.scoresApi.getScores(currentUserId)
                if (response.isSuccessful && response.body() != null) {
                    val scoresResponse = response.body()!!
                    for (score in scoresResponse.scores) {
                        val editText = subjectInputs[score.subjectId]
                        if (editText != null && score.score != null) { // подставляем если нашли поле и балл не null
                            editText.setText(score.score.toString())
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}