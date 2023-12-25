package io.github.domi04151309.powerapp.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import io.github.domi04151309.powerapp.R
import io.github.domi04151309.powerapp.helpers.P
import io.github.domi04151309.powerapp.helpers.PowerOptions
import io.github.domi04151309.powerapp.helpers.Theme
import java.io.DataOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {
    companion object {
        private const val ACTION_BAR_ELEVATION = 16f
    }

    private var themeId = ""

    private fun getThemeId(): String =
        PreferenceManager.getDefaultSharedPreferences(this)
            .getString(P.PREF_THEME, P.PREF_THEME_DEFAULT) ?: P.PREF_THEME_DEFAULT

    private fun setupActionBar() {
        val actionBar = supportActionBar ?: return
        actionBar.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        actionBar.setDisplayShowCustomEnabled(true)
        actionBar.setCustomView(R.layout.action_bar)
        actionBar.elevation = 0f
        val scrollView = findViewById<View>(R.id.scrollView)
        scrollView.viewTreeObserver.addOnScrollChangedListener {
            if (scrollView.scrollY > 0) {
                actionBar.elevation = ACTION_BAR_ELEVATION
            } else {
                actionBar.elevation = 0f
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Theme.set(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupActionBar()

        val powerOptions = PowerOptions(this, true)

        findViewById<View>(R.id.shutdown).setOnClickListener {
            askBefore { powerOptions.shutdown() }
        }
        findViewById<View>(R.id.reboot).setOnClickListener {
            askBefore { powerOptions.reboot() }
        }
        findViewById<View>(R.id.safemode).setOnClickListener {
            askBefore { powerOptions.rebootIntoSafeMode() }
        }
        findViewById<View>(R.id.recovery).setOnClickListener {
            askBefore { powerOptions.rebootIntoRecovery() }
        }
        findViewById<View>(R.id.bootloader).setOnClickListener {
            askBefore { powerOptions.rebootIntoBootloader() }
        }
        findViewById<View>(R.id.edl).setOnClickListener {
            askBefore { powerOptions.rebootIntoEDL() }
        }
        findViewById<View>(R.id.soft_reboot).setOnClickListener {
            askBefore { powerOptions.softReboot() }
        }
        findViewById<View>(R.id.system_ui).setOnClickListener {
            askBefore { powerOptions.restartSystemUI() }
        }
        findViewById<View>(R.id.screen_off).setOnClickListener {
            askBefore { powerOptions.turnOffScreen() }
        }
        findViewById<View>(R.id.root).setOnClickListener {
            val process: Process
            try {
                process = Runtime.getRuntime().exec("su")
                val output = DataOutputStream(process.outputStream)
                output.writeBytes("echo access granted\n")
                output.writeBytes("exit\n")
                output.flush()
            } catch (exception: IOException) {
                exception.printStackTrace()
            }
        }
        findViewById<View>(R.id.prefBtn).setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    SettingsActivity::class.java,
                ),
            )
        }

        themeId = getThemeId()
    }

    override fun onStart() {
        super.onStart()

        if (getThemeId() != themeId) {
            themeId = getThemeId()
            recreate()
        }
    }

    private fun askBefore(function: () -> Unit) {
        if (PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("confirm_dialog", true)
        ) {
            AlertDialog.Builder(this)
                .setTitle(R.string.confirm_dialog)
                .setMessage(R.string.confirm_dialog_summary)
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    function()
                }
                .setNegativeButton(android.R.string.cancel) { _, _ -> }
                .show()
        } else {
            function()
        }
    }
}
