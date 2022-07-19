package com.example.apte4ka.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.apte4ka.R
import com.example.apte4ka.databinding.FragmentPreparationAddBinding
import com.example.apte4ka.presentation.adapter.listaidkit.ListAidKitAdapter
import com.example.apte4ka.presentation.viewmodel.aidkit.AidKitViewModel

class PreparationAddFragment : Fragment() {

    private var aidKitId : Int? = null

    private var _bind : FragmentPreparationAddBinding? = null
    private val bind : FragmentPreparationAddBinding
    get() = _bind?: throw RuntimeException("FragmentPreparationAddBinding == null")

    private lateinit var adapterListAidKit : ListAidKitAdapter

    private lateinit var viewModel : AidKitViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        parseArgs()
        super.onCreate(savedInstanceState)
    }

    private fun parseArgs() {
        arguments?.let {
            aidKitId = it.getInt(AID_KIT_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _bind = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_preparation_add, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[AidKitViewModel::class.java]
        viewModel.listAidKit.observe(viewLifecycleOwner){
            adapterListAidKit.submitList(it)
        }
        recyclerSetup()
    }

    private fun recyclerSetup() : RecyclerView {
        val recyclerView = bind.recyclerListSetAidKit
        with(recyclerView) {
            adapterListAidKit = ListAidKitAdapter()
            adapter = adapterListAidKit
        }
        return recyclerView
    }

    companion object{
        const val AID_KIT_ID = "aid_id"
    }
}