package com.example.apte4ka.presentation.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.apte4ka.R
import com.example.apte4ka.databinding.FragmentPreparationDetailBinding
import com.example.apte4ka.presentation.viewmodel.preparation.PreparationViewModel


class PreparationDetailFragment : Fragment() {


    private var idPrep : Int? = null

    private var _bind : FragmentPreparationDetailBinding? = null
    private val bind : FragmentPreparationDetailBinding
    get() = _bind ?: throw RuntimeException("FragmentPreparationDetailBinding == null")

    private lateinit var viewModelPrep : PreparationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idPrep = it.getInt(DETAIL_PREP_ID)
        }
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> requireActivity().onBackPressed()
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
        viewModelPrep = ViewModelProvider(this)[PreparationViewModel::class.java]
        getData()
    }

    private fun getData() {
        bind.detailPrep = viewModelPrep
        bind.lifecycleOwner = viewLifecycleOwner
        idPrep?.let { viewModelPrep.getPreparationItem(it) }
    }

    companion object{
        const val DETAIL_PREP_ID = "detail_prep_id"
    }
}