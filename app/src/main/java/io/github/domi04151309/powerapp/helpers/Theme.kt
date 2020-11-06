package io.github.domi04151309.powerapp.helpers

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.graphics.BitmapFactory
import androidx.preference.PreferenceManager
import androidx.core.content.ContextCompat
import io.github.domi04151309.powerapp.R

internal object Theme {

    fun check(context: Context) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        when (prefs.getString("AppTheme", "light")) {
            "light" -> {
                context.setTheme(R.style.AppThemeLight)
                recent(context, R.color.colorPrimary)
            }
            "dark" -> {
                context.setTheme(R.style.AppThemeDark)
                recent(context, R.color.dark)
            }
            "black" -> {
                context.setTheme(R.style.AppThemeBlack)
                recent(context, R.color.black)
            }
        }
        context.setTheme(R.style.Patch)
    }

    private fun recent(context: Context, color: Int) {
        val taskDescription = ActivityManager.TaskDescription(
                context.getString(R.string.app_name),
                BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher),
                ContextCompat.getColor(context, color)
        )
        (context as Activity).setTaskDescription(taskDescription)
    }
}
