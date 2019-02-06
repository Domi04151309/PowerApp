package io.github.domi04151309.powerapp

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.graphics.BitmapFactory
import android.preference.PreferenceManager
import android.support.v4.content.ContextCompat

internal object Theme {

    var customActionBar = false

    fun check(context: Context) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val userTheme = preferences.getString("AppStyle", "9")
        when (userTheme) {
            "9" -> {
                context.setTheme(R.style.AppTheme_9)
                recent(context, R.color.colorPrimary_9)
                customActionBar = true
            }
            "8" -> {
                context.setTheme(R.style.AppTheme_8)
                recent(context, R.color.colorPrimary_8)
                customActionBar = false
            }
            "7" -> {
                context.setTheme(R.style.AppTheme_7)
                recent(context, R.color.colorPrimary_7)
                customActionBar = false
            }
            "dark" -> {
                context.setTheme(R.style.AppTheme_Dark)
                recent(context, R.color.colorPrimary_Dark)
                customActionBar = false
            }
            "black" -> {
                context.setTheme(R.style.AppTheme_Black)
                recent(context, R.color.black)
                customActionBar = false
            }
            else -> {
                context.setTheme(R.style.AppTheme_9)
                recent(context, R.color.colorPrimary_9)
                customActionBar = true
            }
        }
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
