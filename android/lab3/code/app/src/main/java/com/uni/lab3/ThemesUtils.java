package com.uni.lab3;

public class ThemesUtils {
    public static int getThemeId(Themes theme) {
        switch (theme) {
            case RED: {
                return R.style.Theme_Lab3_red;
            }
            case GREEN: {
                return R.style.Theme_Lab3_green;
            }
            default: {
                return R.style.Theme_Lab3;
            }
        }
    }
}
