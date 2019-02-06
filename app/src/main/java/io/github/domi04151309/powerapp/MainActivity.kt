package io.github.domi04151309.powerapp

import android.content.Intent
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View

import java.io.DataOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Theme.check(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Theme.customActionBar) {
            val actionBar = supportActionBar!!
            actionBar.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            actionBar.setDisplayShowCustomEnabled(true)
            actionBar.setCustomView(R.layout.action_bar)
            actionBar.elevation = 0f
            val scrollView = findViewById<View>(R.id.scrollView)
            scrollView.viewTreeObserver.addOnScrollChangedListener {
                if (scrollView.scrollY > 0)
                    actionBar.elevation = 16f
                else
                    actionBar.elevation = 0f
            }
        }

        val root = Root(this)

        findViewById<View>(R.id.shutdown).setOnClickListener {
            root.shell("reboot -p")
        }
        findViewById<View>(R.id.reboot).setOnClickListener {
            root.shell("reboot")
        }
        findViewById<View>(R.id.recovery).setOnClickListener {
            root.shell("reboot recovery")
        }
        findViewById<View>(R.id.bootloader).setOnClickListener {
            root.shell("reboot bootloader")
        }
        findViewById<View>(R.id.soft_reboot).setOnClickListener {
            root.shell("setprop ctl.restart zygote")
        }
        findViewById<View>(R.id.system_ui).setOnClickListener {
            root.shell("killall com.android.systemui")
        }
        findViewById<View>(R.id.screen_off).setOnClickListener {
            root.shell("input keyevent KEYCODE_POWER")
        }
        findViewById<View>(R.id.root).setOnClickListener {
            val p: Process
            try {
                p = Runtime.getRuntime().exec("su")
                val os = DataOutputStream(p.outputStream)
                os.writeBytes("echo access granted\n")
                os.writeBytes("exit\n")
                os.flush()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        findViewById<View>(R.id.prefBtn).setOnClickListener { startActivity(Intent(this@MainActivity, Preferences::class.java)) }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onBackPressed() {
        startActivity(Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }
}
