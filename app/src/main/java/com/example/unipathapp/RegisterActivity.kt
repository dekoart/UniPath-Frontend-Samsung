package com.example.unipathapp

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import com.example.unipathapp.api.RetrofitClient
import com.example.unipathapp.models.RegisterRequest

class RegisterActivity : AppCompatActivity() {
    private lateinit var emailEdit: EditText
    private lateinit var passwordEdit: EditText
    private lateinit var confirmPasswordEdit: EditText
    private lateinit var registerButton: AppCompatButton
    private lateinit var loginText: TextView
    private lateinit var showPassword: ImageView
    private lateinit var showConfirmPassword: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        emailEdit = findViewById(R.id.emailEdit)
        passwordEdit = findViewById(R.id.passwordEdit)
        confirmPasswordEdit = findViewById(R.id.confirmPasswordEdit)
        registerButton = findViewById(R.id.registerButton)
        loginText = findViewById(R.id.loginText)
        showPassword = findViewById(R.id.showPassword)
        showConfirmPassword = findViewById(R.id.showConfirmPassword)
        showPassword.setOnClickListener { togglePasswordVisibility(passwordEdit) }
        showConfirmPassword.setOnClickListener { togglePasswordVisibility(confirmPasswordEdit) }
        loginText.setOnClickListener { finish() }
        registerButton.setOnClickListener {
            val email = emailEdit.text.toString().trim()
            val password = passwordEdit.text.toString()
            val confirm = confirmPasswordEdit.text.toString()
            if (email.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                Toast.makeText(
                    this, "Заполните все поля",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if (password != confirm) {
                Toast.makeText(
                    this,
                    "Пароли не совпадают",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if (password.length < 6) {
                Toast.makeText(
                    this,
                    "Пароль должен содержать минимум 6 символов",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    val response = RetrofitClient.instance.register(
                        RegisterRequest(email, password)
                    )
                    if (response.isSuccessful) {
                        val token = response.body() ?: ""
                        val prefs = getSharedPreferences("app_prefs", MODE_PRIVATE)
                        prefs.edit().putString("auth_token", token).apply()
                        Toast.makeText(
                            this@RegisterActivity,
                            "Регистрация успешна!",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        val errorMessage = response.body() ?: "Неизвестная ошибка"
                        Toast.makeText(this@RegisterActivity, errorMessage, Toast.LENGTH_SHORT)
                            .show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Нет связи с сервером",
                        Toast.LENGTH_SHORT
                    ).show()
                    e.printStackTrace()
                }
            }
        }
    }

    private fun togglePasswordVisibility(editText: EditText) {
        val isPasswordVisible = editText.inputType ==
                (android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
        editText.inputType = if (isPasswordVisible)
            android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
        else
            android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        editText.setSelection(editText.text.length)
    }
}