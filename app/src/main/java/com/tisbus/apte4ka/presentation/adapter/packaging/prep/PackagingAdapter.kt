package com.tisbus.apte4ka.presentation.adapter.packaging.prep

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.tisbus.apte4ka.R
import com.tisbus.apte4ka.databinding.PackagingItemBinding
import com.tisbus.apte4ka.domain.entity.packaging.Packaging

class PackagingAdapter(val listPackaging: MutableList<Packaging>) :
    RecyclerView.Adapter<PackagingViewHolder>() {

    var itemSelect: ((Packaging) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackagingViewHolder {
        val bindView = DataBindingUtil.inflate<PackagingItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.packaging_item,
            parent,
            false
        )
        return PackagingViewHolder(bindView)
    }

    override fun onBindViewHolder(holder: PackagingViewHolder, position: Int) {
        val itemPackaging = listPackaging[position]
        val bind = holder.bind
        bind.packaging = itemPackaging
        if (itemPackaging.status) {
            bind.tvPackagingItem.setBackgroundResource(R.color.teal_700)
            bind.tvPackagingItem.setTextColor(Color.WHITE)
            itemPackaging.status = false
        } else {
            bind.tvPackagingItem.setBackgroundResource(R.drawable.prep_packaging_shape)
            bind.tvPackagingItem.setTextColor(ContextCompat.getColor(
                bind.root.context,
                R.color.light_black
            ))
        }
        bind.root.setOnClickListener {
            itemSelect?.invoke(itemPackaging)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return listPackaging.size
    }
}