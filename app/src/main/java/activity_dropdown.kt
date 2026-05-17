package com.example.unipathapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity

class FiltersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_dropdown)

        val cities = arrayOf(
            "Москва",
            "Санкт-Петербург",
            "Казань",
            "Новосибирск"
        )

        val directions = arrayOf(
            "IT и программирование",
            "Экономика",
            "Дизайн",
            "Юриспруденция"
        )

        val educationForms = arrayOf(
            "Очная",
            "Заочная",
            "Очно-заочная"
        )

        val universityTypes = arrayOf(
            "Государственный",
            "Частный"
        )

        val cityDropdown =
            findViewById<AutoCompleteTextView>(R.id.cityDropdown)

        val directionDropdown =
            findViewById<AutoCompleteTextView>(R.id.directionDropdown)

        val educationDropdown =
            findViewById<AutoCompleteTextView>(R.id.educationDropdown)

        val universityDropdown =
            findViewById<AutoCompleteTextView>(R.id.universityDropdown)

        cityDropdown.setAdapter(
            ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                cities
            )
        )

        directionDropdown.setAdapter(
            ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                directions
            )
        )

        educationDropdown.setAdapter(
            ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                educationForms
            )
        )

        universityDropdown.setAdapter(
            ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                universityTypes
            )
        )
    }
}
