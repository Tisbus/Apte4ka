package com.example.apte4ka.presentation.adapter.diffutil.search

import androidx.recyclerview.widget.DiffUtil
import com.example.apte4ka.domain.entity.preparation.Preparation

class SearchDiffUtil(
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
        val new = newList[newItemPosition]
        val old = oldList[oldItemPosition]
        return new.id == old.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val new = newList[newItemPosition]
        val old = oldList[oldItemPosition]
        return new == old
    }
}