package com.sola.github.wow;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by slove
 * 2016/11/23.
 */
public class ColorFragment extends Fragment {

    private Integer colorRes = null;
    private Integer color = null;

    public ColorFragment() {
        this.colorRes = android.R.color.transparent;
    }

    public Integer getColorRes() {
        return colorRes;
    }

    public void setColorRes(Integer colorRes) {
        this.colorRes = colorRes;
        color = null;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
        colorRes = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout view = new LinearLayout(getActivity());
        view.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        view.setOrientation(LinearLayout.VERTICAL);

        if (colorRes != null) {
            // the resource of color has been set
            view.setBackgroundColor(ContextCompat.getColor(getActivity(), colorRes));
        } else {
            if (color != null) {
                // the color has been set
                view.setBackgroundColor(color);
            } else {
                // set transparent
                view.setBackgroundColor(Color.TRANSPARENT);
            }
        }

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}