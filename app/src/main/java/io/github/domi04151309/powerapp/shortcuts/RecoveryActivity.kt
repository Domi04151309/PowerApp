package io.github.domi04151309.powerapp.shortcuts

import io.github.domi04151309.powerapp.PowerOptions
import io.github.domi04151309.powerapp.R

class RecoveryActivity : ShortcutActivity() {

    override fun getShortcutName(): String {
        return resources.getString(R.string.Recovery)
    }

    override fun onOpened() {
        PowerOptions(this).rebootIntoRecovery()
    }
}
