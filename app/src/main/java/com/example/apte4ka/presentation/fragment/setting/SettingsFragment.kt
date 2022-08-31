package com.example.apte4ka.presentation.fragment.setting

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.apte4ka.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}