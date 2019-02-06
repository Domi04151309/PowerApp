package io.github.domi04151309.powerapp

import android.content.Context
import android.util.Log
import android.widget.Toast

class Root(context: Context) {

    private val c = context

    fun shell(command: String) {
        try {
            val p = Runtime.getRuntime()
                    .exec(arrayOf("su", "-c", command))
            p.waitFor()
        } catch (ex: Exception) {
            Toast.makeText(c, "Action failed! Please enable root!", Toast.LENGTH_LONG).show()
            Log.e("Superuser", ex.toString())
        }

    }
}