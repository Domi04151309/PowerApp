package io.github.domi04151309.powerapp.helpers

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.res.Configuration
import android.graphics.BitmapFactory
import androidx.preference.PreferenceManager
import androidx.core.content.ContextCompat
import io.github.domi04151309.powerapp.R

internal object Theme {

    fun set(context: Context) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        when (prefs.getString(P.PREF_THEME, P.PREF_THEME_DEFAULT)) {
            "light" -> {
                context.setTheme(R.style.AppThemeLight)
                recent(context, R.color.colorPrimary)
            }
            "dark" -> {
                context.setTheme(R.style.AppThemeDark)
                recent(context, R.color.colorPrimaryDark)
            }
            "black" -> {
                context.setTheme(R.style.AppThemeBlack)
                recent(context, R.color.colorPrimaryBlack)
            }
            "auto" -> {
                when (context.resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
                    Configuration.UI_MODE_NIGHT_YES -> {
                        context.setTheme(R.style.AppThemeDark)
                        recent(context, R.color.colorPrimaryDark)
                    }
                    else -> {
                        context.setTheme(R.style.AppThemeLight)
                        recent(context, R.color.colorPrimary)
                    }
                }
            }
        }
        context.setTheme(R.style.AppThemePatch)
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
