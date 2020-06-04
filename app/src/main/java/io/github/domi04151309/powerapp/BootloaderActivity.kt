package io.github.domi04151309.powerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class BootloaderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PowerOptions(this).rebootIntoBootloader()
        finish()
    }

}
