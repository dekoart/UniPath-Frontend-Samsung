package com.example.unipathapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.unipathapp.models.UniversityResponse

class UniversityDetailActivity : AppCompatActivity() {

    private lateinit var university: UniversityResponse

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_university_detail)

        university =
            intent.getSerializableExtra("UNIVERSITY") as? UniversityResponse ?: run {

                Toast.makeText(
                    this,
                    "Ошибка загрузки",
                    Toast.LENGTH_SHORT
                ).show()

                finish()

                return
            }

        val photo = findViewById<ImageView>(R.id.photo)

        val name = findViewById<TextView>(R.id.name)

        val budgetScore = findViewById<TextView>(R.id.budget_score)

        val paidScore = findViewById<TextView>(R.id.paid_score)

        val price = findViewById<TextView>(R.id.price)

        val budgetFull = findViewById<TextView>(R.id.budget_full)

        val budgetPart = findViewById<TextView>(R.id.budget_part)
        val budgetMixed = findViewById<TextView>(R.id.budget_mixed)

        val paidFull = findViewById<TextView>(R.id.paid_full)

        val paidPart = findViewById<TextView>(R.id.paid_part)

        val paidMixed = findViewById<TextView>(R.id.paid_mixed)

        val back = findViewById<ImageView>(R.id.btnBackArrow)

        val btnFavoriteUni = findViewById<ImageView>(R.id.btnFavoriteUni)

        val notification = findViewById<ImageView>(R.id.notification)

        name.text = university.name

        if (!university.logo.isNullOrEmpty()) {

            Glide.with(this)
                .load(university.logo)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .centerCrop()
                .into(photo)
        }

        budgetScore.text = university.minBudgetScore?.let {
            "от $it баллов" }
                ?: "Информация уточняется"

        paidScore.text = "от ${university.minBudgetScore?.let { it - 50 } ?: 130} баллов"

        price.text = "от 70 000₽"

        val budgetCount = university.programsCount ?: 0

        budgetFull.text = "$budgetCount очно"

        budgetPart.text = "${budgetCount / 2} заочно"

        budgetMixed.text = "${budgetCount / 2} очно-заочно"

        val paidCount = budgetCount * 3

        paidFull.text = "$paidCount очно"

        paidPart.text = "${paidCount / 2} заочно"

        paidMixed.text = "${paidCount / 2} очно-заочно"


        val program = Program(title = university.name ?: "Без названия",

            university = university.name ?: "Неизвестный университет",

            city = university.city ?: "Неизвестный город"
        )

        btnFavoriteUni.setOnClickListener {

            FavoritesManager.favoritesList.add(program)

            btnFavoriteUni.setImageResource(R.drawable.favoriteuni)

            Toast.makeText(
                this,
                "Добавлено в избранное",
                Toast.LENGTH_SHORT
            ).show()
        }

        notification.setOnClickListener {

            Toast.makeText(
                this,
                "Уведомления",
                Toast.LENGTH_SHORT
            ).show()
        }

        back.setOnClickListener {

            finish()
        }

        findViewById<LinearLayout>(R.id.btnMain)
            .setOnClickListener {

                startActivity(
                    Intent(this, MainActivity::class.java)
                )

                finish()
            }



        findViewById<LinearLayout>(R.id.btnFavorite).setOnClickListener {
                startActivity(Intent(this, FavoriteActivity::class.java))
            }



        findViewById<LinearLayout>(R.id.btnProfile)
            .setOnClickListener {

                startActivity(
                    Intent(this, ProfileActivity::class.java)
                )
            }


        findViewById<LinearLayout>(R.id.btnBackArrow)
            .setOnClickListener {

                startActivity(
                    Intent(this, ResultActivity::class.java)
                )
            }
    }
}