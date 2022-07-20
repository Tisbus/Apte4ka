package com.example.apte4ka.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.apte4ka.R
import com.example.apte4ka.databinding.FragmentAidKitDetailBinding
import com.example.apte4ka.presentation.adapter.preparation.PreparationAdapter
import com.example.apte4ka.presentation.viewmodel.preparation.PreparationViewModel

class AidKitDetailFragment : Fragment() {

    private var aidKitId: Int? = null

    private var _bind : FragmentAidKitDetailBinding? = null
    private val bind : FragmentAidKitDetailBinding
    get() = _bind ?: throw RuntimeException("FragmentAidKitDetailBinding == null")

    private lateinit var prepModel : PreparationViewModel

    private lateinit var adapterPrep : PreparationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        parseArgs()
        super.onCreate(savedInstanceState)
    }

    private fun parseArgs() {
        aidKitId = arguments?.getInt(AID_KIT_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _bind = FragmentAidKitDetailBinding.inflate(layoutInflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepModel = ViewModelProvider(this)[PreparationViewModel::class.java]
        Log.i("check", aidKitId.toString())
        prepModel.listPreparation.observe(viewLifecycleOwner){
            adapterPrep.submitList(it)
        }
        recyclerSetup()
        addPreparation()
    }

    private fun recyclerSetup(): RecyclerView {
        val recyclerView = bind.recyclerListPreparation
        with(recyclerView) {
            adapterPrep = PreparationAdapter()
            adapter = adapterPrep
        }
        return recyclerView
    }

    private fun addPreparation() {
        bind.linerAddPrepBlock.setOnClickListener {
            val bundle = bundleOf(AID_KIT_ID to aidKitId)
            findNavController().navigate(R.id.action_aidKitDetailFragment_to_preparationAddFragment,
                bundle)
        }
    }

    companion object{
        const val AID_KIT_ID = "aid_id"
    }
}