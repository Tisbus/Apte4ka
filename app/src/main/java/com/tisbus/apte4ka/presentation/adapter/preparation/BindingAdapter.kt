package com.tisbus.apte4ka.presentation.adapter.preparation

import android.graphics.Color
import android.util.Log
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.tisbus.apte4ka.domain.entity.symptom.Symptom
import com.squareup.picasso.Picasso
import com.tisbus.apte4ka.R
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("setImagePreparation")
fun setImagePreparation(iv: ImageView, imgUrl: String?) {
/*    Picasso.get().load(imgUrl).into(iv)
    Picasso.get().load(imgUrl).rotate(90F).into(iv)*/
    Glide.with(iv.context).load(imgUrl).override(
        300,
        300
    ).error(R.drawable.ic_add_a_photo_50).into(iv)
}

@BindingAdapter("setImageDetPreparation")
fun setImageDetPreparation(iv: ImageView, imgUrl: String?) {
/*    Picasso.get().load(imgUrl).into(iv)
    Picasso.get().load(imgUrl).rotate(90F).into(iv)*/
    Glide.with(iv.context).load(imgUrl)
        .error(R.drawable.ic_add_a_photo_50).into(iv)
}


@BindingAdapter("setIconAidKit")
fun setIconAidKit(iv: ImageView, rId: String?) {
    if (rId != null) {
        Picasso.get().load(rId.toInt()).into(iv)
    }
}

@BindingAdapter("getCalendarDate")
fun getCalendarDate(calendarView: CalendarView, startDate: String?) {
    val calendar: Calendar = GregorianCalendar()
    val startDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH)
    val formattedStartDate = startDate?.let { startDateFormat.parse(it) }
    if (formattedStartDate != null) {
        calendar.time = formattedStartDate
    }
    calendarView.date = calendar.time.time
}

@BindingAdapter("makeSymptomsText")
fun makeSymptomsText(text: TextView, list: List<Symptom>?) {
    var allSymptoms = ""
    list?.forEach {
        allSymptoms += " ${it.name},"
    }
    text.text = allSymptoms.dropLast(1)
}

//for api max 26
@BindingAdapter("getCountDayToEnd")
fun getCountDayToEnd(text: TextView, endDate: String?) {
    val fmt = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    val toDay = Date()
    val eDate = endDate?.let { fmt.parse(it) }
    val milliseconds = eDate?.time?.minus(toDay.time)
    val days = (milliseconds?.div(1000) ?: throw RuntimeException("div to zero")).div(3600)
        .div(24)
    if (days.toInt() <= 30) {
        text.setTextColor(Color.RED)
    } else {
        text.setTextColor(Color.GRAY)
    }
    val countDay = "${days.toInt()} Д."
    text.text = countDay
}