package io.github.domi04151309.powerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ScreenoffActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PowerOptions(this).turnOffScreen()
        finish()
    }

}
