package io.github.domi04151309.powerapp.services

import android.annotation.TargetApi
import android.os.Build
import android.service.quicksettings.TileService
import io.github.domi04151309.powerapp.helpers.PowerOptions

@TargetApi(Build.VERSION_CODES.N)
class RebootTileService : TileService() {
    override fun onClick() {
        unlockAndRun {
            PowerOptions(this).reboot()
        }
    }
}
