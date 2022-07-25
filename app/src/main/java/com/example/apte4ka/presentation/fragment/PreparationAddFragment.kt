package com.example.apte4ka.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.apte4ka.R
import com.example.apte4ka.databinding.FragmentPreparationAddBinding
import com.example.apte4ka.presentation.adapter.listaidkit.ListAidKitAdapter
import com.example.apte4ka.presentation.viewmodel.aidkit.AidKitViewModel
import com.example.apte4ka.presentation.viewmodel.preparation.PreparationViewModel

class PreparationAddFragment : Fragment() {

    private var aidKitId: Int? = null

    private var _bind: FragmentPreparationAddBinding? = null
    private val bind: FragmentPreparationAddBinding
        get() = _bind ?: throw RuntimeException("FragmentPreparationAddBinding == null")

    private lateinit var adapterListAidKit: ListAidKitAdapter

    private lateinit var aidKitModel: AidKitViewModel

    private lateinit var prepModel: PreparationViewModel

    private var _aidId: Int? = null
    private val aidId: Int
        get() = _aidId ?: throw RuntimeException("aidId == null")

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
        _bind = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_preparation_add,
            container,
            false
        )
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        aidKitModel = ViewModelProvider(this)[AidKitViewModel::class.java]
        prepModel = ViewModelProvider(this)[PreparationViewModel::class.java]
        aidKitModel.listAidKit.observe(viewLifecycleOwner) {
            adapterListAidKit.submitList(it)
            adapterListAidKit.itemSelect = {
                _aidId = it.id
                Log.i("itemId", aidId.toString())
            }
        }
        recyclerSetup()
        addNewPreparation()
       bind.ivAddPhotoPreparation.setOnClickListener {

       }
    }


    private fun addNewPreparation() {
        bind.bAddPreparation.setOnClickListener {
            with(bind) {
                val name = etNamePreparation.text.toString()
                val image = ""
                val symptoms = etAddSymptomsPreparation.text.toString()
                val packing = etPackagePreparation.text.toString()
                val description = etDescriptionPreparation.text.toString()
                val dateCreate = "14.05.2022"
                val dateExp = "14.05.2024"
                prepModel.addPreparationItem(aidId,
                    name,
                    image,
                    symptoms,
                    packing,
                    description,
                    dateCreate,
                    dateExp)
            }
            findNavController().navigate(R.id.action_preparationAddFragment_to_aidKitDetailFragment2)
        }
    }

    private fun recyclerSetup(): RecyclerView {
        val recyclerView = bind.recyclerListSetAidKit
        with(recyclerView) {
            adapterListAidKit = ListAidKitAdapter()
            adapter = adapterListAidKit
        }
        return recyclerView
    }

    companion object {
        const val AID_KIT_ID = "aid_id"
        const val IMAGE_PICK_CODE = 666
        const val PERMISSION_CODE = 999
    }
}