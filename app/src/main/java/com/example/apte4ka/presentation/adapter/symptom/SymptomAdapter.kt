package com.example.apte4ka.presentation.adapter.symptom

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.apte4ka.R
import com.example.apte4ka.databinding.SymptomItemBinding
import com.example.apte4ka.domain.entity.symptom.Symptom

class SymptomAdapter(val listSymptom : List<Symptom>) : RecyclerView.Adapter<SymptomViewHolder>() {

    var itemSelect : ((Symptom) -> Unit)? = null

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
        bind.root.setOnClickListener {
            itemSelect?.invoke(itemSymptom)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return listSymptom.size
    }
}