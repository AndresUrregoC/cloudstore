package com.grability.cloudstore.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by AndresDev on 23/02/16.
 */
public class AnimateUtil {

    private int lastPosition = -1;

    public void setAnimation(View viewToAnimate, int position, int animations, int duration) {

        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), animations);
            animation.setDuration(duration);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}
