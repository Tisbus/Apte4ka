package com.example.apte4ka.presentation.adapter.preparation

import android.widget.CalendarView
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("setImagePreparation")
fun setImagePreparation(iv: ImageView, imgUrl: String) {
    Picasso.get().load(imgUrl).rotate(90f).into(iv)
}

@BindingAdapter("getCalendarDate")
fun getCalendarDate(calendarView: CalendarView, startDate: String?) {
    val calendar: Calendar = GregorianCalendar()
    val startDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH)
    val formattedStartDate: Date = startDate?.let { startDateFormat.parse(it) } as Date
    calendar.time = formattedStartDate
    calendarView.date = calendar.time.time
}