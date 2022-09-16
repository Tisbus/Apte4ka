package com.tisbus.apte4ka.presentation.fragment.pagemenu.restore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.tisbus.apte4ka.R
import com.tisbus.apte4ka.databinding.FragmentBackupBinding
import com.tisbus.apte4ka.databinding.FragmentRestoreBinding

class RestoreFragment : Fragment() {

    private var _bind : FragmentRestoreBinding? = null
    private val bind : FragmentRestoreBinding
        get() = _bind ?: throw RuntimeException("FragmentRestoreBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _bind = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_restore,
            container,
            false
        )
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.bRestoreDB.setOnClickListener {

        }
    }

    companion object {

    }
}