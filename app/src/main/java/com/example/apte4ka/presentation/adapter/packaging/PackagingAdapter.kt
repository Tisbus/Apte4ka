package com.example.apte4ka.presentation.adapter.packaging

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.apte4ka.R
import com.example.apte4ka.databinding.PackagingItemBinding
import com.example.apte4ka.domain.entity.packaging.Packaging

class PackagingAdapter(val listPackaging: List<Packaging>) :
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
            bind.cvPackaging.background.setTint(Color.RED)
        } else {
            bind.cvPackaging.background.setTint(Color.WHITE)
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