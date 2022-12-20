package com.example.labaratornaya_1.fragments;

import android.app.Activity;
import android.content.Intent;

import com.example.labaratornaya_1.R;

public class Utils
{
    private static int sTheme;
    public final static int THEME_DEFAULT = 0;
    public final static int THEME_GREEN = 1;
    public final static int THEME_DARK = 2;

    public static void changeToTheme(Activity activity, int theme)
    {
        sTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }
    public static void onActivityCreateSetTheme(Activity activity)
    {
        switch (sTheme)
        {
            default:
            case THEME_DEFAULT:
                activity.setTheme(R.style.Theme_Labaratornaya_1);
                break;
            case THEME_GREEN:
                activity.setTheme(R.style.Theme_Green);
                break;
            case THEME_DARK:
                activity.setTheme(R.style.Theme_Dark);
                break;
        }
    }
}