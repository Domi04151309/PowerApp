package io.github.domi04151309.powerapp.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import io.github.domi04151309.powerapp.R
import io.github.domi04151309.powerapp.helpers.P
import io.github.domi04151309.powerapp.helpers.Theme

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Theme.set(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {

        private val prefsChangedListener =
            SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
                if (key == P.PREF_THEME) requireActivity().recreate()
            }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            preferenceManager.sharedPreferences?.registerOnSharedPreferenceChangeListener(
                prefsChangedListener
            )
        }

        override fun onDestroy() {
            super.onDestroy()
            preferenceManager.sharedPreferences?.unregisterOnSharedPreferenceChangeListener(
                prefsChangedListener
            )
        }

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            addPreferencesFromResource(R.xml.pref_general)
            findPreference<Preference>("about")?.onPreferenceClickListener =
                Preference.OnPreferenceClickListener {
                    startActivity(Intent(context, AboutActivity::class.java))
                    true
                }
        }
    }
}
