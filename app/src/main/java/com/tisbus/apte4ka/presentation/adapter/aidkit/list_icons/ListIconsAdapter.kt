package com.tisbus.apte4ka.presentation.adapter.aidkit.list_icons

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tisbus.apte4ka.R
import com.tisbus.apte4ka.databinding.ItemImageAidBinding

class ListIconsAdapter : RecyclerView.Adapter<ListIconsViewHolder>() {

    private val listIcons = listOf(
        R.drawable.aid_kit_in_list,
        R.drawable.aid_kit_in_list3,
        R.drawable.aid_kit_in_list4,
        R.drawable.aid_kit_in_list5,
        R.drawable.aid_kit_in_list6,
        R.drawable.aid_kit_in_list7,
        R.drawable.aid_kit_in_list8,
        R.drawable.aid_kit_in_list9,
        R.drawable.aid_kit_in_list10,
        R.drawable.aid_kit_in_list11
    )

    var itemSelect: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListIconsViewHolder {
        val viewBind = DataBindingUtil.inflate<ItemImageAidBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_image_aid,
            parent,
            false
        )
        return ListIconsViewHolder(viewBind)
    }

    override fun onBindViewHolder(holder: ListIconsViewHolder, position: Int) {
        val item = listIcons[position]
        val bind = holder.bind
        Picasso.get().load(item).into(bind.ivItemAidKit)
        bind.root.setOnClickListener {
            itemSelect?.invoke(item)
        }
    }

    override fun getItemCount(): Int {
        return listIcons.size
    }
}