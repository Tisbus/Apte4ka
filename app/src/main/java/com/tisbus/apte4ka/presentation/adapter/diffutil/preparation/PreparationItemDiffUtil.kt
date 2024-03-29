package com.tisbus.apte4ka.presentation.adapter.diffutil.preparation

import androidx.recyclerview.widget.DiffUtil
import com.tisbus.apte4ka.domain.entity.preparation.Preparation

class PreparationItemDiffUtil : DiffUtil.ItemCallback<Preparation>() {
    override fun areItemsTheSame(oldItem: Preparation, newItem: Preparation): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Preparation, newItem: Preparation): Boolean {
        return oldItem == newItem
    }
}