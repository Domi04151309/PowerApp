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
        val userThemeMode = preferences.getString("AppStyleMode", "light")
        when (userTheme) {
            "9" -> {
                context.setTheme(R.style.AppTheme_9)
                when (userThemeMode) {
                    "light" -> {
                        recent(context, R.color.colorPrimary_9)
                    }
                    "dark" -> {
                        context.setTheme(R.style.Dark)
                        recent(context, R.color.colorPrimary_Dark)
                    }
                    "black" -> {
                        context.setTheme(R.style.Black)
                        recent(context, R.color.black)
                    }
                }
                customActionBar = true
            }
            "8" -> {
                context.setTheme(R.style.AppTheme_8)
                when (userThemeMode) {
                    "light" -> {
                        recent(context, R.color.colorPrimary_8)
                    }
                    "dark" -> {
                        context.setTheme(R.style.Dark)
                        context.setTheme(R.style.Theme_8_Patch)
                        recent(context, R.color.colorPrimary_Dark)
                    }
                    "black" -> {
                        context.setTheme(R.style.Black)
                        context.setTheme(R.style.ActionBarTheme_8_Patch)
                        recent(context, R.color.black)
                    }
                }
                customActionBar = false
            }
            "7" -> {
                context.setTheme(R.style.AppTheme_7)
                when (userThemeMode) {
                    "light" -> {
                        recent(context, R.color.colorPrimary_7)
                    }
                    "dark" -> {
                        context.setTheme(R.style.Dark_TwoTone)
                        recent(context, R.color.colorPrimary_Dark)
                    }
                    "black" -> {
                        context.setTheme(R.style.Black)
                        recent(context, R.color.black)
                    }
                }
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
