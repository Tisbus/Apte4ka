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

class SearchAdapter(private var listPrep: MutableList<Preparation>) :
    RecyclerView.Adapter<SearchViewHolder>(), Filterable {

    var itemSelect: ((Preparation) -> Unit)? = null
    var displayList : MutableList<Preparation> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = DataBindingUtil.inflate<SearchItemBinding>(
            LayoutInflater.from(parent.context), R.layout.search_item, parent, false
        )
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val itemSearch = displayList[position]
        val bind = holder.bind
        bind.preparation = itemSearch
        bind.root.setOnClickListener {
            itemSelect?.invoke(itemSearch)
        }
    }

    init {
        displayList.addAll(listPrep)
    }

    override fun getItemCount(): Int {
        return displayList.size
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val listFilter : MutableList<Preparation> = mutableListOf()
                if(p0==null||p0.isEmpty())
                {
                    listFilter.addAll(listPrep)
                }else
                {
                    val filterPattern: String = p0.toString().lowercase().trim()
                    for (item in listPrep) {
                        if (item.name.lowercase().contains(filterPattern)) {
                            listFilter.add(item)
                        }
                    }
                }
                val results = Filter.FilterResults()
                results.values = listFilter
                return  results
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                displayList.clear()
                displayList.addAll(p1?.values as MutableList<Preparation>)
                notifyDataSetChanged()
            }
        }
    }
}