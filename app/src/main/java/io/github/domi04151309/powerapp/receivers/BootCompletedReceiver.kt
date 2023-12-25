package io.github.domi04151309.powerapp.receivers

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.service.quicksettings.TileService
import io.github.domi04151309.powerapp.services.RebootTileService
import io.github.domi04151309.powerapp.services.RestartSystemUITileService
import io.github.domi04151309.powerapp.services.ShutdownTileService

class BootCompletedReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            TileService.requestListeningState(
                context,
                ComponentName(context, ShutdownTileService::class.java),
            )
            TileService.requestListeningState(
                context,
                ComponentName(context, RebootTileService::class.java),
            )
            TileService.requestListeningState(
                context,
                ComponentName(context, RestartSystemUITileService::class.java),
            )
        }
    }
}
