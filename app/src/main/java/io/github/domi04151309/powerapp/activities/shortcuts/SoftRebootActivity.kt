package io.github.domi04151309.powerapp.activities.shortcuts

import io.github.domi04151309.powerapp.R
import io.github.domi04151309.powerapp.helpers.PowerOptions

class SoftRebootActivity : ShortcutActivity() {
    override fun getShortcutName(): String = resources.getString(R.string.soft_reboot)

    override fun onOpened() {
        PowerOptions(this).softReboot()
    }
}
