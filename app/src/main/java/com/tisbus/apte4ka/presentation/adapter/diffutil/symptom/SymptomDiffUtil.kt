package com.tisbus.apte4ka.presentation.adapter.diffutil.symptom

import androidx.recyclerview.widget.DiffUtil
import com.tisbus.apte4ka.domain.entity.symptom.Symptom

class SymptomDiffUtil(
    private val oldList : List<Symptom>,
    private val newList : List<Symptom>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]
        return old.id == new.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]
        return old == new
    }
}