package com.example.apte4ka.presentation.adapter.preparation

import android.graphics.Color
import android.util.Log
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.apte4ka.domain.entity.symptom.Symptom
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("setImagePreparation")
fun setImagePreparation(iv: ImageView, imgUrl: String) {
    Picasso.get().load(imgUrl).into(iv)
}

@BindingAdapter("getCalendarDate")
fun getCalendarDate(calendarView: CalendarView, startDate: String?) {
    val calendar: Calendar = GregorianCalendar()
    val startDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH)
    val formattedStartDate: Date = startDate?.let { startDateFormat.parse(it) } as Date
    calendar.time = formattedStartDate
    calendarView.date = calendar.time.time
}

@BindingAdapter("makeSymptomsText")
fun makeSymptomsText(text: TextView, list : List<Symptom>) {
    var allSymptoms = ""
    list.forEach {
        allSymptoms += " ${it.name},"
    }
    text.text = allSymptoms.dropLast(1)
}

//for api max 26
@BindingAdapter("getCountDayToEnd")
fun getCountDayToEnd(text: TextView, endDate: String) {
    val fmt = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    val toDay = Date()
    val eDate = fmt.parse(endDate)
    val milliseconds = eDate?.time?.minus(toDay.time)
    val days = (milliseconds?.div(1000) ?: throw RuntimeException("div to zero")).div(3600)
        .div(24)
    if(days.toInt() <= 30){
        text.setTextColor(Color.RED)
    }else{
        text.setTextColor(Color.GRAY)
    }
    val countDay = "${days.toInt()} Д."
    text.text = countDay
}