package io.github.domi04151309.powerapp.services

import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.service.controls.Control
import android.service.controls.ControlsProviderService
import android.service.controls.DeviceTypes
import android.service.controls.actions.ControlAction
import android.service.controls.templates.StatelessTemplate
import androidx.annotation.RequiresApi
import io.github.domi04151309.powerapp.R
import io.github.domi04151309.powerapp.helpers.PowerOptions
import java.util.concurrent.Flow
import java.util.function.Consumer

@RequiresApi(Build.VERSION_CODES.R)
class ControlService : ControlsProviderService() {
    private var updateSubscriber: Flow.Subscriber<in Control>? = null
    private val items: Map<String, Int> =
        mapOf(
            Pair("shutdown", R.string.shutdown),
            Pair("reboot", R.string.reboot),
            Pair("safe_mode", R.string.reboot_into_safe_mode),
            Pair("recovery", R.string.reboot_into_recovery),
            Pair("bootloader", R.string.reboot_into_bootloader),
            Pair("edl", R.string.reboot_into_edl),
            Pair("soft_reboot", R.string.soft_reboot),
            Pair("restart_system_ui", R.string.restart_system_ui),
            Pair("screen_off", R.string.turn_off_screen),
        )

    override fun createPublisherForAllAvailable(): Flow.Publisher<Control> =
        Flow.Publisher { subscriber ->
            updateSubscriber = subscriber

            val pendingIntent =
                PendingIntent.getActivity(
                    baseContext,
                    0,
                    Intent(),
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
                )

            for ((id, title) in items) {
                subscriber.onNext(
                    Control.StatelessBuilder(
                        id,
                        pendingIntent,
                    )
                        .setTitle(resources.getString(title))
                        .setZone(resources.getString(R.string.app_name))
                        .setStructure(resources.getString(R.string.app_name))
                        .setDeviceType(DeviceTypes.TYPE_REMOTE_CONTROL)
                        .build(),
                )
            }
            subscriber.onComplete()
        }

    private fun loadStatefulControl(
        subscriber: Flow.Subscriber<in Control>?,
        id: String,
    ) {
        val pendingIntent =
            PendingIntent.getActivity(
                baseContext,
                0,
                Intent(),
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
            )

        subscriber?.onNext(
            Control.StatefulBuilder(
                id,
                pendingIntent,
            )
                .setTitle(resources.getString(items[id] ?: error("Impossible state.")))
                .setZone(resources.getString(R.string.app_name))
                .setStructure(resources.getString(R.string.app_name))
                .setDeviceType(DeviceTypes.TYPE_REMOTE_CONTROL)
                .setStatus(Control.STATUS_OK)
                .setControlTemplate(
                    StatelessTemplate(id),
                )
                .build(),
        )
    }

    override fun createPublisherFor(controlIds: MutableList<String>): Flow.Publisher<Control> =
        Flow.Publisher { subscriber ->
            updateSubscriber = subscriber
            subscriber.onSubscribe(
                object : Flow.Subscription {
                    override fun request(n: Long) {
                        // Do nothing.
                    }

                    override fun cancel() {
                        // Do nothing.
                    }
                },
            )
            controlIds.forEach { id ->
                loadStatefulControl(subscriber, id)
            }
        }

    override fun performControlAction(
        controlId: String,
        action: ControlAction,
        consumer: Consumer<Int>,
    ) {
        PowerOptions(this).run {
            when (controlId) {
                "shutdown" -> shutdown()
                "reboot" -> reboot()
                "safe_mode" -> rebootIntoSafeMode()
                "recovery" -> rebootIntoRecovery()
                "bootloader" -> rebootIntoBootloader()
                "edl" -> rebootIntoEDL()
                "soft_reboot" -> softReboot()
                "restart_system_ui" -> restartSystemUI()
                "screen_off" -> turnOffScreen()
            }
        }

        consumer.accept(ControlAction.RESPONSE_OK)
    }
}
