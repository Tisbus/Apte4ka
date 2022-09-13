package com.tisbus.apte4ka.presentation.adapter.packaging.direct

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import com.tisbus.apte4ka.R
import com.tisbus.apte4ka.databinding.PackagingItemDirectBinding
import com.tisbus.apte4ka.domain.entity.packaging.Packaging
import com.tisbus.apte4ka.presentation.adapter.diffutil.packaging.PackagingItemDiffUtil

class PackagingDirectAdapter : ListAdapter<Packaging, PackagingDirectViewHolder>(PackagingItemDiffUtil()){

    val itemSelect : ((Packaging) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackagingDirectViewHolder {
        val viewBind = DataBindingUtil.inflate<PackagingItemDirectBinding>(
            LayoutInflater.from(parent.context),
            R.layout.packaging_item_direct,
            parent,
            false
        )
        return PackagingDirectViewHolder(viewBind)
    }

    override fun onBindViewHolder(holder: PackagingDirectViewHolder, position: Int) {
        val itemPackaging = currentList[position]
        val bind = holder.bind
        bind.packagingDirect = itemPackaging
        bind.root.setOnClickListener {
            itemSelect?.invoke(itemPackaging)
        }
    }
}