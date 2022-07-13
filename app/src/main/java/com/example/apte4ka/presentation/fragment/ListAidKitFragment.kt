package com.example.apte4ka.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.apte4ka.R
import com.example.apte4ka.databinding.FragmentListAidKitBinding
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
            adapterAidKit.submitList(it)
        }
        setupRecyclerView()
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

}