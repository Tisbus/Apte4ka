package com.tisbus.apte4ka.presentation.adapter.preparation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import com.tisbus.apte4ka.R
import com.tisbus.apte4ka.databinding.PreparationItemBinding
import com.tisbus.apte4ka.domain.entity.preparation.Preparation
import com.tisbus.apte4ka.presentation.adapter.diffutil.preparation.PreparationItemDiffUtil
import java.text.SimpleDateFormat
import java.util.*

class PreparationAdapter :
    ListAdapter<Preparation, PreparationViewHolder>(PreparationItemDiffUtil()) {

    var itemSelect: ((Preparation) -> Unit)? = null
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
        if (getCountDayToEnd(itemPreparation.dateExp)) {
            bind.svPrep.strokeColor = ContextCompat.getColor(
                bind.root.context,
                R.color.red
            )
            bind.svPrep.strokeWidth = 2
        }
    }

    private fun getCountDayToEnd(endDate: String?): Boolean {
        val fmt = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val toDay = Date()
        val eDate = endDate?.let { fmt.parse(it) }
        val milliseconds = eDate?.time?.minus(toDay.time)
        val days = (milliseconds?.div(1000) ?: throw RuntimeException("div to zero")).div(3600)
            .div(24)
        return days.toInt() <= 30
    }
}