package com.example.apte4ka.presentation.fragment.preparation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.apte4ka.R
import com.example.apte4ka.databinding.FragmentPreparationDetailBinding
import com.example.apte4ka.presentation.AidKitApp
import com.example.apte4ka.presentation.viewmodel.factory.AidKitViewModelFactory
import com.example.apte4ka.presentation.viewmodel.preparation.PreparationViewModel
import javax.inject.Inject


class PreparationDetailFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: AidKitViewModelFactory
    private val component by lazy {
        (requireActivity().application as AidKitApp).component
    }

    private var idPrep: Int? = null
    private var aidKitId: Int? = null

    private var _bind: FragmentPreparationDetailBinding? = null
    private val bind: FragmentPreparationDetailBinding
        get() = _bind ?: throw RuntimeException("FragmentPreparationDetailBinding == null")

    private lateinit var viewModelPrep: PreparationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idPrep = it.getInt(DETAIL_PREP_ID)
            aidKitId = arguments?.getInt(AID_KIT_ID)
        }
        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            requireActivity().onBackPressed()
        }
        return true
    }

    private fun setupBackButton() {
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _bind = FragmentPreparationDetailBinding.inflate(inflater, container, false)
        setupBackButton()
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelPrep = ViewModelProvider(this, viewModelFactory)[PreparationViewModel::class.java]
        getData()
        bind.bCopyPrepDetail.setOnClickListener {
            val bundle = bundleOf(DETAIL_PREP_ID to idPrep,
                AID_KIT_ID to aidKitId)
            findNavController().navigate(
                R.id.action_preparationDetailFragment_to_preparationCopyFragment,
                bundle
            )
        }
    }

    private fun getData() {
        bind.detailPrep = viewModelPrep
        bind.lifecycleOwner = viewLifecycleOwner
        idPrep?.let {
            viewModelPrep.getPreparationItem(it)
        }
    }

    companion object {
        const val AID_KIT_ID = "aid_id"
        const val DETAIL_PREP_ID = "detail_prep_id"
    }
}