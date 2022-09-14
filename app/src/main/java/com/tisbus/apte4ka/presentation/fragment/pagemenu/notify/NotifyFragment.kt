package com.tisbus.apte4ka.presentation.fragment.pagemenu.notify

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.tisbus.apte4ka.R
import com.tisbus.apte4ka.databinding.FragmentNotifyBinding
import com.tisbus.apte4ka.domain.entity.aidkit.AidKit
import com.tisbus.apte4ka.domain.entity.notify.Notify
import com.tisbus.apte4ka.presentation.AidKitApp
import com.tisbus.apte4ka.presentation.adapter.notify.NotifyAdapter
import com.tisbus.apte4ka.presentation.viewmodel.factory.AidKitViewModelFactory
import com.tisbus.apte4ka.presentation.viewmodel.notify.NotifyViewModel
import javax.inject.Inject

class NotifyFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory : AidKitViewModelFactory
    val component by lazy{
        ((requireActivity().application) as AidKitApp).component
    }
    lateinit var viewNotifyModel : NotifyViewModel
    lateinit var adapterNotify : NotifyAdapter

    private var _bind : FragmentNotifyBinding? = null
    private val bind : FragmentNotifyBinding
    get() = _bind ?: throw RuntimeException("FragmentNotifyBinding == null")

    private var listNotify : MutableList<Notify> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _bind = DataBindingUtil.inflate(
            layoutInflater,
            R.id.notifyFragment,
            container,
            false
        )
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewNotifyModel = ViewModelProvider(this, viewModelFactory)[NotifyViewModel::class.java]
        viewNotifyModel.notifyList.observe(viewLifecycleOwner){
            listNotify = it
            setupRecycler()
            goToDetailPrep()
            itemTouchDelete()
            itemTouchCheck()
        }
    }

    private fun goToDetailPrep() {
        adapterNotify.itemSelect = {
            it.status = false
            val bundle = bundleOf(DETAIL_PREP_ID to it.idPrep)
            findNavController().navigate(
                R.id.action_notifyFragment_to_preparationDetailFragment,
                bundle
            )
        }
    }

    private fun itemTouchDelete() {
        val itemTHDeleteCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        val itemNotify = listNotify[viewHolder.adapterPosition].id
                        viewNotifyModel.deleteNotifyItem(itemNotify)
                    }
                }
            }
        }
        val itemTHDelete = ItemTouchHelper(itemTHDeleteCallback)
        itemTHDelete.attachToRecyclerView(setupRecycler())
    }

    private fun itemTouchCheck() {
        val itemTHCheckCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when (direction) {
                    ItemTouchHelper.RIGHT -> {
                       listNotify[viewHolder.adapterPosition].status = false
                    }
                }
            }
        }
        val itemTHCheck = ItemTouchHelper(itemTHCheckCallback)
        itemTHCheck.attachToRecyclerView(setupRecycler())
    }

    private fun setupRecycler() : RecyclerView {
        val recycler = bind.rvNotify
        adapterNotify = NotifyAdapter(listNotify)
        recycler.adapter = adapterNotify
        return recycler
    }

    companion object {
        const val DETAIL_PREP_ID = "detail_prep_id"
    }
}