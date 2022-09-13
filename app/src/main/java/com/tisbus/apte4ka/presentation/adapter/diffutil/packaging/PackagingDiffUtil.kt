package com.tisbus.apte4ka.presentation.adapter.diffutil.packaging

import androidx.recyclerview.widget.DiffUtil
import com.tisbus.apte4ka.domain.entity.packaging.Packaging

class PackagingDiffUtil(
    private val oldList : List<Packaging>,
    private val newList : List<Packaging>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[oldItemPosition]
        return old.id == new.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[oldItemPosition]
        return old == new
    }
}