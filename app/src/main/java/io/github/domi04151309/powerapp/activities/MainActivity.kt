package io.github.domi04151309.powerapp.activities

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.preference.PreferenceManager
import io.github.domi04151309.powerapp.helpers.PowerOptions
import io.github.domi04151309.powerapp.R
import io.github.domi04151309.powerapp.helpers.P
import io.github.domi04151309.powerapp.helpers.Theme

import java.io.DataOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private var themeId = ""
    private fun getThemeId(): String =
            PreferenceManager.getDefaultSharedPreferences(this)
                    .getString(P.PREF_THEME, P.PREF_THEME_DEFAULT) ?: P.PREF_THEME_DEFAULT

    override fun onCreate(savedInstanceState: Bundle?) {
        Theme.set(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar = supportActionBar ?: return
        actionBar.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        actionBar.setDisplayShowCustomEnabled(true)
        actionBar.setCustomView(R.layout.action_bar)
        actionBar.elevation = 0f
        val scrollView = findViewById<View>(R.id.scrollView)
        scrollView.viewTreeObserver.addOnScrollChangedListener {
            if (scrollView.scrollY > 0)
                actionBar.elevation = 16f
            else
                actionBar.elevation = 0f
        }

        val po = PowerOptions(this, true)

        findViewById<View>(R.id.shutdown).setOnClickListener {
            askBefore { po.shutdown() }
        }
        findViewById<View>(R.id.reboot).setOnClickListener {
            askBefore { po.reboot() }
        }
        findViewById<View>(R.id.safemode).setOnClickListener {
            askBefore { po.rebootIntoSafeMode() }
        }
        findViewById<View>(R.id.recovery).setOnClickListener {
            askBefore { po.rebootIntoRecovery() }
        }
        findViewById<View>(R.id.bootloader).setOnClickListener {
            askBefore { po.rebootIntoBootloader() }
        }
        findViewById<View>(R.id.edl).setOnClickListener {
            askBefore { po.rebootIntoEDL() }
        }
        findViewById<View>(R.id.soft_reboot).setOnClickListener {
            askBefore { po.softReboot() }
        }
        findViewById<View>(R.id.system_ui).setOnClickListener {
            askBefore { po.restartSystemUI() }
        }
        findViewById<View>(R.id.screen_off).setOnClickListener {
            askBefore { po.turnOffScreen() }
        }
        findViewById<View>(R.id.root).setOnClickListener {
            val p: Process
            try {
                p = Runtime.getRuntime().exec("su")
                val os = DataOutputStream(p.outputStream)
                os.writeBytes("echo access granted\n")
                os.writeBytes("exit\n")
                os.flush()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        findViewById<View>(R.id.prefBtn).setOnClickListener { startActivity(Intent(this@MainActivity, SettingsActivity::class.java)) }

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
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("confirm_dialog", true)) {
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
