package com.example.unipathapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.unipathapp.models.ProgramResponse

class UniversityDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_university_detail)
        val program = intent.getSerializableExtra("UNIVERSITY") as? ProgramResponse ?: return
        val ivPhoto: ImageView = findViewById(R.id.universityPhoto)
        val tvName: TextView = findViewById(R.id.universityName)
        val tvBudgetScore: TextView = findViewById(R.id.budgetScore)
        val tvPaidScore: TextView = findViewById(R.id.paidScore)
        val tvPrice: TextView = findViewById(R.id.price)
        val tvBudgetFullTime: TextView = findViewById(R.id.budgetFullTime)
        val tvBudgetPartTime: TextView = findViewById(R.id.budgetPartTime)
        val tvBudgetMixed: TextView = findViewById(R.id.budgetMixed)
        val tvPaidFullTime: TextView = findViewById(R.id.paidFullTime)
        val tvPaidPartTime: TextView = findViewById(R.id.paidPartTime)
        val tvPaidMixed: TextView = findViewById(R.id.paidMixed)

        tvName.text = program.universityName
        // загрузка фото
        if (!program.logoUrl.isNullOrEmpty()) {
            Glide.with(this)
                .load(program.logoUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .centerCrop()
                .into(ivPhoto)
        }

        tvBudgetScore.text = "от ${program.budgetScore ?: 130} баллов"
        tvPaidScore.text = "от ${program.paidScore ?: 180} баллов"
        tvPrice.text = "от ${program.price ?: 70000}₽"

        val budget = program.budget ?: 0
        tvBudgetFullTime.text = "$budget очно"
        tvBudgetPartTime.text = "$budget заочно"
        tvBudgetMixed.text = "$budget очно-заочно"

        val paid = program.paid ?: 0
        tvPaidFullTime.text = "$paid очно"
        tvPaidPartTime.text = "$paid заочно"
        tvPaidMixed.text = "$paid очно-заочно"
    }
}