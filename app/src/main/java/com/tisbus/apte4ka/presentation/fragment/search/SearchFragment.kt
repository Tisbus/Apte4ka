package com.tisbus.apte4ka.presentation.fragment.search

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.tisbus.apte4ka.R
import com.tisbus.apte4ka.databinding.FragmentSearchBinding
import com.tisbus.apte4ka.domain.entity.preparation.Preparation
import com.tisbus.apte4ka.presentation.AidKitApp
import com.tisbus.apte4ka.presentation.adapter.search.SearchAdapter
import com.tisbus.apte4ka.presentation.viewmodel.factory.AidKitViewModelFactory
import com.tisbus.apte4ka.presentation.viewmodel.preparation.PreparationViewModel
import javax.inject.Inject

class SearchFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: AidKitViewModelFactory
    private val component by lazy {
        (requireActivity().application as AidKitApp).component
    }
    private var _bind: FragmentSearchBinding? = null
    private val bind: FragmentSearchBinding
        get() = _bind ?: throw RuntimeException("FragmentSearchBinding == null")

    private lateinit var adapterSearch: SearchAdapter

    private lateinit var viewModelPreparation: PreparationViewModel

    private var listPrep: MutableList<Preparation> = mutableListOf()

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelPreparation =
            ViewModelProvider(this, viewModelFactory)[PreparationViewModel::class.java]
        viewModelPreparation.listPreparation.observe(viewLifecycleOwner) {
            listPrep = it
            setupRecyclerView()
        }
        setupRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        inflater.inflate(R.menu.filter_menu, menu)
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

    private fun setupBackButton() {
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> requireActivity().onBackPressed()
            R.id.action_filter -> findNavController().navigate(R.id.action_searchFragment_to_filterFragment)
        }
        return true
    }

    private fun goToDetailPreparation(itemId: Int) {
        val bundle = bundleOf(DETAIL_PREP_ID to itemId)
        findNavController().navigate(
            R.id.action_searchFragment_to_preparationDetailFragment,
            bundle
        )
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