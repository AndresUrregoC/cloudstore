package com.grability.cloudstore.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;

/**
 * Created by AndresDev on 24/02/16.
 */
public class OrientationUtil {

    public static void adjustScreenOrientation(Activity activity) {
        int orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        if (isLarge(activity)) {
            orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        }
        activity.setRequestedOrientation(orientation);
    }

    public static boolean isLarge(Context context) {
        return (getScreenSize(context)) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static int getScreenSize(Context context) {
        return context.getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;
    }
}
