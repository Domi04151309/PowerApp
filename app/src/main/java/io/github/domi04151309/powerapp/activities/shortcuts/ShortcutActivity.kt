package io.github.domi04151309.powerapp.activities.shortcuts

import android.app.Activity
import android.content.Intent
import android.content.Intent.ShortcutIconResource
import android.os.Bundle
import io.github.domi04151309.powerapp.R

abstract class ShortcutActivity : Activity() {
    open fun onOpened() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent.action == Intent.ACTION_CREATE_SHORTCUT) {
            setupShortcut()
        } else {
            onOpened()
        }
        finish()
    }

    open fun getShortcutName(): String {
        return ""
    }

    private fun setupShortcut() {
        setResult(
            RESULT_OK,
            Intent().putExtra(Intent.EXTRA_SHORTCUT_INTENT, Intent(this, this::class.java))
                .putExtra(Intent.EXTRA_SHORTCUT_NAME, getShortcutName())
                .putExtra(
                    Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                    ShortcutIconResource.fromContext(this, R.mipmap.ic_launcher),
                ),
        )
    }
}
