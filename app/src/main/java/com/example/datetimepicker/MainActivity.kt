package com.example.datetimepicker

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.datetimepicker.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.date.setOnClickListener {
            val calendar = Calendar.getInstance()
            // Calculate the minimum and maximum selectable dates
            calendar.add(Calendar.DAY_OF_MONTH, -10)
            val minDate = calendar.time
            calendar.add(Calendar.DAY_OF_MONTH, 20)
            val maxDate = calendar.time
            calendar.add(Calendar.DAY_OF_MONTH, -10)

            // Create the DatePickerDialog with the specified date range
            DatePickerDialog(this, { _, year, month, date ->
                    calendar.set(year, month, date)
                    val selectedDate = calendar.time
                        val simpleDateFormat = SimpleDateFormat("dd / MMMM / yyyy", Locale.getDefault())
                        val formattedDate = simpleDateFormat.format(selectedDate)
                        binding.date.text = formattedDate
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).apply {
                datePicker.minDate = minDate.time
                datePicker.maxDate = maxDate.time
            }.show()
        }

        binding.time.setOnClickListener {
            TimePickerDialog(this,{_,hour,minute ->
                val calendar = Calendar.getInstance()
                calendar.set(hour,minute)
                val simpleTimeFormat = SimpleDateFormat(" h : mm  a",Locale.getDefault())
                val formattedTime = simpleTimeFormat.format(calendar.time)
                binding.time.text = formattedTime
            },
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),false)
                .show()
        }
        binding.time.setOnClickListener {
            TimePickerDialog(this, { _, hour, minute ->
                if(hour < 9 || hour > 18){
                    Toast.makeText(this@MainActivity,"Our Active Hours are from 9 AM to 6 PM", Toast.LENGTH_SHORT).show()
                }
                    else{
                    val calendar = Calendar.getInstance()
                    calendar.set(Calendar.HOUR_OF_DAY, hour)
                    calendar.set(Calendar.MINUTE, minute)
                    val simpleTimeFormat = SimpleDateFormat("hh : mm  a", Locale.getDefault()) // Or your desired locale
                    val formattedTime = simpleTimeFormat.format(calendar.time)
                    binding.time.text = formattedTime
                    }
            },
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                false)
                .show()
        }
    }
}