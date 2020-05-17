package io.github.domi04151309.powerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ShutdownActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PowerOptions(this).shutdown()
    }

}
