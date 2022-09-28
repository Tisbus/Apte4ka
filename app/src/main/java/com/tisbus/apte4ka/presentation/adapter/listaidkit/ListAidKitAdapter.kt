package com.tisbus.apte4ka.presentation.adapter.listaidkit

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.tisbus.apte4ka.R
import com.tisbus.apte4ka.databinding.AidKitSetItemBinding
import com.tisbus.apte4ka.domain.entity.aidkit.AidKit

class ListAidKitAdapter(private val listAidKit : MutableList<AidKit>) : RecyclerView.Adapter<ListAidKitViewHolder>(){

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
        val itemListAidKit = listAidKit[position]
        val binding = holder.binding
        binding.listAidKit = itemListAidKit
        if (selectItem == position) {
            binding.tvNamAidKitInList.setBackgroundResource(R.color.green)
            binding.tvNamAidKitInList.setTextColor(Color.WHITE)
        } else {
            binding.tvNamAidKitInList.setBackgroundResource(R.drawable.aid_kit_shape)
            binding.tvNamAidKitInList.setTextColor(ContextCompat.getColor(
                binding.root.context,
                R.color.light_black
            ))
        }
        if(itemListAidKit.status){
            binding.tvNamAidKitInList.setBackgroundResource(R.color.green)
            binding.tvNamAidKitInList.setTextColor(Color.WHITE)
            itemListAidKit.status = false
        }
        binding.root.setOnClickListener {
            itemSelect?.invoke(itemListAidKit)
            selectItem = position
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return listAidKit.size
    }

    companion object {
        const val UNDEFINED_ITEM = -1
    }
}