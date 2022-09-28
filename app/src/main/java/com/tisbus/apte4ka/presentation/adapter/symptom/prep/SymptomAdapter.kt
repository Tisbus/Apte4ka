package com.tisbus.apte4ka.presentation.adapter.symptom.prep

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.tisbus.apte4ka.R
import com.tisbus.apte4ka.databinding.SymptomItemBinding
import com.tisbus.apte4ka.domain.entity.symptom.Symptom

class SymptomAdapter(val listSymptom: MutableList<Symptom>) : RecyclerView.Adapter<SymptomViewHolder>() {

    var itemSelect: ((Symptom) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SymptomViewHolder {
        val bindView = DataBindingUtil.inflate<SymptomItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.symptom_item,
            parent,
            false
        )
        return SymptomViewHolder(bindView)
    }

    override fun onBindViewHolder(holder: SymptomViewHolder, position: Int) {
        val itemSymptom = listSymptom[position]
        val bind = holder.bind
        bind.symptom = itemSymptom
        if (itemSymptom.status) {
            bind.tvSymptomsItem.setBackgroundResource(R.color.purple_200)
            bind.tvSymptomsItem.setTextColor(Color.WHITE)
        } else {
            bind.tvSymptomsItem.setBackgroundResource(R.drawable.prep_symptom_shape)
            bind.tvSymptomsItem.setTextColor(ContextCompat.getColor(
                bind.root.context,
                R.color.light_black
            ))
        }
        bind.root.setOnClickListener {
            itemSelect?.invoke(itemSymptom)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return listSymptom.size
    }
}