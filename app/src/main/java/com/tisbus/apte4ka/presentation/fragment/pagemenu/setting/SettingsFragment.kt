package com.tisbus.apte4ka.presentation.fragment.pagemenu.setting

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.tisbus.apte4ka.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}