package com.example.apte4ka.presentation.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apte4ka.R
import com.example.apte4ka.databinding.FragmentFilterBinding
import com.example.apte4ka.domain.entity.preparation.Preparation
import com.example.apte4ka.domain.entity.symptom.Symptom
import com.example.apte4ka.presentation.adapter.preparation.PreparationAdapter
import com.example.apte4ka.presentation.adapter.search.SearchAdapter
import com.example.apte4ka.presentation.adapter.symptom.SymptomAdapter
import com.example.apte4ka.presentation.viewmodel.lists.ListsViewModel
import com.example.apte4ka.presentation.viewmodel.preparation.PreparationViewModel


class FilterFragment : Fragment() {

    private var _bind: FragmentFilterBinding? = null
    private val bind: FragmentFilterBinding
        get() = _bind ?: throw RuntimeException("FragmentFilterBinding == null")

    private lateinit var adapterSymptom: SymptomAdapter
    private lateinit var adapterPrep: PreparationAdapter

    private lateinit var viewModelPreparation: PreparationViewModel
    private lateinit var listsModel: ListsViewModel

    private var listPrep: MutableList<Preparation> = mutableListOf()
    private var listSymptoms: List<Symptom> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _bind = FragmentFilterBinding.inflate(
            inflater,
            container,
            false
        )
        setupBackButton()
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelPreparation = ViewModelProvider(this)[PreparationViewModel::class.java]
        listsModel = ViewModelProvider(this)[ListsViewModel::class.java]
        viewModelPreparation.listPreparation.observe(viewLifecycleOwner) {
            adapterPrep.submitList(it)
        }
        setupRecyclerView()
        recyclerSetupSymptom()
        selectSymptoms()
    }

    private fun recyclerSetupSymptom(): RecyclerView {
        val recyclerSymptoms = bind.rSetSymptoms
        listSymptoms = listsModel.listSymptom
        adapterSymptom = SymptomAdapter(listSymptoms)
        with(recyclerSymptoms) {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = adapterSymptom
        }
        return recyclerSymptoms
    }

    private fun setupRecyclerView(): RecyclerView {
        val recycler = bind.rvSearch
        with(recycler) {
            adapterPrep = PreparationAdapter()
            adapter = adapterPrep
        }
        adapterPrep.itemSelect = {
            goToDetailPreparation(it.id)
        }
        return recycler
    }

    private fun getSymptomToList(): String {
        var filterSymptom = ""
        listSymptoms.forEach {
            if (it.status) {
                filterSymptom = it.name
            }
        }
        return filterSymptom
    }

    private fun setSymptomStatus(item : Symptom) {
        listSymptoms.forEach {
            if (item != it) {
                it.status = false
            }
        }
    }

    private fun selectSymptoms() {
        adapterSymptom.itemSelect = {
            it.status = true
            setSymptomStatus(it)
        }
    }

    private fun setupBackButton() {
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> findNavController().navigate(R.id.action_filterFragment_to_searchFragment)
        }
        return true
    }

    private fun goToDetailPreparation(itemId: Int) {
        val bundle = bundleOf(SearchFragment.DETAIL_PREP_ID to itemId)
        findNavController().navigate(
            R.id.action_filterFragment_to_preparationDetailFragment,
            bundle
        )
    }

    companion object {
        const val DETAIL_PREP_ID = "detail_prep_id"
    }
}