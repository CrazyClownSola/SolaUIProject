package com.sola.github.solauiproject;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.sola.github.dragfullview.DragRoundView;
import com.sola.github.dragfullview.RoundFrameView;
import com.sola.github.dragfullview.delegate.IEventCallBack;
import com.sola.github.dragfullview.delegate.IRoundTouchListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by 禄骥
 * 2016/8/10.
 */
@EActivity(R.layout.activity_round)
public class RoundActivity extends FragmentActivity {
    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    @ViewById
    RoundFrameView id_round_frame;

    @ViewById
    DragRoundView id_btn_round;

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    // ===========================================================
    // Methods
    // ===========================================================

    @AfterViews
    public void afterViews() {
        id_btn_round.setListener(new IRoundTouchListener() {
            @Override
            public void onViewTouch(MotionEvent event, int originalX, int originalY, IEventCallBack callBack) {
                id_round_frame.startDrawing(event, originalX, originalY, callBack);
            }
        });
    }

    @Click
    public void id_trgger() {
        id_btn_round.refreshText("99+");
    }


    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
