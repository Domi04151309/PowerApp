package io.github.domi04151309.powerapp.shortcuts

import io.github.domi04151309.powerapp.PowerOptions
import io.github.domi04151309.powerapp.R

class ScreenOffActivity : ShortcutActivity() {

    override fun getShortcutName(): String {
        return resources.getString(R.string.ScreenOff)
    }

    override fun onOpened() {
        PowerOptions(this).turnOffScreen()
    }
}
