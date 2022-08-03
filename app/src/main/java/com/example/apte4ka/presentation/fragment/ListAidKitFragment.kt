package com.example.apte4ka.presentation.fragment

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.apte4ka.R
import com.example.apte4ka.databinding.FragmentListAidKitBinding
import com.example.apte4ka.domain.entity.aidkit.AidKit
import com.example.apte4ka.presentation.adapter.aidkit.AidKitAdapter
import com.example.apte4ka.presentation.viewmodel.aidkit.AidKitViewModel

class ListAidKitFragment : Fragment() {

    private var _bind : FragmentListAidKitBinding? = null
    private val bind : FragmentListAidKitBinding
    get() = _bind ?: throw RuntimeException("FragmentListAidKitBinding == null")

    lateinit var viewModel : AidKitViewModel

    lateinit var adapterAidKit: AidKitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_search -> findNavController().navigate(R.id.searchFragment)
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
        viewModel = ViewModelProvider(this)[AidKitViewModel::class.java]
        addNewAidKit()
        addNewPrep()
        viewModel.listAidKit.observe(viewLifecycleOwner){
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

    private fun setupRecyclerView() : RecyclerView {
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
    companion object{
        const val AID_KIT_ID = "aid_id"
    }

}