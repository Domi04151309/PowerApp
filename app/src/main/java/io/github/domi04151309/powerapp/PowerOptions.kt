package io.github.domi04151309.powerapp

import android.app.AlertDialog
import android.content.Context
import android.util.Log

class PowerOptions(context: Context) {

    private val c = context

    private fun shell(command: String) {
        try {
            val p = Runtime.getRuntime()
                    .exec(arrayOf("su", "-c", command))
            p.waitFor()
        } catch (ex: Exception) {
            AlertDialog.Builder(c).setTitle(c.resources.getString(R.string.action_failed))
                    .setMessage(c.resources.getString(R.string.action_failed_summary))
                    .setPositiveButton(c.resources.getString(android.R.string.ok)) { _, _ -> }
                    .show()
            Log.e("Superuser", ex.toString())
        }

    }

    fun shutdown() {
        shell("reboot -p")
    }

    fun reboot() {
        shell("reboot")
    }

    fun rebootIntoRecovery() {
        shell("reboot recovery")
    }

    fun rebootIntoBootloader() {
        shell("reboot bootloader")
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