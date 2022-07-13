package com.example.apte4ka.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.apte4ka.R
import com.example.apte4ka.databinding.FragmentAidKitAddBinding
import com.example.apte4ka.domain.entity.aidkit.AidKit
import com.example.apte4ka.presentation.viewmodel.aidkit.AidKitViewModel

class AidKitAddFragment : Fragment() {

    private var _bind : FragmentAidKitAddBinding? = null
    private val bind : FragmentAidKitAddBinding
    get() = _bind ?: throw RuntimeException("FragmentAidKitAddBinding == null")

    private val viewModel by lazy{
        ViewModelProvider(this)[AidKitViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _bind = FragmentAidKitAddBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.bConfirmAddAidKit.setOnClickListener {
            addAidKit()
            findNavController().navigate(R.id.action_aidKitAddFragment_to_listAidKitFragment)
        }
    }

    private fun addAidKit() {
        with(bind) {
            viewModel.addAidKit(
                etName.text.toString(),
                etDescription.text.toString()
            )
        }
    }
}