package com.tisbus.apte4ka.presentation.adapter.aidkit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import com.tisbus.apte4ka.R
import com.tisbus.apte4ka.databinding.AidKitItemBinding
import com.tisbus.apte4ka.domain.entity.aidkit.AidKit
import com.tisbus.apte4ka.presentation.adapter.diffutil.aidkit.AidKitItemDiffUtil


class AidKitAdapter : ListAdapter<AidKit, AidKitViewHolder>(AidKitItemDiffUtil()) {

    var onItemSelect : ((AidKit) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AidKitViewHolder {
        val bind = DataBindingUtil.inflate<AidKitItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.aid_kit_item,
            parent,
            false
        )
        return AidKitViewHolder(bind)
    }

    override fun onBindViewHolder(holder: AidKitViewHolder, position: Int) {
        val itemAidKit : AidKit = getItem(position)
        val binding = holder.bind
        binding.aidKit = itemAidKit
        binding.root.setOnClickListener {
            onItemSelect?.invoke(itemAidKit)
        }
    }
}