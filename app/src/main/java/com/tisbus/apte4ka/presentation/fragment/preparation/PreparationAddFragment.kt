package com.tisbus.apte4ka.presentation.fragment.preparation

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tisbus.apte4ka.R
import com.tisbus.apte4ka.databinding.FragmentPreparationAddBinding
import com.tisbus.apte4ka.domain.entity.aidkit.AidKit
import com.tisbus.apte4ka.domain.entity.packaging.Packaging
import com.tisbus.apte4ka.domain.entity.symptom.Symptom
import com.tisbus.apte4ka.presentation.AidKitApp
import com.tisbus.apte4ka.presentation.adapter.listaidkit.ListAidKitAdapter
import com.tisbus.apte4ka.presentation.adapter.packaging.prep.PackagingAdapter
import com.tisbus.apte4ka.presentation.adapter.symptom.prep.SymptomAdapter
import com.tisbus.apte4ka.presentation.viewmodel.aidkit.AidKitViewModel
import com.tisbus.apte4ka.presentation.viewmodel.factory.AidKitViewModelFactory
import com.tisbus.apte4ka.presentation.viewmodel.lists.ListsViewModel
import com.tisbus.apte4ka.presentation.viewmodel.preparation.PreparationViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class PreparationAddFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: AidKitViewModelFactory
    private val component by lazy {
        (requireActivity().application as AidKitApp).component
    }

    private var _bind: FragmentPreparationAddBinding? = null
    private val bind: FragmentPreparationAddBinding
        get() = _bind ?: throw RuntimeException("FragmentPreparationAddBinding == null")

    private lateinit var adapterListAidKit: ListAidKitAdapter
    private lateinit var adapterSymptom: SymptomAdapter
    private lateinit var adapterPackaging: PackagingAdapter

    private lateinit var aidKitModel: AidKitViewModel

    private lateinit var prepModel: PreparationViewModel
    private lateinit var listsModel: ListsViewModel

    private var _aidId: Int? = null
    private val aidId: Int
        get() = _aidId ?: throw RuntimeException("aidId == null")

    private var _aidName: String? = null
    private val aidName: String
        get() = _aidName ?: throw RuntimeException("_aidName == null")

    private var urlImg: Uri? = null

    private var currentDate: String = ""
    private var expDate: String = ""
    private var namePackaging: String = ""

    private var listAidKit: MutableList<AidKit> = mutableListOf()
    private var listSymptoms: MutableList<Symptom> = mutableListOf()
    private var listPackaging: MutableList<Packaging> = mutableListOf()

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
        _bind = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_preparation_add,
            container,
            false
        )
        setupBackButton()
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        aidKitModel = ViewModelProvider(this, viewModelFactory)[AidKitViewModel::class.java]
        prepModel = ViewModelProvider(this, viewModelFactory)[PreparationViewModel::class.java]
        listsModel = ViewModelProvider(this, viewModelFactory)[ListsViewModel::class.java]
        aidKitModel.listAidKit.observe(viewLifecycleOwner) {
            listAidKit = it
            recyclerSetup()
            adapterListAidKit.itemSelect = { item ->
                _aidId = item.id
                _aidName = item.name
                Log.i("itemId", aidId.toString())
            }
        }
        listsModel.listPackaging.observe(viewLifecycleOwner) {
            listPackaging = it
            recyclerSetupPackaging()
            selectPackaging()
        }
        listsModel.listSymptom.observe(viewLifecycleOwner) {
            listSymptoms = it
            recyclerSetupSymptom()
            selectSymptoms()
        }
        setupLifeCycle()
        checkErrorListener()
        addNewPreparation()
        setupSetImages()
        setupDate()
    }

    //need for work update status error
    private fun setupLifeCycle() {
        bind.editPrep = prepModel
        bind.lifecycleOwner = viewLifecycleOwner
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
        adapterPackaging = PackagingAdapter(listPackaging)
        with(recyclerPackaging) {
            adapter = adapterPackaging
        }
        return recyclerPackaging
    }

    private fun recyclerSetupSymptom(): RecyclerView {
        val recyclerSymptoms = bind.rSetSymptoms
        adapterSymptom = SymptomAdapter(listSymptoms)
        with(recyclerSymptoms) {
            adapter = adapterSymptom
        }
        return recyclerSymptoms
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
        fun getCurDate(): String {
            val dateFormat = SimpleDateFormat("dd.MM.yyyy")
            val calendar = Calendar.getInstance().time
            return dateFormat.format(calendar)
        }
        if (currentDate.isEmpty()) {
            currentDate = getCurDate()
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

    private fun checkErrorListener() {
        with(bind) {
            etNamePreparation.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    prepModel.resetCheckNameError()
                }

                override fun afterTextChanged(p0: Editable?) {

                }
            }
            )
        }
    }

    private fun setupSetImages() {
        val takeImages =
            registerForActivityResult(ActivityResultContracts.TakePicture()) { success: Boolean ->
                if (success) {
                    Glide.with(this@PreparationAddFragment).load(urlImg).override(
                        300,
                        300
                    ).error(R.drawable.ic_add_a_photo_50).into(bind.ivAddPhotoPreparation)
/*                    Picasso.get().load(urlImg).into(bind.ivAddPhotoPreparation)
                    Picasso.get().load(urlImg).rotate(90F).into(bind.ivAddPhotoPreparation)*/
                }
            }

        fun takeImages() {
            val pathImg = File(requireActivity().externalMediaDirs.first(), "Apte4ka")
            pathImg.mkdirs()
            val fileName = "img_" + System.currentTimeMillis() + ".jpg"
            val sdImageDirectoryPath = File(pathImg, fileName)
            urlImg = FileProvider.getUriForFile(
                requireContext(),
                "com.tisbus.apte4ka",
                sdImageDirectoryPath
            )
            takeImages.launch(urlImg)
        }
        bind.ivAddPhotoPreparation.setOnClickListener {
            takeImages()
        }
    }

    private fun addNewPreparation() {
        with(bind) {
            bAddPreparation.setOnClickListener {
                val name = etNamePreparation.text.toString()
                val image = urlImg.toString()
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
                prepModel.addPreparationItem(aidId,
                    name,
                    image,
                    symptom,
                    packing,
                    description,
                    dateCreate,
                    dateExp)
                if (checkError(name)) {
                    val bundle = bundleOf(AID_KIT_ID to aidId, AID_KIT_NAME to aidName)
                    findNavController().navigate(R.id.action_preparationAddFragment_to_aidKitDetailFragment2,
                        bundle)
                }
            }
        }
    }

    private fun checkError(name: String): Boolean {
        return name.isNotBlank()
    }

    private fun recyclerSetup(): RecyclerView {
        val recyclerView = bind.recyclerListSetAidKit
        with(recyclerView) {
            adapterListAidKit = ListAidKitAdapter(listAidKit)
            adapter = adapterListAidKit
        }
        return recyclerView
    }

    companion object {
        const val AID_KIT_ID = "aid_id"
        const val AID_KIT_NAME = "aid_name"
    }
}