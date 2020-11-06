package io.github.domi04151309.powerapp

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.service.quicksettings.TileService
import io.github.domi04151309.powerapp.qs.Reboot
import io.github.domi04151309.powerapp.qs.RestartSystemUI
import io.github.domi04151309.powerapp.qs.Shutdown

class OnBootCompleted : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED && Build.VERSION.SDK_INT >= 24) {
            TileService.requestListeningState(context, ComponentName(context, Shutdown::class.java))
            TileService.requestListeningState(context, ComponentName(context, Reboot::class.java))
            TileService.requestListeningState(context, ComponentName(context, RestartSystemUI::class.java))
        }
    }
}