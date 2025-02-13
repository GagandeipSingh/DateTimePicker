package com.example.datetimepicker

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.datetimepicker.databinding.ActivityMainBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.CompositeDateValidator
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat.CLOCK_12H
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.date.setOnClickListener {
//            val calendar = Calendar.getInstance()
//            // Calculate the minimum and maximum selectable dates
//            calendar.add(Calendar.DAY_OF_MONTH, -10)
//            val minDate = calendar.time
//            calendar.add(Calendar.DAY_OF_MONTH, 20)
//            val maxDate = calendar.time
//            calendar.add(Calendar.DAY_OF_MONTH, -10)
//
//            // Create the DatePickerDialog with the specified date range
//            DatePickerDialog(this, { _, year, month, date ->
//                    calendar.set(year, month, date)
//                    val selectedDate = calendar.time
//                        val simpleDateFormat = SimpleDateFormat("dd / MMMM / yyyy", Locale.getDefault())
//                        val formattedDate = simpleDateFormat.format(selectedDate)
//                        binding.date.text = formattedDate
//                },
//                calendar.get(Calendar.YEAR),
//                calendar.get(Calendar.MONTH),
//                calendar.get(Calendar.DAY_OF_MONTH)
//            ).apply {
//                datePicker.minDate = minDate.time
//                datePicker.maxDate = maxDate.time
//            }.show()
            val simpleDateFormat = SimpleDateFormat("dd / MMMM / yyyy", Locale.getDefault())
            val calConstraintsBuilder = CalendarConstraints.Builder()
            val minDate = simpleDateFormat.parse("12 / December / 2024")
            val maxDate = simpleDateFormat.parse("12 / March / 2025")
            calConstraintsBuilder.setStart(minDate!!.time)
            calConstraintsBuilder.setEnd(maxDate!!.time)
            val validator = CompositeDateValidator.allOf(
                listOf(DateValidatorPointForward.from(minDate.time),DateValidatorPointBackward.before(maxDate.time))
            )
            calConstraintsBuilder.setValidator(validator)
            val calConstraints = calConstraintsBuilder.build()
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setCalendarConstraints(calConstraints)
                .build()
            datePicker.show(supportFragmentManager,null)
            datePicker.addOnPositiveButtonClickListener { selected->
                binding.date.text = simpleDateFormat.format(Date(selected))
            }

        }

        binding.time.setOnClickListener {
//            TimePickerDialog(this, { _, hour, minute ->
//                if((hour == 18 && minute > 0) || (hour < 9 || hour > 18)){
//                    Toast.makeText(this@MainActivity,"Our Active Hours are from 9 AM to 6 PM", Toast.LENGTH_SHORT).show()
//                }
//                    else{
//                    val calendar = Calendar.getInstance()
//                    calendar.set(Calendar.HOUR_OF_DAY, hour)
//                    calendar.set(Calendar.MINUTE, minute)
//                    val simpleTimeFormat = SimpleDateFormat("hh : mm  a", Locale.getDefault()) // Or your desired locale
//                    val formattedTime = simpleTimeFormat.format(calendar.time)
//                    binding.time.text = formattedTime
//                    }
//            },
//                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
//                Calendar.getInstance().get(Calendar.MINUTE),
//                false)
//                .show()


            val simpleTimeFormat = SimpleDateFormat("hh : mm  a", Locale.getDefault()) // Or your desired locale

            val cal = Calendar.getInstance()
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(CLOCK_12H)
                .setHour(cal.get(Calendar.HOUR_OF_DAY))
                .setMinute(cal.get(Calendar.MINUTE))
                .build()
            timePicker.show(supportFragmentManager,null)
            timePicker.addOnPositiveButtonClickListener {
                if((timePicker.hour == 18 && timePicker.minute > 0) || (timePicker.hour < 9 || timePicker.hour > 18)){
                    Toast.makeText(this@MainActivity,"Our Active Hours are from 9 AM to 6 PM", Toast.LENGTH_SHORT).show()
                } else{
                    val calSelected = Calendar.getInstance()
                    calSelected.set(Calendar.HOUR_OF_DAY,timePicker.hour)
                    calSelected.set(Calendar.MINUTE,timePicker.minute)
                    binding.time.text = simpleTimeFormat.format(calSelected.time)
                }
            }
        }
    }
}