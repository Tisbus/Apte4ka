package com.example.apte4ka.presentation.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apte4ka.R
import com.example.apte4ka.databinding.FragmentSearchBinding
import com.example.apte4ka.domain.entity.preparation.Preparation
import com.example.apte4ka.domain.entity.symptom.Symptom
import com.example.apte4ka.presentation.adapter.search.SearchAdapter
import com.example.apte4ka.presentation.adapter.symptom.SymptomAdapter
import com.example.apte4ka.presentation.viewmodel.lists.ListsViewModel
import com.example.apte4ka.presentation.viewmodel.preparation.PreparationViewModel

class SearchFragment : Fragment() {

    private var _bind: FragmentSearchBinding? = null
    private val bind: FragmentSearchBinding
        get() = _bind ?: throw RuntimeException("FragmentSearchBinding == null")

    private lateinit var adapterSearch: SearchAdapter
    private lateinit var adapterSymptom: SymptomAdapter

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
        _bind = FragmentSearchBinding.inflate(inflater, container, false)
        setupBackButton()
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelPreparation = ViewModelProvider(this)[PreparationViewModel::class.java]
        listsModel = ViewModelProvider(this)[ListsViewModel::class.java]
        viewModelPreparation.listPreparation.observe(viewLifecycleOwner) {
            listPrep = it
            setupRecyclerView()
        }
        setupRecyclerView()
        recyclerSetupSymptom()
        selectSymptoms()
        enterFilterSymptom()
    }

    private fun enterFilterSymptom() {
        bind.bEnterFilter.setOnClickListener {
            val listFilterSymptoms: MutableList<String> = mutableListOf()
            listSymptoms.forEach {
                if (it.status) {
                    listFilterSymptoms.add(it.name)
                }
            }
            val listFilterPrep: MutableList<Preparation> = mutableListOf()
            listPrep.forEach { i ->
                i.symptoms.forEach { e ->
                    if (e.name.equals(listFilterSymptoms)) {
                        listFilterPrep.add(i)
                    }
                }
            }
            listPrep = listFilterPrep
            setupRecyclerView()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterSearch.filter.filter(newText)
                return false
            }
        })
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

    private fun selectSymptoms() {
        adapterSymptom.itemSelect = {
            it.status = !it.status
        }
    }

    private fun setupBackButton() {
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> requireActivity().onBackPressed()
        }
        return true
    }

    private fun goToDetailPreparation(itemId: Int) {
        val bundle = bundleOf(DETAIL_PREP_ID to itemId)
        findNavController().navigate(R.id.action_searchFragment_to_preparationDetailFragment,
            bundle)
    }

    private fun setupRecyclerView(): RecyclerView {
        val recycler = bind.rvSearch
        with(recycler) {
            adapterSearch = SearchAdapter(listPrep)
            adapter = adapterSearch
        }
        adapterSearch.itemSelect = {
            goToDetailPreparation(it.id)
        }
        return recycler
    }

    companion object {
        const val DETAIL_PREP_ID = "detail_prep_id"
    }
}