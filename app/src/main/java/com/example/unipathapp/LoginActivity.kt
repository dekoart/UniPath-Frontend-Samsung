package com.example.unipathapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import com.example.unipathapp.api.RetrofitClient
import com.example.unipathapp.models.LoginRequest

class LoginActivity : AppCompatActivity() {
    private lateinit var emailEdit: EditText
    private lateinit var passwordEdit: EditText
    private lateinit var btnLogin: AppCompatButton
    private lateinit var btnRegister: Button
    private lateinit var showPassword: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailEdit = findViewById(R.id.emailEdit)
        passwordEdit = findViewById(R.id.passwordEdit)
        btnLogin = findViewById(R.id.btnLogin)
        btnRegister = findViewById(R.id.btnRegister)
        showPassword = findViewById(R.id.showPassword)
        showPassword.setOnClickListener { togglePasswordVisibility(passwordEdit) }
        btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        btnLogin.setOnClickListener {
            val email = emailEdit.text.toString().trim()
            val password = passwordEdit.text.toString()
            if (email.isEmpty() || password.isEmpty()) { // не пускаем пустые запросы
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            lifecycleScope.launch { // корутина чтобы сеть не блокировала интерфейс
                try {
                    val response = RetrofitClient.authApi.login(
                        LoginRequest(email, password)
                    )
                    if (response.isSuccessful) {
                        val token = response.body() ?: ""
                        // сохраняем токен
                        val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
                        prefs.edit().putString("auth_token", token).apply()
                        Toast.makeText(this@LoginActivity, "Вход успешен!", Toast.LENGTH_SHORT)
                            .show()
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish() // закрываем вход чтобы назад не вернуться
                    } else {
                        val errorMessage = response.body() ?: "Неизвестная ошибка"
                        Toast.makeText(this@LoginActivity, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) { // ловим обрыв связи
                    Toast.makeText(this@LoginActivity, "Нет связи с сервером", Toast.LENGTH_SHORT)
                        .show()
                    e.printStackTrace()
                }
            }
        }
    }

    private fun togglePasswordVisibility(editText: EditText) { // переключает пароль между точками и обычным текстом
        val isPasswordVisible = editText.inputType ==
                (android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
        editText.inputType = if (isPasswordVisible)
            android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
        else
            android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        editText.setSelection(editText.text.length)
    }
}