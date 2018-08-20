package io.github.domi04151309.powerapp;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;

class Theme {

    static void check(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String userTheme = preferences.getString("AppStyle", "8");
        switch (userTheme) {
            case "8":
                context.setTheme(R.style.AppTheme_8);
                recent(context, R.color.colorPrimary);
                break;
            case "7":
                context.setTheme(R.style.AppTheme_7);
                recent(context, R.color.colorPrimary_7);
                break;
            case "dark":
                context.setTheme(R.style.AppTheme_Dark);
                recent(context, R.color.colorPrimary_Dark);
                break;
            case "black":
                context.setTheme(R.style.AppTheme_Black);
                recent(context, R.color.black);
                break;
        }
    }

    static void checkDialog(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String userTheme = preferences.getString("AppStyle", "8");
        switch (userTheme) {
            case "8":
                recent(context, R.color.colorPrimary);
                break;
            case "7":
                recent(context, R.color.colorPrimary_7);
                break;
            case "dark":
                context.setTheme(R.style.DialogTheme_Dark);
                recent(context, R.color.colorPrimary_Dark);
                break;
            case "black":
                context.setTheme(R.style.DialogTheme_Black);
                recent(context, R.color.black);
                break;
        }
    }

    private static void recent(Context context, int color){
        ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription(
                context.getString(R.string.app_name),
                BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher),
                ContextCompat.getColor(context, color)
        );
        ((Activity)context).setTaskDescription(taskDescription);
    }
}
