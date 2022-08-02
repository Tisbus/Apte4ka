package com.example.apte4ka.presentation.adapter.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.apte4ka.R
import com.example.apte4ka.databinding.SearchItemBinding
import com.example.apte4ka.domain.entity.preparation.Preparation

class SearchAdapter(private val listPrep : MutableList<Preparation>) :
    RecyclerView.Adapter<SearchViewHolder>(), Filterable{

    var itemSelect : ((Preparation) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = DataBindingUtil.inflate<SearchItemBinding>(
            LayoutInflater.from(parent.context), R.layout.search_item, parent, false
        )
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val itemSearch = listPrep[position]
        val bind = holder.bind
        bind.preparation = itemSearch
        bind.root.setOnClickListener {
            itemSelect?.invoke(itemSearch)
        }
    }

    override fun getItemCount(): Int {
        return listPrep.size
    }

    override fun getFilter(): Filter {
        TODO("Not yet implemented")
    }
}