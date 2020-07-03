package io.github.domi04151309.powerapp.shortcuts

import io.github.domi04151309.powerapp.PowerOptions

class RestartUIActivity : ShortcutActivity() {

    override fun onOpened() {
        super.onOpened()
        PowerOptions(this).restartSystemUI()
    }
}
