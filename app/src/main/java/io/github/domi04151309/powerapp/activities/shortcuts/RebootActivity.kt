package io.github.domi04151309.powerapp.activities.shortcuts

import io.github.domi04151309.powerapp.helpers.PowerOptions
import io.github.domi04151309.powerapp.R

class RebootActivity : ShortcutActivity() {

    override fun getShortcutName(): String {
        return resources.getString(R.string.Reboot)
    }

    override fun onOpened() {
        PowerOptions(this).reboot()
    }
}
