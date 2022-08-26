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
    private val items: Map<String, Int> = mapOf(
        Pair("shutdown", R.string.Shutdown),
        Pair("reboot", R.string.Reboot),
        Pair("safe_mode", R.string.SafeMode),
        Pair("recovery", R.string.Recovery),
        Pair("bootloader", R.string.Bootloader),
        Pair("edl", R.string.EDL),
        Pair("soft_reboot", R.string.SoftReboot),
        Pair("restart_system_ui", R.string.SystemUI),
        Pair("screen_off", R.string.ScreenOff)
    )

    override fun createPublisherForAllAvailable(): Flow.Publisher<Control> {
        return Flow.Publisher { subscriber ->
            updateSubscriber = subscriber

            val pi = PendingIntent.getActivity(
                baseContext, 0, Intent(),
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            for ((key, value) in items) {
                subscriber.onNext(
                    Control.StatelessBuilder(
                        key,
                        pi
                    )
                        .setTitle(resources.getString(value))
                        .setZone(resources.getString(R.string.app_name))
                        .setStructure(resources.getString(R.string.app_name))
                        .setDeviceType(DeviceTypes.TYPE_REMOTE_CONTROL)
                        .build()
                )
            }
            subscriber.onComplete()
        }
    }

    private fun loadStatefulControl(subscriber: Flow.Subscriber<in Control>?, id: String) {
        val pi = PendingIntent.getActivity(
            baseContext, 0, Intent(),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        subscriber?.onNext(
            Control.StatefulBuilder(
                id,
                pi
            )
                .setTitle(resources.getString(items[id] ?: throw IllegalStateException()))
                .setZone(resources.getString(R.string.app_name))
                .setStructure(resources.getString(R.string.app_name))
                .setDeviceType(DeviceTypes.TYPE_REMOTE_CONTROL)
                .setStatus(Control.STATUS_OK).setControlTemplate(
                    StatelessTemplate(id)
                )
                .build()
        )
    }

    override fun createPublisherFor(controlIds: MutableList<String>): Flow.Publisher<Control> {
        return Flow.Publisher { subscriber ->
            updateSubscriber = subscriber
            subscriber.onSubscribe(object : Flow.Subscription {
                override fun request(n: Long) {}
                override fun cancel() {}
            })
            controlIds.forEach { id ->
                loadStatefulControl(subscriber, id)
            }
        }
    }

    override fun performControlAction(
        controlId: String,
        action: ControlAction,
        consumer: Consumer<Int>
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