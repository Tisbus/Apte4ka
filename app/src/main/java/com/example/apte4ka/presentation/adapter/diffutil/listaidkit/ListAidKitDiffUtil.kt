package com.example.apte4ka.presentation.adapter.diffutil.listaidkit

import androidx.recyclerview.widget.DiffUtil
import com.example.apte4ka.domain.entity.aidkit.AidKit

class ListAidKitDiffUtil(
    private val newList : List<AidKit>,
    private val oldList : List<AidKit>
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