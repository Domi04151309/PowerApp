package io.github.domi04151309.powerapp.activities.shortcuts

import io.github.domi04151309.powerapp.R
import io.github.domi04151309.powerapp.helpers.PowerOptions

class EDLActivity : ShortcutActivity() {
    override fun getShortcutName(): String = resources.getString(R.string.reboot_into_edl)

    override fun onOpened() {
        PowerOptions(this).rebootIntoEDL()
    }
}
