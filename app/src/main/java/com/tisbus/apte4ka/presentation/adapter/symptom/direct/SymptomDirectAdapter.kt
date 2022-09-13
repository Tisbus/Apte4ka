package com.tisbus.apte4ka.presentation.adapter.symptom.direct

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import com.tisbus.apte4ka.R
import com.tisbus.apte4ka.databinding.SymptomItemDirectBinding
import com.tisbus.apte4ka.domain.entity.symptom.Symptom
import com.tisbus.apte4ka.presentation.adapter.diffutil.symptom.SymptomItemDiffUtil

class SymptomDirectAdapter : ListAdapter<Symptom, SymptomDirectViewHolder>(SymptomItemDiffUtil()) {

    var itemSelect: ((Symptom) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SymptomDirectViewHolder {
        val viewBind = DataBindingUtil.inflate<SymptomItemDirectBinding>(
            LayoutInflater.from(parent.context),
            R.layout.symptom_item_direct,
            parent,
            false
        )
        return SymptomDirectViewHolder(viewBind)
    }

    override fun onBindViewHolder(holder: SymptomDirectViewHolder, position: Int) {
        val itemSymptom = currentList[position]
        val bind = holder.bind
        bind.symptomDirect = itemSymptom
        bind.root.setOnClickListener {
            itemSelect?.invoke(itemSymptom)
        }
    }
}