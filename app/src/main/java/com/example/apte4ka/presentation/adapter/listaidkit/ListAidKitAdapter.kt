package com.example.apte4ka.presentation.adapter.listaidkit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.apte4ka.R
import com.example.apte4ka.databinding.AidKitSetItemBinding
import com.example.apte4ka.domain.entity.aidkit.AidKit
import com.example.apte4ka.presentation.adapter.diffutil.listaidkit.ListAidKitItemDiffUtil

class ListAidKitAdapter :
    ListAdapter<AidKit, ListAidKitViewHolder>(ListAidKitItemDiffUtil()){

    var itemSelect : ((AidKit) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAidKitViewHolder {
        val bind = DataBindingUtil.inflate<AidKitSetItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.aid_kit_set_item,
            parent,
            false
        )
        return ListAidKitViewHolder(bind)
    }

    override fun onBindViewHolder(holder: ListAidKitViewHolder, position: Int) {
        val itemListAidKit = getItem(position)
        val binding = holder.binding
        binding.listAidKit = itemListAidKit
        binding.root.setOnClickListener {
            itemSelect?.invoke(itemListAidKit)
        }
    }
}