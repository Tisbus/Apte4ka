package com.tisbus.apte4ka.presentation.adapter.preparation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import com.tisbus.apte4ka.R
import com.tisbus.apte4ka.databinding.PreparationItemBinding
import com.tisbus.apte4ka.domain.entity.preparation.Preparation
import com.tisbus.apte4ka.presentation.adapter.diffutil.preparation.PreparationItemDiffUtil

class PreparationAdapter :
    ListAdapter<Preparation, PreparationViewHolder>(PreparationItemDiffUtil()) {

    var itemSelect : ((Preparation) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreparationViewHolder {
        val binding =
            DataBindingUtil.inflate<PreparationItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.preparation_item,
                parent,
                false
            )
        return PreparationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PreparationViewHolder, position: Int) {
        val itemPreparation = getItem(position)
        val bind = holder.bind
        bind.preparation = itemPreparation
        bind.root.setOnClickListener {
            itemSelect?.invoke(itemPreparation)
        }
    }
}