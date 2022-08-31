package com.tisbus.apte4ka.presentation.fragment.preparation

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.tisbus.apte4ka.R
import com.tisbus.apte4ka.databinding.FragmentPreparationEditBinding
import com.tisbus.apte4ka.domain.entity.aidkit.AidKit
import com.tisbus.apte4ka.domain.entity.packaging.Packaging
import com.tisbus.apte4ka.domain.entity.symptom.Symptom
import com.tisbus.apte4ka.presentation.AidKitApp
import com.tisbus.apte4ka.presentation.adapter.listaidkit.ListAidKitAdapter
import com.tisbus.apte4ka.presentation.adapter.packaging.PackagingAdapter
import com.tisbus.apte4ka.presentation.adapter.symptom.SymptomAdapter
import com.tisbus.apte4ka.presentation.viewmodel.aidkit.AidKitViewModel
import com.tisbus.apte4ka.presentation.viewmodel.factory.AidKitViewModelFactory
import com.tisbus.apte4ka.presentation.viewmodel.lists.ListsViewModel
import com.tisbus.apte4ka.presentation.viewmodel.preparation.PreparationViewModel
import com.squareup.picasso.Picasso
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class PreparationEditFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: AidKitViewModelFactory
    private val component by lazy {
        (requireActivity().application as AidKitApp).component
    }
    private var _bind: FragmentPreparationEditBinding? = null
    private val bind: FragmentPreparationEditBinding
        get() = _bind ?: throw RuntimeException("FragmentPreparationEditBinding == null")

    private lateinit var adapterListAidKit: ListAidKitAdapter
    private lateinit var adapterSymptom: SymptomAdapter
    private lateinit var adapterPackaging: PackagingAdapter

    private lateinit var viewModelPrep: PreparationViewModel
    private lateinit var aidKitModel: AidKitViewModel
    private lateinit var listsModel: ListsViewModel

    private var urlImg: Uri? = null
    private var imageUrl: String = ""

    private var _aidId: Int? = null
    private val aidId: Int
        get() = _aidId ?: throw RuntimeException("aidId == null")

    private var currentDate: String = ""
    private var expDate: String = ""
    private var namePackaging: String = ""
    private var idPrep: Int? = null

    private var listAidKit: MutableList<AidKit> = mutableListOf()
    private var listSymptoms: List<Symptom> = listOf()
    private var listPackaging: List<Packaging> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        parseArgs()
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

    private fun parseArgs() {
        arguments?.let {
            idPrep = it.getInt(DETAIL_PREP_ID)
            _aidId = it.getInt(AID_KIT_ID)
        }
        Log.i("Prep", idPrep.toString() + " " + _aidId.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _bind = FragmentPreparationEditBinding.inflate(inflater, container, false)
        setupBackButton()
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelPrep = ViewModelProvider(this, viewModelFactory)[PreparationViewModel::class.java]
        aidKitModel = ViewModelProvider(this, viewModelFactory)[AidKitViewModel::class.java]
        listsModel = ViewModelProvider(this, viewModelFactory)[ListsViewModel::class.java]
        recyclerSetupPackaging()
        recyclerSetupSymptom()
        setupSetDataLayout()
        aidKitModel.listAidKit.observe(viewLifecycleOwner) {
            listAidKit = it
            recyclerSetup()
            listAidKit[aidId - 1].status = true
            adapterListAidKit.itemSelect = {
                _aidId = it.id
            }
        }
        selectPackaging()
        selectSymptoms()
        setupSetImages()
        setupDate()
        editPrep()
    }

    private fun selectPackaging() {
        adapterPackaging.itemSelect = {
            it.status = true
            namePackaging = it.name
        }
    }

    private fun selectSymptoms() {
        adapterSymptom.itemSelect = {
            it.status = !it.status
        }
    }

    private fun recyclerSetupPackaging(): RecyclerView {
        val recyclerPackaging = bind.rPackagePreparation
        listPackaging = listsModel.listPackaging
        adapterPackaging = PackagingAdapter(listPackaging)
        with(recyclerPackaging) {
            adapter = adapterPackaging
        }
        return recyclerPackaging
    }

    private fun recyclerSetupSymptom(): RecyclerView {
        val recyclerSymptoms = bind.rSetSymptoms
        listSymptoms = listsModel.listSymptom
        adapterSymptom = SymptomAdapter(listSymptoms)
        with(recyclerSymptoms) {
            adapter = adapterSymptom
        }
        return recyclerSymptoms
    }

    private fun editPrep() {
        bind.bEditPreparation.setOnClickListener {
            with(bind) {
                val name = etNamePreparation.text.toString()
                imageUrl = urlImg.toString()
                val symptom: MutableList<Symptom> = mutableListOf()
                listSymptoms.forEach {
                    if (it.status) {
                        symptom.add(it)
                    }
                }
                val packing = namePackaging
                val description = etDescriptionPreparation.text.toString()
                val dateCreate = currentDate
                val dateExp = expDate
                viewModelPrep.editPreparationItem(
                    aidId,
                    name,
                    imageUrl,
                    symptom,
                    packing,
                    description,
                    dateCreate,
                    dateExp)
            }
            val bundle = bundleOf(AID_KIT_ID to aidId)
            findNavController().navigate(R.id.action_preparationEditFragment_to_aidKitDetailFragment,
                bundle)
        }
    }

    private fun setupDate() {
        fun getDate(year: Int, month: Int, day: Int): String {
            val dateFormat = SimpleDateFormat("dd.MM.yyyy")
            val calendar = Calendar.getInstance()
            calendar.set(year, month, day)
            return dateFormat.format(calendar.time)
        }
        bind.cvDateCreate.setOnDateChangeListener {
                calendarView,
                year,
                month,
                day,
            ->
            currentDate = getDate(year, month, day)
        }

        bind.cvDateExp.setOnDateChangeListener {
                calendarView,
                year,
                month,
                day,
            ->
            expDate = getDate(year, month, day)
        }
    }

    private fun setupSetImages() {
        val takeImages =
            registerForActivityResult(ActivityResultContracts.TakePicture()) { success: Boolean ->
                if (success) {
                    Picasso.get().load(urlImg).rotate(90F).into(bind.ivAddPhotoPreparation)
                }
            }

        fun takeImages() {
            val pathImg = File(requireActivity().externalMediaDirs.first(), "Apte4ka")
            pathImg.mkdirs()
            val fileName = "img_" + System.currentTimeMillis() + ".jpg"
            val sdImageDirectoryPath = File(pathImg, fileName)
            urlImg = FileProvider.getUriForFile(
                requireContext(),
                "com.example.apte4ka",
                sdImageDirectoryPath
            )
            takeImages.launch(urlImg)
        }
        bind.ivAddPhotoPreparation.setOnClickListener {
            takeImages()
        }
    }

    private fun recyclerSetup(): RecyclerView {
        val recyclerView = bind.recyclerListSetAidKit
        with(recyclerView) {
            adapterListAidKit = ListAidKitAdapter(listAidKit)
            adapter = adapterListAidKit
        }
        return recyclerView
    }

    private fun setupSetDataLayout() {
        with(bind) {
            editPrep = viewModelPrep
            lifecycleOwner = viewLifecycleOwner
        }
        idPrep?.let {
            viewModelPrep.getPreparationItem(it)
            imageUrl = viewModelPrep.prepLD.value?.image.toString()
            urlImg = Uri.parse(imageUrl)
            getDataPackaging()
            getDataSymptom()
            currentDate = viewModelPrep.prepLD.value?.dataCreate.toString()
            expDate = viewModelPrep.prepLD.value?.dateExp.toString()
        }
    }

    private fun getDataSymptom() {
        viewModelPrep.prepLD.value?.symptoms?.forEach {
            for (i in listSymptoms) {
                if (it.name.contains(i.name)) {
                    i.status = true
                    adapterSymptom.notifyDataSetChanged()
                }
            }
        }
    }

    private fun getDataPackaging() {
        namePackaging = viewModelPrep.prepLD.value?.packaging.toString()
        listPackaging.forEach {
            it.status = it.name == namePackaging
        }
        adapterPackaging.notifyDataSetChanged()
    }

    companion object {
        const val AID_KIT_ID = "aid_id"
        const val DETAIL_PREP_ID = "detail_prep_id"
    }
}