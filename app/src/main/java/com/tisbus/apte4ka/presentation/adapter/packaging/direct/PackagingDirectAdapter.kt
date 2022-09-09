package com.tisbus.apte4ka.presentation.adapter.packaging.direct

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.tisbus.apte4ka.R
import com.tisbus.apte4ka.databinding.PackagingItemBinding
import com.tisbus.apte4ka.domain.entity.packaging.Packaging

class PackagingDirectAdapter(val listPackaging: List<Packaging>) :
    RecyclerView.Adapter<PackagingDirectViewHolder>() {

    var itemSelect: ((Packaging) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackagingDirectViewHolder {
        val bindView = DataBindingUtil.inflate<PackagingItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.packaging_item,
            parent,
            false
        )
        return PackagingDirectViewHolder(bindView)
    }

    override fun onBindViewHolder(holder: PackagingDirectViewHolder, position: Int) {
        val itemPackaging = listPackaging[position]
        val bind = holder.bind
        bind.packaging = itemPackaging
        if (itemPackaging.status) {
            bind.cvPackaging.background.setTint(Color.RED)
            itemPackaging.status = false
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