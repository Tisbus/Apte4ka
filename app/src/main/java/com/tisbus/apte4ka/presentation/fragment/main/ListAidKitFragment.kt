package com.tisbus.apte4ka.presentation.fragment.main

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.tisbus.apte4ka.R
import com.tisbus.apte4ka.databinding.FragmentListAidKitBinding
import com.tisbus.apte4ka.domain.entity.aidkit.AidKit
import com.tisbus.apte4ka.presentation.AidKitApp
import com.tisbus.apte4ka.presentation.adapter.aidkit.AidKitAdapter
import com.tisbus.apte4ka.presentation.viewmodel.aidkit.AidKitViewModel
import com.tisbus.apte4ka.presentation.viewmodel.factory.AidKitViewModelFactory
import com.tisbus.apte4ka.presentation.viewmodel.preparation.PreparationViewModel
import javax.inject.Inject

class ListAidKitFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: AidKitViewModelFactory

    private val component by lazy {
        (requireActivity().application as AidKitApp).component
    }

    private lateinit var viewModelPrep: PreparationViewModel
    private var _bind: FragmentListAidKitBinding? = null
    private val bind: FragmentListAidKitBinding
        get() = _bind ?: throw RuntimeException("FragmentListAidKitBinding == null")

    lateinit var viewModel: AidKitViewModel

    lateinit var adapterAidKit: AidKitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _bind = FragmentListAidKitBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[AidKitViewModel::class.java]
        viewModelPrep = ViewModelProvider(this, viewModelFactory)[PreparationViewModel::class.java]
        addNewAidKit()
        addNewPrep()
        viewModel.listAidKit.observe(viewLifecycleOwner) {
            checkAidListSize(it)
            adapterAidKit.submitList(it)
        }
        setupRecyclerView()
        setupItemTouch()
        goToDetailAidKit()
    }

    private fun setupItemTouch() {
        itemTouchDelete()
        itemTouchEdit()
    }

    private fun itemTouchEdit() {
        val itemTHEditCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
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
                        val bundle =
                            bundleOf(AID_KIT_ID to adapterAidKit.currentList[viewHolder.adapterPosition].id)
                        findNavController().navigate(R.id.action_listAidKitFragment_to_aidKitEditFragment,
                            bundle)
                    }
                }
            }
        }
        val itemTHEdit = ItemTouchHelper(itemTHEditCallback)
        itemTHEdit.attachToRecyclerView(setupRecyclerView())
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
                        showNewDeleteDialog(viewHolder)
                    }
                }
            }
        }
        val itemTHDelete = ItemTouchHelper(itemTHDeleteCallback)
        itemTHDelete.attachToRecyclerView(setupRecyclerView())
    }

    //AlertDialog delete aid_kit and prep
    private fun showNewDeleteDialog(viewHolder: RecyclerView.ViewHolder) {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setTitle("Удаление аптечки")
        dialogBuilder.setMessage("Хотите удалить аптечку и препараты которые в ней находятся?")
        dialogBuilder.setPositiveButton("Да") { dialog, id ->
            val aidItem = adapterAidKit.currentList[viewHolder.adapterPosition].id
            viewModelPrep.deletePrepItemAidId(aidItem)
            viewModel.deleteAidKitItem(aidItem)
            findNavController().navigate(R.id.listAidKitFragment)
        }
        dialogBuilder.setNegativeButton("Нет") { dialog, id ->
            findNavController().navigate(R.id.listAidKitFragment)
        }
        val b = dialogBuilder.create()
        b.show()
    }

    private fun goToDetailAidKit() {
        adapterAidKit.onItemSelect = {
            val bundle = bundleOf(AID_KIT_ID to it.id)
            findNavController().navigate(R.id.action_listAidKitFragment_to_aidKitDetailFragment,
                bundle)
        }
    }

    private fun checkAidListSize(it: MutableList<AidKit>) {
        with(bind) {
            if (it.size > 0) {
                svAidList.visibility = View.VISIBLE
                linerStartAddBlock.visibility = View.GONE
                linerMainAddBlock.visibility = View.VISIBLE
            } else {
                svAidList.visibility = View.GONE
                linerStartAddBlock.visibility = View.VISIBLE
                linerMainAddBlock.visibility = View.INVISIBLE
            }
        }
    }

    private fun setupRecyclerView(): RecyclerView {
        val recyclerView = bind.recyclerAidKit
        with(recyclerView) {
            adapterAidKit = AidKitAdapter()
            adapter = adapterAidKit
        }
        return recyclerView
    }

    private fun addNewPrep() {
        bind.linerMainAddPrepBlock.setOnClickListener {
            findNavController().navigate(R.id.action_listAidKitFragment_to_preparationAddFragment)
        }
    }

    private fun addNewAidKit() {
        bind.linerStartAddBlock.setOnClickListener {
            findNavController().navigate(R.id.action_listAidKitFragment_to_aidKitAddFragment)
        }
        bind.linerMainAddAidBlock.setOnClickListener {
            findNavController().navigate(R.id.action_listAidKitFragment_to_aidKitAddFragment)
        }
    }

    //notification
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Срок годности"
            val descText = "Каннал уведомлений о сроках годности"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
                .apply { description = descText }
            val notificationManager =
                requireActivity().getSystemService(Context.NOTIFICATION_SERVICE)
                        as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val AID_KIT_ID = "aid_id"
        const val CHANNEL_ID = "1"
    }
}