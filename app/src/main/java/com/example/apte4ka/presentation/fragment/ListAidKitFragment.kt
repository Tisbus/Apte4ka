package com.example.apte4ka.presentation.fragment

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.apte4ka.presentation.AidKitApp
import com.example.apte4ka.R
import com.example.apte4ka.data.service.WorkerUpdateNotify
import com.example.apte4ka.databinding.FragmentListAidKitBinding
import com.example.apte4ka.domain.entity.aidkit.AidKit
import com.example.apte4ka.presentation.adapter.aidkit.AidKitAdapter
import com.example.apte4ka.presentation.viewmodel.aidkit.AidKitViewModel
import com.example.apte4ka.presentation.viewmodel.factory.AidKitViewModelFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ListAidKitFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: AidKitViewModelFactory
    private val component by lazy {
        (requireActivity().application as AidKitApp).component
    }

    private var _bind: FragmentListAidKitBinding? = null
    private val bind: FragmentListAidKitBinding
        get() = _bind ?: throw RuntimeException("FragmentListAidKitBinding == null")

    lateinit var viewModel: AidKitViewModel

    lateinit var adapterAidKit: AidKitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        workerUpdateNotification()
        createNotificationChannel()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_search) {
            findNavController().navigate(R.id.searchFragment)
        }
        return true
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
                        viewModel.deleteAidKitItem(adapterAidKit.currentList[viewHolder.adapterPosition].id)
                    }
                }
            }
        }
        val itemTHDelete = ItemTouchHelper(itemTHDeleteCallback)
        itemTHDelete.attachToRecyclerView(setupRecyclerView())
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

    //notification and work_manager service
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

    private fun workerGetTime(): Long {
        val currentDate = Calendar.getInstance()
        val dueDate = Calendar.getInstance()
        // Set Execution around 05:00:00 AM
        dueDate.set(Calendar.HOUR_OF_DAY, 11)
        dueDate.set(Calendar.MINUTE, 58)
        dueDate.set(Calendar.SECOND, 0)
        if (dueDate.before(currentDate)) {
            dueDate.add(Calendar.HOUR_OF_DAY, 24)
        }
        return dueDate.timeInMillis.minus(currentDate.timeInMillis)
    }

    private fun workerUpdateNotification() {
        val dailyWorkRequest = OneTimeWorkRequestBuilder<WorkerUpdateNotify>()
            .setInitialDelay(workerGetTime(), TimeUnit.MILLISECONDS)
            .build()
        WorkManager.getInstance(requireContext())
            .enqueue(dailyWorkRequest)
    }

    companion object {
        const val AID_KIT_ID = "aid_id"
        const val CHANNEL_ID = "1"
    }
}