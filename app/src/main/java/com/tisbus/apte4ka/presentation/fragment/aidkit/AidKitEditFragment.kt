package com.tisbus.apte4ka.presentation.fragment.aidkit

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tisbus.apte4ka.R
import com.tisbus.apte4ka.databinding.FragmentAidKitEditBinding
import com.tisbus.apte4ka.presentation.AidKitApp
import com.tisbus.apte4ka.presentation.adapter.aidkit.list_icons.ListIconsAdapter
import com.tisbus.apte4ka.presentation.viewmodel.aidkit.AidKitViewModel
import com.tisbus.apte4ka.presentation.viewmodel.factory.AidKitViewModelFactory
import javax.inject.Inject


class AidKitEditFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: AidKitViewModelFactory
    private val component by lazy {
        (requireActivity().application as AidKitApp).component
    }
    private var aidKitId: Int? = null

    private var _iconId : String? = ""
    private val iconId : String
    get() = _iconId ?: throw RuntimeException("iconId == null")

    private var _bind: FragmentAidKitEditBinding? = null
    private val bind: FragmentAidKitEditBinding
        get() = _bind ?: throw RuntimeException("FragmentAidKitEditBinding == null")

    private lateinit var viewModel : AidKitViewModel
    private lateinit var adapterItem: ListIconsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        parseArgs()
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
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
        setupBackButton()
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[AidKitViewModel::class.java]
        getData()
        chooseIcon()
        saveEditForm()
    }

    private fun chooseIcon() {
        bind.ivIconAidKit.setOnClickListener {
            showNewDeleteDialog()
        }
    }

    //AlertDialog
    private fun showNewDeleteDialog() {
        val imageList = layoutInflater.inflate(R.layout.custom_aid_kit, null)
        val recyclerView = imageList.findViewById<RecyclerView>(R.id.rvCustomAidIcon)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        adapterItem = ListIconsAdapter()
        recyclerView.adapter = adapterItem
        val dialogBuilder = AlertDialog.Builder(requireContext())
            .setTitle("Выберите иконку")
            .setView(imageList)
        val dialog = dialogBuilder.create()
        dialog.show()
        adapterItem.itemSelect = {
            Picasso.get().load(it).into(bind.ivIconAidKit)
            _iconId = it.toString()
            dialog.dismiss()
        }
    }

    private fun saveEditForm() {
        with(bind) {
            bind.bConfirmAddAidKit.setOnClickListener {
                if(iconId.isEmpty()){
                    _iconId = viewModel.aidKitLD.value?.icon
                }
                viewModel.editAidKitItem(
                    etName.text.toString(),
                    etDescription.text.toString(),
                    iconId
                )
                findNavController().navigate(R.id.action_aidKitEditFragment_to_listAidKitFragment)
            }
        }
    }

    private fun getData() {
        with(bind) {
            aidEdit = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        aidKitId?.let {
            viewModel.getAidKitItem(it)
        }
    }

    companion object {
        const val AID_KIT_ID = "aid_id"
    }
}