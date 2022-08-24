package com.example.apte4ka.data.converter

import androidx.room.TypeConverter
import com.example.apte4ka.domain.entity.symptom.Symptom
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {
    private val gson = Gson()

    @TypeConverter
    fun subjectSymptomToJson(subject: List<Symptom>?): String? {
        return gson.toJson(subject)?.let {
            return it
        }
    }

    @TypeConverter
    fun jsonSymptomToSubject(json: String?): List<Symptom> {
        return if (json.isNullOrEmpty()) {
            listOf()
        } else {
            val type = object : TypeToken<List<Symptom>>() {}.type
            gson.fromJson(json, type)
        }
    }
}