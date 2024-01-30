package io.github.domi04151309.powerapp.activities.shortcuts

import io.github.domi04151309.powerapp.R
import io.github.domi04151309.powerapp.helpers.PowerOptions

class ShutdownActivity : ShortcutActivity() {
    override fun getShortcutName(): String = resources.getString(R.string.shutdown)

    override fun onOpened() {
        PowerOptions(this).shutdown()
    }
}
