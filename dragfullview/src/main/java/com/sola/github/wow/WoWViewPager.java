package com.sola.github.wow;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.AccelerateInterpolator;

import com.sola.github.wow.animations.ViewAnimation;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class WoWViewPager extends ViewPager {

    /**
     * viewAnimations for many views
     */
    private ArrayList<ViewAnimation> viewAnimations;

    private int scrollDuration = 1000;

    public WoWViewPager(Context context) {
        super(context);
        viewAnimations = new ArrayList<>();
    }

    public WoWViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        viewAnimations = new ArrayList<>();
    }

    /**
     * add a viewAnimation to WoWo
     *
     * @param viewAnimation the new viewAnimation
     */
    public void addAnimation(ViewAnimation viewAnimation) {
        viewAnimations.add(viewAnimation);
    }

//    public void runInit() {
//        for (int i = 0; i < viewAnimations.size(); i++) {
//            viewAnimations.get(i).play(-1, 1);
//            viewAnimations.get(i).end(-2);
//        }
//    }

    /**
     * get the starting page and the positionOffset to play all pageAnimations in each viewAnimation
     *
     * @param position             start page
     * @param positionOffset       positionOffset
     * @param positionOffsetPixels positionOffset By Pixels
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.d("Sola_wow", "onPageScrolled position[" + position + "]" +
                "offset[" + positionOffset + "]offsetPx[" + positionOffsetPixels + "]");
        super.onPageScrolled(position, positionOffset, positionOffsetPixels);

        for (int i = 0; i < viewAnimations.size(); i++) {
            viewAnimations.get(i).play(position, positionOffset);
            viewAnimations.get(i).end(position - 1);
        }

    }


}
