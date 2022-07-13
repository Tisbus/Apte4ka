package com.example.apte4ka.presentation.adapter.diffutil.aidkit

import androidx.recyclerview.widget.DiffUtil
import com.example.apte4ka.domain.entity.aidkit.AidKit

class AidKitItemDiffUtil : DiffUtil.ItemCallback<AidKit>() {
    override fun areItemsTheSame(oldItem: AidKit, newItem: AidKit): Boolean {
        return newItem.id == oldItem.id
    }

    override fun areContentsTheSame(oldItem: AidKit, newItem: AidKit): Boolean {
        return newItem == oldItem
    }
}