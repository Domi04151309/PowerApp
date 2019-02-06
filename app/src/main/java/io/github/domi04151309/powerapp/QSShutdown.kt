package io.github.domi04151309.powerapp

import android.annotation.TargetApi
import android.os.Build
import android.service.quicksettings.TileService

@TargetApi(Build.VERSION_CODES.N)
class QSShutdown : TileService() {

    override fun onClick() {
        val isCurrentlyLocked = isLocked
        if (!isCurrentlyLocked) {
            Root(this).shell("reboot -p")
        }
    }
}