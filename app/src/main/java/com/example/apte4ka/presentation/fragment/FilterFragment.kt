package com.example.apte4ka.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
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
import com.example.apte4ka.presentation.AidKitApp
import com.example.apte4ka.presentation.adapter.preparation.PreparationAdapter
import com.example.apte4ka.presentation.adapter.symptom.SymptomAdapter
import com.example.apte4ka.presentation.viewmodel.factory.AidKitViewModelFactory
import com.example.apte4ka.presentation.viewmodel.lists.ListsViewModel
import com.example.apte4ka.presentation.viewmodel.preparation.PreparationViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class FilterFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: AidKitViewModelFactory
    private val component by lazy {
        (requireActivity().application as AidKitApp).component
    }

    private var _bind: FragmentFilterBinding? = null
    private val bind: FragmentFilterBinding
        get() = _bind ?: throw RuntimeException("FragmentFilterBinding == null")

    private lateinit var adapterSymptom: SymptomAdapter
    private lateinit var adapterPrep: PreparationAdapter

    private lateinit var viewModelPreparation: PreparationViewModel
    private lateinit var listsModel: ListsViewModel

    private var listSymptoms: List<Symptom> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
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
        viewModelPreparation =
            ViewModelProvider(this, viewModelFactory)[PreparationViewModel::class.java]
        listsModel = ViewModelProvider(this, viewModelFactory)[ListsViewModel::class.java]
        viewModelPreparation.listPreparation.observe(viewLifecycleOwner) {
            adapterPrep.submitList(it)
            enterFilter(adapterPrep.currentList)
            clearFilter(it)
        }
        setupRecyclerView()
        dataSymptom()
    }

    //for api max 26
    private fun getCountDayToEnd(endDate: String): Long {
        val fmt = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val toDay = Date()
        val eDate = fmt.parse(endDate)
        val milliseconds = eDate?.time?.minus(toDay.time)
        val days = (milliseconds?.div(1000) ?: throw RuntimeException("div to zero")).div(3600)
            .div(24)
        Log.i("Time", days.toString())
        return days
    }

    private fun isNotEmptySymptom(): Boolean {
        var isEmpty = false
        listSymptoms.forEach { i ->
            if (i.status) {
                isEmpty = true
            }
        }
        return isEmpty
    }

    private fun clearFilter(it: MutableList<Preparation>?) {
        bind.bClearAll.setOnClickListener { i ->
            listSymptoms.forEach { i ->
                i.status = false
            }
            adapterPrep.submitList(it)
            dataSymptom()
            bind.cbCountEndDay.isChecked = false
        }
    }

    private fun dataSymptom() {
        recyclerSetupSymptom()
        selectSymptoms()
    }

    private fun enterFilter(item: MutableList<Preparation>) {
        bind.bEnterFilter.setOnClickListener {
            val itemFilter = mutableListOf<Preparation>()
            itemFilter.clear()
            if (isNotEmptySymptom()) {
                item.forEach {
                    it.symptoms.forEach { i ->
                        if (i.name.contains(getSymptomToList())) {
                            itemFilter.add(it)
                        }
                    }
                }
                if (bind.cbCountEndDay.isChecked) {
                    itemFilter.removeIf { i ->
                        getCountDayToEnd(i.dateExp).toInt() > 30
                    }
                }
                adapterPrep.submitList(itemFilter)
            }
            if (bind.cbCountEndDay.isChecked && !isNotEmptySymptom()) {
                itemFilter.addAll(item)
                itemFilter.removeIf { i ->
                    getCountDayToEnd(i.dateExp).toInt() > 30
                }
                adapterPrep.submitList(itemFilter)
            }
        }
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

    private fun setSymptomStatus(item: Symptom) {
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