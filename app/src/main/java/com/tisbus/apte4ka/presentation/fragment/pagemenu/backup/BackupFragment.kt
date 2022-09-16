package com.tisbus.apte4ka.presentation.fragment.pagemenu.backup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.tisbus.apte4ka.R
import com.tisbus.apte4ka.databinding.FragmentBackupBinding

class BackupFragment : Fragment() {

    private var _bind: FragmentBackupBinding? = null
    private val bind: FragmentBackupBinding
        get() = _bind ?: throw RuntimeException("FragmentBackupBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _bind = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_backup,
            container,
            false
        )
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
/*        val backup = RoomBackup(bind.root.context)
        bind.bSaveDB.setOnClickListener {
            backup.backupLocation(RoomBackup.BACKUP_FILE_LOCATION_CUSTOM_FILE)
                .backupLocationCustomFile(File("${bind.root.context.filesDir}/databasebackup/aptBackup.sqlite3"))
                .database(PreparationDatabase.getInstance(requireActivity().application))
                .enableLogDebug(true)
                .backupIsEncrypted(false)
                .maxFileCount(5).apply {
                    onCompleteListener { success, message, exitCode ->
                        Log.d(TAG, "success: $success, message: $message, exitCode: $exitCode")
                        Toast.makeText(
                            bind.root.context,
                            "success: $success, message: $message, exitCode: $exitCode",
                            Toast.LENGTH_LONG
                        ).show()
                        if (success) restartApp(Intent(bind.root.context,
                            BackupFragment::class.java))
                    }
                }.backup()
        }*/
    }

    companion object {
        const val TAG = "Apte4ka_Backup"
    }
}