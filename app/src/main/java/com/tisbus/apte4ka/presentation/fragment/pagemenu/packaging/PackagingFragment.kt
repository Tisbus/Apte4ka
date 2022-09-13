package com.tisbus.apte4ka.presentation.fragment.pagemenu.packaging

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.tisbus.apte4ka.R
import com.tisbus.apte4ka.databinding.FragmentPackagingBinding
import com.tisbus.apte4ka.presentation.AidKitApp
import com.tisbus.apte4ka.presentation.adapter.packaging.direct.PackagingDirectAdapter
import com.tisbus.apte4ka.presentation.adapter.packaging.prep.PackagingAdapter
import com.tisbus.apte4ka.presentation.viewmodel.factory.AidKitViewModelFactory
import com.tisbus.apte4ka.presentation.viewmodel.lists.ListsViewModel
import javax.inject.Inject

class PackagingFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory : AidKitViewModelFactory
    val component by lazy{
        (requireActivity().application as AidKitApp).component
    }
    private var _bind: FragmentPackagingBinding? = null
    private val bind: FragmentPackagingBinding
        get() = _bind ?: throw RuntimeException("FragmentPackagingBinding == null")

    private lateinit var adapterPackaging : PackagingDirectAdapter

    private lateinit var packagingViewModel : ListsViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _bind = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_packaging,
            container,
            false
        )
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        packagingViewModel = ViewModelProvider(this, viewModelFactory)[ListsViewModel::class.java]
        packagingViewModel.listPackaging.observe(viewLifecycleOwner){
            adapterPackaging.submitList(it)
        }
        setupRecycler()
    }

    private fun setupRecycler() : RecyclerView{
        val recycler = bind.rvPackagingDirect
        adapterPackaging = PackagingDirectAdapter()
        recycler.adapter = adapterPackaging
        return recycler
    }

    companion object {

    }
}