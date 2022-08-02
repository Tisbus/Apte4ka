package com.example.apte4ka.presentation.adapter.diffutil.search

import androidx.recyclerview.widget.DiffUtil
import com.example.apte4ka.domain.entity.preparation.Preparation

class SearchItemDiffUtil : DiffUtil.ItemCallback<Preparation>() {
    override fun areItemsTheSame(oldItem: Preparation, newItem: Preparation): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Preparation, newItem: Preparation): Boolean {
        return oldItem == newItem
    }
}