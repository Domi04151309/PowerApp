package io.github.domi04151309.powerapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.TextView
import io.github.domi04151309.powerapp.BuildConfig
import io.github.domi04151309.powerapp.R
import io.github.domi04151309.powerapp.helpers.Theme

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Theme.check(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        findViewById<TextView>(R.id.versionTxt).text = resources.getString(R.string.about_version, BuildConfig.VERSION_NAME)
        findViewById<TextView>(R.id.github).movementMethod = LinkMovementMethod.getInstance()
        findViewById<TextView>(R.id.license).movementMethod = LinkMovementMethod.getInstance()
        findViewById<TextView>(R.id.icons).movementMethod = LinkMovementMethod.getInstance()
    }
}
