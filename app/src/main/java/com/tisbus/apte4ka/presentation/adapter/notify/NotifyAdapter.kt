package com.tisbus.apte4ka.presentation.adapter.notify

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.tisbus.apte4ka.R
import com.tisbus.apte4ka.databinding.NotifyItemBinding
import com.tisbus.apte4ka.domain.entity.notify.Notify

class NotifyAdapter(val listNotify : MutableList<Notify>) : RecyclerView.Adapter<NotifyViewHolder>() {

    var itemSelect : ((Notify) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotifyViewHolder {
        val viewBind = DataBindingUtil.inflate<NotifyItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.notify_item,
            parent,
            false
        )
        return NotifyViewHolder(viewBind)
    }

    override fun onBindViewHolder(holder: NotifyViewHolder, position: Int) {
        val itemNotify = listNotify[position]
        val bind = holder.bind
        bind.notify = itemNotify
        bind.root.setOnClickListener {
            itemSelect?.invoke(itemNotify)
        }
    }

    override fun getItemCount(): Int {
        return listNotify.size
    }
}