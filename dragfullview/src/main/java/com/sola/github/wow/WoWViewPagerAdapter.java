package com.sola.github.wow;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sola.github.wow.ColorFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class WoWViewPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<ColorFragment> fragments;
    private int fragmentsNumber;

    /**
     * fragments' color
     */
    private Integer colorRes = null;
    private Integer color = null;
    private ArrayList<Integer> colorsRes = null;
    private ArrayList<Integer> colors = null;

    public WoWViewPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
    }

    @SuppressWarnings("unused")
    public int getFragmentsNumber() {
        return fragmentsNumber;
    }

    @SuppressWarnings("unused")
    public void setFragmentsNumber(int fragmentsNumber) {
        this.fragmentsNumber = fragmentsNumber;
    }

    @Override
    public Fragment getItem(int position) {
        ColorFragment fragment = null;

        if (position < fragments.size()) fragment = fragments.get(position);

        if (fragment == null) {
            fragment = new ColorFragment();
            if (colorRes != null) {
                // the resource of color of all fragments has been set
                fragment.setColorRes(colorRes);
            } else {
                if (color != null) {
                    // the color of all fragments has been set
                    fragment.setColor(color);
                } else {
                    if (colors != null) {
                        if (position < 0 || position >= colors.size()) {
                            // out of index
                            fragment.setColor(colors.get(position));
                        } else {
                            fragment.setColor(Color.TRANSPARENT);
                        }
                    } else {
                        if (colorsRes != null) {
                            if (position < 0 || position >= colorsRes.size()) {
                                // out of index
                                fragment.setColor(Color.TRANSPARENT);
                            } else {
                                fragment.setColorRes(colorsRes.get(position));
                            }
                        } else {
                            fragment.setColor(Color.TRANSPARENT);
                        }
                    }
                }
            }
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return fragmentsNumber;
    }

    public int getColorRes() {
        return colorRes;
    }

    /**
     * set resource of color of fragments
     *
     * @param colorRes resource of color of fragments
     */
    public void setColorRes(int colorRes) {
        this.colorRes = colorRes;
        colorsRes = null;
        colors = null;
        color = null;
    }

    public Integer getColor() {
        return color;
    }

    /**
     * set color of fragments
     *
     * @param color color of fragments
     */
    public void setColor(Integer color) {
        this.color = color;
        colorRes = null;
        colors = null;
        colorsRes = null;
    }

    /**
     * set resources of colors of fragments
     *
     * @return resources of colors of fragments
     */
    public ArrayList<Integer> getColorsRes() {
        return colorsRes;
    }

    public void setColorsRes(Integer[] colorsRes) {
        setColorsRes(new ArrayList<>(Arrays.asList(colorsRes)));
    }

    /**
     * set resources of colors of fragments
     *
     * @param colorsRes resources of colors of fragments
     */
    private void setColorsRes(ArrayList<Integer> colorsRes) {
        this.colorsRes = colorsRes;
        colors = null;
        color = null;
        colorRes = null;
    }

    public ArrayList<Integer> getColors() {
        return colors;
    }

    /**
     * set colors of fragments
     *
     * @param colors colors
     */
    public void setColors(Integer[] colors) {
        setColors(new ArrayList<>(Arrays.asList(colors)));
    }

    /**
     * set colors of fragments
     *
     * @param colors colors
     */
    private void setColors(ArrayList<Integer> colors) {
        this.colors = colors;
        colorRes = null;
        color = null;
        colorsRes = null;
    }
}
