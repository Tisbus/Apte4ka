package com.example.apte4ka.presentation.fragment

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.apte4ka.R
import com.example.apte4ka.databinding.FragmentPreparationAddBinding
import com.example.apte4ka.presentation.adapter.listaidkit.ListAidKitAdapter
import com.example.apte4ka.presentation.viewmodel.aidkit.AidKitViewModel
import com.example.apte4ka.presentation.viewmodel.preparation.PreparationViewModel
import com.squareup.picasso.Picasso
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class PreparationAddFragment : Fragment() {

    private var aidKitId: Int? = null

    private var _bind: FragmentPreparationAddBinding? = null
    private val bind: FragmentPreparationAddBinding
        get() = _bind ?: throw RuntimeException("FragmentPreparationAddBinding == null")

    private lateinit var adapterListAidKit: ListAidKitAdapter

    private lateinit var aidKitModel: AidKitViewModel

    private lateinit var prepModel: PreparationViewModel

    private var _aidId: Int? = null
    private val aidId: Int
        get() = _aidId ?: throw RuntimeException("aidId == null")

    private var urlImg: Uri? = null

    private var currentDate: String = ""
    private var expDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        parseArgs()
        super.onCreate(savedInstanceState)
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
        _bind = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_preparation_add,
            container,
            false
        )
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        aidKitModel = ViewModelProvider(this)[AidKitViewModel::class.java]
        prepModel = ViewModelProvider(this)[PreparationViewModel::class.java]
        aidKitModel.listAidKit.observe(viewLifecycleOwner) {
            adapterListAidKit.submitList(it)
            adapterListAidKit.itemSelect = {
                _aidId = it.id
                Log.i("itemId", aidId.toString())
            }
        }
        recyclerSetup()
        addNewPreparation()
        setupSetImages()
        setupDate()
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

    private fun addNewPreparation() {
        bind.bAddPreparation.setOnClickListener {
            with(bind) {
                val name = etNamePreparation.text.toString()
                val image = urlImg.toString()
                val symptoms = etAddSymptomsPreparation.text.toString()
                val packing = etPackagePreparation.text.toString()
                val description = etDescriptionPreparation.text.toString()
                val dateCreate = currentDate
                val dateExp = expDate
                prepModel.addPreparationItem(aidId,
                    name,
                    image,
                    symptoms,
                    packing,
                    description,
                    dateCreate,
                    dateExp)
            }
            findNavController().navigate(R.id.action_preparationAddFragment_to_aidKitDetailFragment2)
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

    companion object {
        const val AID_KIT_ID = "aid_id"
    }
}