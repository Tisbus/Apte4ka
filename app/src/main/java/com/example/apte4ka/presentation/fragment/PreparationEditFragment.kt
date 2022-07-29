package com.example.apte4ka.presentation.fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.apte4ka.R
import com.example.apte4ka.databinding.FragmentPreparationEditBinding
import com.example.apte4ka.presentation.adapter.listaidkit.ListAidKitAdapter
import com.example.apte4ka.presentation.viewmodel.aidkit.AidKitViewModel
import com.example.apte4ka.presentation.viewmodel.preparation.PreparationViewModel
import com.squareup.picasso.Picasso
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class PreparationEditFragment : Fragment() {

    private var _bind: FragmentPreparationEditBinding? = null
    private val bind: FragmentPreparationEditBinding
        get() = _bind ?: throw RuntimeException("FragmentPreparationEditBinding == null")

    private lateinit var adapterListAidKit: ListAidKitAdapter
    private lateinit var viewModelPrep: PreparationViewModel
    private lateinit var aidKitModel: AidKitViewModel
    private var urlImg: Uri? = null
    private var imageUrl: String = ""

    private var _aidId: Int? = null
    private val aidId: Int
        get() = _aidId ?: throw RuntimeException("aidId == null")

    private var currentDate: String = ""
    private var expDate: String = ""
    private var idPrep: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        parseArgs()
        super.onCreate(savedInstanceState)
    }

    private fun parseArgs() {
        arguments?.let {
            idPrep = it.getInt(AID_KIT_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _bind = FragmentPreparationEditBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelPrep = ViewModelProvider(this)[PreparationViewModel::class.java]
        aidKitModel = ViewModelProvider(this)[AidKitViewModel::class.java]
        setupSetDataLayout()
        aidKitModel.listAidKit.observe(viewLifecycleOwner){
            adapterListAidKit.submitList(it)
            adapterListAidKit.itemSelect = {
                _aidId = it.id
            }
        }
        recyclerSetup()
        setupSetImages()
        setupDate()
        editPrep()
    }

    private fun editPrep() {
        bind.bEditPreparation.setOnClickListener {
            with(bind) {
                val name = etNamePreparation.text.toString()
                imageUrl = urlImg.toString()
                val symptoms = etAddSymptomsPreparation.text.toString()
                val packing = etPackagePreparation.text.toString()
                val description = etDescriptionPreparation.text.toString()
                val dateCreate = currentDate
                val dateExp = expDate
                viewModelPrep.editPreparationItem(
                    aidId,
                    name,
                    imageUrl,
                    symptoms,
                    packing,
                    description,
                    dateCreate,
                    dateExp)
            }
            val bundle = bundleOf(AID_KIT_ID to idPrep)
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
                    Picasso.get().load(urlImg).into(bind.ivAddPhotoPreparation)
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
            adapterListAidKit = ListAidKitAdapter()
            adapter = adapterListAidKit
        }
        return recyclerView
    }

    private fun setupSetDataLayout() {
        with(bind){
            editPrep = viewModelPrep
            lifecycleOwner = viewLifecycleOwner
        }
        idPrep?.let{
            viewModelPrep.getPreparationItem(it)
            _aidId = it
            imageUrl = viewModelPrep.prepLD.value?.image.toString()
            urlImg = Uri.parse(imageUrl)
            currentDate = viewModelPrep.prepLD.value?.dataCreate.toString()
            expDate = viewModelPrep.prepLD.value?.dateExp.toString()
        }
    }

    companion object {
        const val AID_KIT_ID = "aid_id"
    }
}