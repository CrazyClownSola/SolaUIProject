package com.sola.github.solauiproject.transitions;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.ViewGroup;

import com.sola.github.solauiproject.R;
import com.sola.github.solauiproject.transitions.tool.TransitionAdapter;
import com.sola.github.tools.RxBaseActivity;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by slove
 * 2016/12/12.
 */
@EActivity(R.layout.activity_second_transition)
public class TransitionSecondActivity extends RxBaseActivity {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    @ViewById
    ViewGroup id_include_transition;

    int count;

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    protected void doAfterViews() {
        initTransition();
    }

    // ===========================================================
    // Methods
    // ===========================================================

    private void initTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide slide = new Slide(Gravity.TOP);
            slide.excludeTarget(android.R.id.navigationBarBackground, true);
            slide.excludeTarget(android.R.id.statusBarBackground, true);
            getWindow().setExitTransition(slide);
            // 这里的跳转的问题
            if (getWindow().getEnterTransition() != null)
                getWindow().getEnterTransition().addListener(new TransitionAdapter() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onTransitionEnd(Transition transition) {
                        super.onTransitionEnd(transition);
                        getWindow().getEnterTransition().removeListener(this);

                        Scene scene = Scene.getSceneForLayout(id_include_transition,
                                R.layout.layout_transition_second_before, mContext.get());
                        TransitionManager.go(scene);
                        count++;
                    }
                });
        }
    }

    @Click
    public void id_btn_switch() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Scene scene;
            if (++count % 2 == 0)
                scene = Scene.getSceneForLayout(id_include_transition,
                        R.layout.layout_transition_second_after, mContext.get());
            else
                scene = Scene.getSceneForLayout(id_include_transition,
                        R.layout.layout_transition_second_before, mContext.get());
            TransitionManager.go(scene);
        }
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
