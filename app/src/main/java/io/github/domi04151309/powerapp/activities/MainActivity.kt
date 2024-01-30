package io.github.domi04151309.powerapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.elevation.SurfaceColors
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import io.github.domi04151309.powerapp.R
import io.github.domi04151309.powerapp.adapters.SimpleListAdapter
import io.github.domi04151309.powerapp.data.SimpleListItem
import io.github.domi04151309.powerapp.helpers.P
import io.github.domi04151309.powerapp.helpers.PowerOptions
import io.github.domi04151309.powerapp.interfaces.RecyclerViewHelperInterface
import java.io.DataOutputStream
import java.io.IOException

class MainActivity : BaseActivity(), RecyclerViewHelperInterface {
    private fun getListItems() =
        listOf(
            SimpleListItem(
                title = resources.getString(R.string.shutdown),
                icon = R.drawable.ic_round_shutdown,
            ),
            SimpleListItem(
                title = resources.getString(R.string.reboot),
                icon = R.drawable.ic_round_reboot,
            ),
            SimpleListItem(
                title = resources.getString(R.string.reboot_into_safe_mode),
                icon = R.drawable.ic_round_safe_mode,
            ),
            SimpleListItem(
                title = resources.getString(R.string.reboot_into_recovery),
                icon = R.drawable.ic_round_recovery,
            ),
            SimpleListItem(
                title = resources.getString(R.string.reboot_into_bootloader),
                icon = R.drawable.ic_round_bootloader,
            ),
            SimpleListItem(
                title = resources.getString(R.string.reboot_into_edl),
                icon = R.drawable.ic_round_edl,
            ),
            SimpleListItem(
                title = resources.getString(R.string.soft_reboot),
                icon = R.drawable.ic_round_soft_reboot,
            ),
            SimpleListItem(
                title = resources.getString(R.string.restart_system_ui),
                icon = R.drawable.ic_round_restart_system_ui,
            ),
            SimpleListItem(
                title = resources.getString(R.string.turn_off_screen),
                icon = R.drawable.ic_round_screen_off,
            ),
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.statusBarColor = SurfaceColors.SURFACE_0.getColor(this)

        findViewById<MaterialToolbar>(R.id.toolbar).setOnMenuItemClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    SettingsActivity::class.java,
                ),
            )
            true
        }
        findViewById<RecyclerView>(R.id.list).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = SimpleListAdapter(getListItems(), this@MainActivity)
        }
        findViewById<ExtendedFloatingActionButton>(R.id.floating_action_button).setOnClickListener {
            try {
                val process = Runtime.getRuntime().exec("su")
                val output = DataOutputStream(process.outputStream)
                output.writeBytes("echo access granted\n")
                output.writeBytes("exit\n")
                output.flush()
            } catch (exception: IOException) {
                exception.printStackTrace()
            }
        }
    }

    override fun onItemClicked(position: Int) {
        val powerOptions = PowerOptions(this, true)
        when (position) {
            LIST_ITEM_SHUTDOWN ->
                askBefore(R.string.shutdown) {
                    powerOptions.shutdown()
                }
            LIST_ITEM_REBOOT ->
                askBefore(R.string.reboot) {
                    powerOptions.reboot()
                }
            LIST_ITEM_REBOOT_INTO_SAFE_MODE ->
                askBefore(R.string.reboot_into_safe_mode) {
                    powerOptions.rebootIntoSafeMode()
                }
            LIST_ITEM_REBOOT_INTO_RECOVERY ->
                askBefore(R.string.reboot_into_recovery) {
                    powerOptions.rebootIntoRecovery()
                }
            LIST_ITEM_REBOOT_INTO_BOOTLOADER ->
                askBefore(R.string.reboot_into_bootloader) {
                    powerOptions.rebootIntoBootloader()
                }
            LIST_ITEM_REBOOT_INTO_EDL ->
                askBefore(R.string.reboot_into_edl) {
                    powerOptions.rebootIntoEDL()
                }
            LIST_ITEM_SOFT_REBOOT ->
                askBefore(R.string.soft_reboot) {
                    powerOptions.softReboot()
                }
            LIST_ITEM_RESTART_SYSTEM_UI ->
                askBefore(R.string.restart_system_ui) {
                    powerOptions.restartSystemUI()
                }
            LIST_ITEM_TURN_OFF_SCREEN ->
                askBefore(R.string.turn_off_screen) {
                    powerOptions.turnOffScreen()
                }
        }
    }

    private fun askBefore(
        resource: Int,
        lambda: () -> Unit,
    ) {
        if (PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean(P.CONFIRM_DIALOG, P.CONFIRM_DIALOG_DEFAULT)
        ) {
            MaterialAlertDialogBuilder(this)
                .setTitle(R.string.confirm_dialog)
                .setMessage(R.string.confirm_dialog_summary)
                .setPositiveButton(resource) { _, _ ->
                    lambda()
                }
                .setNegativeButton(android.R.string.cancel) { _, _ -> }
                .show()
        } else {
            lambda()
        }
    }

    companion object {
        private const val LIST_ITEM_SHUTDOWN = 0
        private const val LIST_ITEM_REBOOT = 1
        private const val LIST_ITEM_REBOOT_INTO_SAFE_MODE = 2
        private const val LIST_ITEM_REBOOT_INTO_RECOVERY = 3
        private const val LIST_ITEM_REBOOT_INTO_BOOTLOADER = 4
        private const val LIST_ITEM_REBOOT_INTO_EDL = 5
        private const val LIST_ITEM_SOFT_REBOOT = 6
        private const val LIST_ITEM_RESTART_SYSTEM_UI = 7
        private const val LIST_ITEM_TURN_OFF_SCREEN = 8
    }
}
