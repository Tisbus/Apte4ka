package com.example.apte4ka.presentation.adapter.preparation

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
    list.forEach {
        text.text = it.name
    }
}