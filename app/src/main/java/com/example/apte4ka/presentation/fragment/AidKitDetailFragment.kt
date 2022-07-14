package com.example.apte4ka.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.apte4ka.databinding.FragmentAidKitDetailBinding

class AidKitDetailFragment : Fragment() {

    private var aidKitId: Int? = null

    private var _bind : FragmentAidKitDetailBinding? = null
    private val bind : FragmentAidKitDetailBinding
    get() = _bind ?: throw RuntimeException("FragmentAidKitDetailBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        parseArgs()
        super.onCreate(savedInstanceState)
    }

    private fun parseArgs() {
        aidKitId = arguments?.getInt(AID_KIT_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _bind = FragmentAidKitDetailBinding.inflate(layoutInflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("check", aidKitId.toString())
    }

    companion object{
        const val AID_KIT_ID = "aid_id"
    }
}