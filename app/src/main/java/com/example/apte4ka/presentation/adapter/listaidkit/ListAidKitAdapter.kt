package com.example.apte4ka.presentation.adapter.listaidkit

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.apte4ka.R
import com.example.apte4ka.databinding.AidKitSetItemBinding
import com.example.apte4ka.domain.entity.aidkit.AidKit
import com.example.apte4ka.presentation.adapter.diffutil.listaidkit.ListAidKitItemDiffUtil

class ListAidKitAdapter :
    ListAdapter<AidKit, ListAidKitViewHolder>(ListAidKitItemDiffUtil()) {

    var itemSelect: ((AidKit) -> Unit)? = null
    var selectItem: Int = UNDEFINED_ITEM
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
        if(itemListAidKit.status){
            binding.cvAidKitItem.background.setTint(Color.RED)
        }
        if (selectItem == position) {
            binding.cvAidKitItem.background.setTint(Color.RED)
        } else {
            binding.cvAidKitItem.background.setTint(Color.WHITE)
        }

        binding.root.setOnClickListener {
            itemSelect?.invoke(itemListAidKit)
            selectItem = position
            notifyDataSetChanged()
        }
    }



    companion object {
        const val UNDEFINED_ITEM = -1
    }
}