package io.github.domi04151309.powerapp.shortcuts

import android.app.Activity
import android.os.Bundle

open class ShortcutActivity : Activity() {

    open fun onOpened() { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onOpened()
        finish()
    }
}
