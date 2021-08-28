package io.github.domi04151309.powerapp.helpers

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
import io.github.domi04151309.powerapp.R

class PowerOptions(private val context: Context, private val dialog: Boolean = false) {

    private fun shell(command: String) {
        try {
            Runtime.getRuntime().exec(arrayOf("su", "-c", command)).waitFor()
        } catch (e: Exception) {
            if (dialog) {
                AlertDialog.Builder(context)
                        .setTitle(R.string.action_failed)
                        .setMessage(R.string.action_failed_summary)
                        .setPositiveButton(android.R.string.ok) { _, _ -> }
                        .show()
            } else {
                Toast.makeText(context, R.string.action_failed_summary, Toast.LENGTH_LONG).show()
            }
            Log.e("Superuser", e.toString())
        }

    }

    fun shutdown() {
        shell("reboot -p")
    }

    fun reboot() {
        shell("reboot")
    }

    fun safemode() {
        shell("setprop persist.sys.safemode 1")
        shell("reboot")
    }

    fun rebootIntoRecovery() {
        shell("reboot recovery")
    }

    fun rebootIntoBootloader() {
        shell("reboot bootloader")
    }

    fun rebootIntoEDL() {
        shell("reboot edl")
    }

    fun softReboot() {
        shell("setprop ctl.restart zygote")
    }

    fun restartSystemUI() {
        shell("killall com.android.systemui")
    }

    fun turnOffScreen() {
        shell("input keyevent KEYCODE_POWER")
    }
}