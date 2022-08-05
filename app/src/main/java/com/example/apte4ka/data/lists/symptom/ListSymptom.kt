package com.example.apte4ka.data.lists.symptom

import com.example.apte4ka.domain.entity.symptom.Symptom

class ListSymptom {
    val listSymptoms : List<Symptom> = arrayListOf(
        Symptom("Головная боль", "", false),
        Symptom("Грудная боль", "", false),
        Symptom("Спина боль", "", false),
        Symptom("Ножная боль", "", false),
        Symptom("Ручная боль", "", false),
        Symptom("Горловая боль", "", false),
        Symptom("Сердечная боль", "", false)
    )
}