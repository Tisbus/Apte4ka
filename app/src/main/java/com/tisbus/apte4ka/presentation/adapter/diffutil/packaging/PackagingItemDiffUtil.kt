package com.tisbus.apte4ka.presentation.adapter.diffutil.packaging

import androidx.recyclerview.widget.DiffUtil
import com.tisbus.apte4ka.domain.entity.packaging.Packaging

class PackagingItemDiffUtil : DiffUtil.ItemCallback<Packaging>()
{
    override fun areItemsTheSame(oldItem: Packaging, newItem: Packaging): Boolean {
        return  oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Packaging, newItem: Packaging): Boolean {
        return oldItem == newItem
    }
}