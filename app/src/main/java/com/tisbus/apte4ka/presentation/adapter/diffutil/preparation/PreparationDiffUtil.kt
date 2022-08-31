package com.tisbus.apte4ka.presentation.adapter.diffutil.preparation

import androidx.recyclerview.widget.DiffUtil
import com.tisbus.apte4ka.domain.entity.preparation.Preparation

class PreparationDiffUtil(
    private val newList : List<Preparation>,
    private val oldList : List<Preparation>
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