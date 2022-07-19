package com.example.apte4ka.presentation.adapter.diffutil.listaidkit

import androidx.recyclerview.widget.DiffUtil
import com.example.apte4ka.domain.entity.aidkit.AidKit

class ListAidKitItemDiffUtil : DiffUtil.ItemCallback<AidKit>() {
    override fun areItemsTheSame(oldItem: AidKit, newItem: AidKit): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AidKit, newItem: AidKit): Boolean {
        return oldItem == newItem
    }
}