package com.tisbus.apte4ka.presentation.fragment.aidkit

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tisbus.apte4ka.R
import com.tisbus.apte4ka.databinding.FragmentAidKitAddBinding
import com.tisbus.apte4ka.presentation.AidKitApp
import com.tisbus.apte4ka.presentation.adapter.aidkit.list_icons.ListIconsAdapter
import com.tisbus.apte4ka.presentation.viewmodel.aidkit.AidKitViewModel
import com.tisbus.apte4ka.presentation.viewmodel.factory.AidKitViewModelFactory
import javax.inject.Inject

class AidKitAddFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: AidKitViewModelFactory
    private val component by lazy {
        (requireActivity().application as AidKitApp).component
    }
    private var _bind: FragmentAidKitAddBinding? = null
    private val bind: FragmentAidKitAddBinding
        get() = _bind ?: throw RuntimeException("FragmentAidKitAddBinding == null")

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[AidKitViewModel::class.java]
    }
    private lateinit var adapterItem: ListIconsAdapter

    private var iconId = ICON_DEFAULT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        _bind = FragmentAidKitAddBinding.inflate(inflater, container, false)
        setupBackButton()
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(bind) {
            ivIconAidKit.setOnClickListener {
                showNewDeleteDialog()
            }
            bConfirmAddAidKit.setOnClickListener {
                addAidKit()
                findNavController().navigate(R.id.action_aidKitAddFragment_to_listAidKitFragment)
            }
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
            iconId = it.toString()
            dialog.dismiss()
        }
    }

    private fun addAidKit() {
        with(bind) {
            viewModel.addAidKit(
                etName.text.toString(),
                etDescription.text.toString(),
                iconId
            )
        }
    }

    companion object {
        const val ICON_DEFAULT = R.drawable.aid_kit_in_list.toString()
    }
}