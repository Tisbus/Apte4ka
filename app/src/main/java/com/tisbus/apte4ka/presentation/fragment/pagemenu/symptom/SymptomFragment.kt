package com.tisbus.apte4ka.presentation.fragment.pagemenu.symptom

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.setPadding
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.tisbus.apte4ka.R
import com.tisbus.apte4ka.databinding.FragmentSymptomBinding
import com.tisbus.apte4ka.domain.entity.symptom.Symptom
import com.tisbus.apte4ka.presentation.AidKitApp
import com.tisbus.apte4ka.presentation.adapter.symptom.direct.SymptomDirectAdapter
import com.tisbus.apte4ka.presentation.viewmodel.factory.AidKitViewModelFactory
import com.tisbus.apte4ka.presentation.viewmodel.lists.ListsViewModel
import javax.inject.Inject

class SymptomFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: AidKitViewModelFactory
    val component by lazy {
        (requireActivity().application as AidKitApp).component
    }

    private var _bind: FragmentSymptomBinding? = null
    private val bind: FragmentSymptomBinding
        get() = _bind ?: throw RuntimeException("FragmentSymptomBinding == null")

    private lateinit var adapterSymptom: SymptomDirectAdapter

    private lateinit var symptomViewModel: ListsViewModel

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
        // Inflate the layout for this fragment
        _bind = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_symptom,
            container,
            false
        )
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        symptomViewModel = ViewModelProvider(this, viewModelFactory)[ListsViewModel::class.java]
        symptomViewModel.listSymptom.observe(viewLifecycleOwner) {
            adapterSymptom.submitList(it)
        }
        setupAdapter()
        addSymptom()
        editSymptom()
    }

    private fun editSymptom() {
        adapterSymptom.itemSelect = {
            showEditDialog(it)
        }
    }

    //AlertDialog edit Symptom
    private fun showEditDialog(item : Symptom) {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setTitle("Изменение Симптома")
        //add edit text in builder
        val inputSymptom = EditText(requireContext())
        inputSymptom.hint = "Введити имя симптома"
        inputSymptom.inputType = InputType.TYPE_CLASS_TEXT
        inputSymptom.setPadding(50)
        inputSymptom.setText(item.name)
        dialogBuilder.setView(inputSymptom)
        dialogBuilder.setPositiveButton("Да") { dialog, id ->
            symptomViewModel.editSymptomItem(
                inputSymptom.text.toString(),
                "",
                false,
                item
            )
        }
        dialogBuilder.setNegativeButton("Нет") { dialog, id ->

        }
        val b = dialogBuilder.create()
        b.show()
    }


    private fun addSymptom() {
        bind.fabAddSymptom.setOnClickListener {
            showNewAddDialog()
        }
    }

    //AlertDialog add new Symptom
    private fun showNewAddDialog() {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setTitle("Добавление Симптома")
        //add edit text in builder
        val inputSymptom = EditText(requireContext())
        inputSymptom.hint = "Введити имя симптома"
        inputSymptom.inputType = InputType.TYPE_CLASS_TEXT
        inputSymptom.setPadding(50)
        dialogBuilder.setView(inputSymptom)
        dialogBuilder.setPositiveButton("Да") { dialog, id ->
            symptomViewModel.addSymptomItem(
                inputSymptom.text.toString(),
                "",
                false
            )
        }
        dialogBuilder.setNegativeButton("Нет") { dialog, id ->

        }
        val b = dialogBuilder.create()
        b.show()
    }

    private fun setupAdapter(): RecyclerView {
        val recycler = bind.rvSymptomDirect
        adapterSymptom = SymptomDirectAdapter()
        recycler.adapter = adapterSymptom
        return recycler
    }
}