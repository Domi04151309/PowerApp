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
    companion object {
        private const val LIST_ITEM_SHUTDOWN = 1
        private const val LIST_ITEM_REBOOT = 2
        private const val LIST_ITEM_REBOOT_INTO_SAFE_MODE = 3
        private const val LIST_ITEM_REBOOT_INTO_RECOVERY = 4
        private const val LIST_ITEM_REBOOT_INTO_BOOTLOADER = 5
        private const val LIST_ITEM_REBOOT_INTO_EDL = 6
        private const val LIST_ITEM_SOFT_REBOOT = 7
        private const val LIST_ITEM_RESTART_SYSTEM_UI = 8
        private const val LIST_ITEM_TURN_OFF_SCREEN = 9
    }

    private fun getListItems() =
        listOf(
            SimpleListItem(
                title = resources.getString(R.string.Shutdown),
                icon = R.drawable.ic_round_shutdown,
            ),
            SimpleListItem(
                title = resources.getString(R.string.Reboot),
                icon = R.drawable.ic_round_reboot,
            ),
            SimpleListItem(
                title = resources.getString(R.string.SafeMode),
                icon = R.drawable.ic_round_safe_mode,
            ),
            SimpleListItem(
                title = resources.getString(R.string.Recovery),
                icon = R.drawable.ic_round_recovery,
            ),
            SimpleListItem(
                title = resources.getString(R.string.Bootloader),
                icon = R.drawable.ic_round_bootloader,
            ),
            SimpleListItem(
                title = resources.getString(R.string.EDL),
                icon = R.drawable.ic_round_edl,
            ),
            SimpleListItem(
                title = resources.getString(R.string.SoftReboot),
                icon = R.drawable.ic_round_soft_reboot,
            ),
            SimpleListItem(
                title = resources.getString(R.string.SystemUI),
                icon = R.drawable.ic_round_restart_system_ui,
            ),
            SimpleListItem(
                title = resources.getString(R.string.ScreenOff),
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
            val process: Process
            try {
                process = Runtime.getRuntime().exec("su")
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
        askBefore {
            when (position) {
                LIST_ITEM_SHUTDOWN -> powerOptions.shutdown()
                LIST_ITEM_REBOOT -> powerOptions.reboot()
                LIST_ITEM_REBOOT_INTO_SAFE_MODE -> powerOptions.rebootIntoSafeMode()
                LIST_ITEM_REBOOT_INTO_RECOVERY -> powerOptions.rebootIntoRecovery()
                LIST_ITEM_REBOOT_INTO_BOOTLOADER -> powerOptions.rebootIntoBootloader()
                LIST_ITEM_REBOOT_INTO_EDL -> powerOptions.rebootIntoEDL()
                LIST_ITEM_SOFT_REBOOT -> powerOptions.softReboot()
                LIST_ITEM_RESTART_SYSTEM_UI -> powerOptions.restartSystemUI()
                LIST_ITEM_TURN_OFF_SCREEN -> powerOptions.turnOffScreen()
            }
        }
    }

    private fun askBefore(function: () -> Unit) {
        if (PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean(P.CONFIRM_DIALOG, P.CONFIRM_DIALOG_DEFAULT)
        ) {
            MaterialAlertDialogBuilder(this)
                .setTitle(R.string.confirm_dialog)
                .setMessage(R.string.confirm_dialog_summary)
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    function()
                }
                .setNegativeButton(android.R.string.cancel) { _, _ -> }
                .show()
        } else {
            function()
        }
    }
}
