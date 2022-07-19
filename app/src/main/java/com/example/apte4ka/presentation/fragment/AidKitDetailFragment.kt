package com.example.apte4ka.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.apte4ka.R
import com.example.apte4ka.databinding.FragmentAidKitDetailBinding

class AidKitDetailFragment : Fragment() {

    private var aidKitId: Int? = null

    private var _bind : FragmentAidKitDetailBinding? = null
    private val bind : FragmentAidKitDetailBinding
    get() = _bind ?: throw RuntimeException("FragmentAidKitDetailBinding == null")

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
        Log.i("check", aidKitId.toString())
        addPreparation()
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