package com.example.apte4ka.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.apte4ka.R
import com.example.apte4ka.databinding.FragmentAidKitEditBinding
import com.example.apte4ka.presentation.viewmodel.aidkit.AidKitViewModel


class AidKitEditFragment : Fragment() {

    private var aidKitId: Int? = null

    private var _bind: FragmentAidKitEditBinding? = null
    private val bind: FragmentAidKitEditBinding
        get() = _bind ?: throw RuntimeException("FragmentAidKitEditBinding == null")

    private val viewModel by lazy {
        ViewModelProvider(this)[AidKitViewModel::class.java]
    }

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
        _bind = FragmentAidKitEditBinding.inflate(layoutInflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("check", aidKitId.toString())
        getData()
        saveEditForm()

    }

    private fun saveEditForm() {
        with(bind) {
            bind.bConfirmAddAidKit.setOnClickListener {
                viewModel.editAidKitItem(
                    etName.text.toString(),
                    etDescription.text.toString()
                )
                findNavController().navigate(R.id.action_aidKitEditFragment_to_listAidKitFragment)
            }
        }
    }

    private fun getData() {
        aidKitId?.let {
            viewModel.getAidKitItem(it)
        }
        with(bind) {
            aidEdit = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

    companion object {
        const val AID_KIT_ID = "aid_id"
    }
}