package com.sola.github.solauiproject.transitions;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.transition.Slide;
import android.transition.Transition;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.sola.github.solauiproject.R;
import com.sola.github.solauiproject.tools.BundleFactory;
import com.sola.github.solauiproject.transitions.tool.TransitionAdapter;
import com.sola.github.tools.Navigator;
import com.sola.github.tools.RxBaseActivity;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

/**
 * Created by slove
 * 2016/12/12.
 */
@EActivity(R.layout.activity_first_transition)
public class TransitionFirstActivity extends RxBaseActivity {

    // ===========================================================
    // Constants
    // ===========================================================

    private static final int ACTIVITY_RESULT = 0x0021;

    // ===========================================================
    // Fields
    // ===========================================================

    @ViewById
    View id_btn_switch;

    @ViewById
    ViewGroup id_include_rectangle;

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide slide = new Slide(Gravity.BOTTOM);
            slide.excludeTarget(android.R.id.navigationBarBackground, true);
            slide.excludeTarget(android.R.id.statusBarBackground, true);
            slide.excludeTarget(R.id.id_tool_bar, true);
            getWindow().setExitTransition(slide);
            // 这里的跳转的问题
            if (getWindow().getEnterTransition() != null)
                getWindow().getEnterTransition().addListener(new TransitionAdapter() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onTransitionEnd(Transition transition) {
                        super.onTransitionEnd(transition);
                        getWindow().getEnterTransition().removeListener(this);
                        for (int i = 0; i < id_include_rectangle.getChildCount(); i++) {
                            View view = id_include_rectangle.getChildAt(i);
                            view.animate().setStartDelay(i * 50)
                                    .scaleX(1).scaleY(1);
                        }
                    }
                });
        }
    }

    // ===========================================================
    // Methods
    // ===========================================================

    @OnActivityResult(ACTIVITY_RESULT)
    public void onResult() {
//        if(resultCode)
        for (int i = 0; i < id_include_rectangle.getChildCount(); i++) {
            View view = id_include_rectangle.getChildAt(i);
            view.animate().setStartDelay(i * 50)
                    .scaleX(1).scaleY(1);
        }
    }

    @Click
    public void id_btn_switch() {
        for (int i = 0; i < id_include_rectangle.getChildCount(); i++) {
            View view = id_include_rectangle.getChildAt(i);
            view.animate().setStartDelay(i * 50)
                    .scaleX(0).scaleY(0);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Navigator.getInstance().switchActivityWithTransition(mContext.get(),
                    TransitionSecondActivity_.class,
                    BundleFactory.getInstance().putString("Title", "Second").build(),
                    ACTIVITY_RESULT,
                    -1, -1,
                    Pair.create(id_btn_switch, "fab_btn"),
                    Pair.create(id_tool_bar, "header_1")
            );
//            Intent intent = new Intent(mContext.get(), TransitionSecondActivity_.class);
//            ActivityOptions transitionActivityOptions =
//                    ActivityOptions.makeSceneTransitionAnimation(
//                            TransitionFirstActivity.this,
//                            Pair.create(id_btn_switch, "fab_btn"),
//                            Pair.create(id_tool_bar, "header_1"));
//            startActivity(intent, transitionActivityOptions.toBundle());
        }
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
