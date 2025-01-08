package com.example.miprimerproyecto_adriansaavedra

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import com.example.miprimerproyecto_adriansaavedra.databinding.ActivityMainBinding
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


            binding.btnDatePicker?.setOnClickListener {
                showDatePickerDialog()
            }






    }
    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = formatDateText(selectedDay, selectedMonth, selectedYear)
                binding.tvSelectedDate?.text = formattedDate
                binding.btnDatePicker?.text = formattedDate
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    private fun formatDateText(day: Int, month: Int, year: Int): String {
        val calendar = Calendar.getInstance()
        calendar[Calendar.YEAR] = year
        calendar[Calendar.MONTH] = month
        calendar[Calendar.DAY_OF_MONTH] = day

        val currentDate = Calendar.getInstance()

        return when {
            calendar[Calendar.YEAR] == currentDate[Calendar.YEAR] &&
                    calendar[Calendar.DAY_OF_YEAR] == currentDate[Calendar.DAY_OF_YEAR] -> "Hoy"
            calendar[Calendar.YEAR] == currentDate[Calendar.YEAR] &&
                    calendar[Calendar.DAY_OF_YEAR] == currentDate[Calendar.DAY_OF_YEAR] + 1 -> "Mañana"
            calendar[Calendar.YEAR] == currentDate[Calendar.YEAR] &&
                    calendar[Calendar.DAY_OF_YEAR] == currentDate[Calendar.DAY_OF_YEAR] - 1 -> "Ayer"
            else -> "$day/${month + 1}/$year"
        }
    }


}
