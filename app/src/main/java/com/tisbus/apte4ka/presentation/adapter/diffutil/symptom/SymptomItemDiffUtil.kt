package com.tisbus.apte4ka.presentation.adapter.diffutil.symptom

import androidx.recyclerview.widget.DiffUtil
import com.tisbus.apte4ka.domain.entity.symptom.Symptom

class SymptomItemDiffUtil : DiffUtil.ItemCallback<Symptom>() {
    override fun areItemsTheSame(oldItem: Symptom, newItem: Symptom): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Symptom, newItem: Symptom): Boolean {
        return oldItem == newItem
    }
}