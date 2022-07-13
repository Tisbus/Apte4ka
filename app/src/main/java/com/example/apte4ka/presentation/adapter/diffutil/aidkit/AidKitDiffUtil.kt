package com.example.apte4ka.presentation.adapter.diffutil.aidkit

import androidx.recyclerview.widget.DiffUtil
import com.example.apte4ka.domain.entity.aidkit.AidKit

class AidKitDiffUtil(
    private val oldAidKitList : List<AidKit>,
    private val newAidKitList : List<AidKit>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldAidKitList.size
    }

    override fun getNewListSize(): Int {
        return newAidKitList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldAidKitList[oldItemPosition]
        val newItem = newAidKitList[newItemPosition]
        return newItem.id == oldItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldAidKitList[oldItemPosition]
        val newItem = newAidKitList[newItemPosition]
        return newItem == oldItem
    }
}
