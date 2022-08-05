package com.example.apte4ka.data.lists.symptom

import com.example.apte4ka.domain.entity.symptom.Symptom

class ListSymptom {
    val listSymptoms : List<Symptom> = arrayListOf(
        Symptom(1,"Головная боль", "", false),
        Symptom(2,"Грудная боль", "", false),
        Symptom(3,"Спина боль", "", false),
        Symptom(4,"Ножная боль", "", false),
        Symptom(5,"Ручная боль", "", false),
        Symptom(6,"Горловая боль", "", false),
        Symptom(7,"Сердечная боль", "", false)
    )
}