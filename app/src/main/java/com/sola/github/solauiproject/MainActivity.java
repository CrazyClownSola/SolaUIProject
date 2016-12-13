package com.sola.github.solauiproject;

import android.view.View;

import com.sola.github.solauiproject.tools.BundleFactory;
import com.sola.github.solauiproject.transitions.TransitionFirstActivity_;
import com.sola.github.tools.Navigator;
import com.sola.github.tools.RxBaseActivity;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_main)
public class MainActivity extends RxBaseActivity {

    @Override
    protected void doAfterViews() {
        id_text_title.setText(getString(R.string.app_name));
    }

    @Click({
            R.id.id_btn_fuzzy,
            R.id.id_btn_wow,
            R.id.id_btn_Empty,
            R.id.id_btn_scan,
            R.id.id_btn_web,
            R.id.id_btn_round,
            R.id.id_btn_edge,
            R.id.id_btn_material,
            R.id.id_btn_recycler,
            R.id.id_btn_transition
    })
    public void btnClick(View v) {
        switch (v.getId()) {
            case R.id.id_btn_fuzzy:
                Navigator.getInstance().switchActivity(
                        mContext.get(),
                        FuzzyImageActivity_.class);
                break;
            case R.id.id_btn_wow:
                Navigator.getInstance().switchActivity(
                        mContext.get(),
                        WowActivity_.class);
                break;
            case R.id.id_btn_Empty:
                Navigator.getInstance().switchActivity(
                        mContext.get(),
                        EmptyActivity_.class);
                break;
            case R.id.id_btn_scan:
                Navigator.getInstance().switchActivity(
                        mContext.get(),
                        ScanViewActivity_.class,
                        BundleFactory.getInstance()
                                .putString("title", "扫描")
                                .build());
                break;
            case R.id.id_btn_web:
                Navigator.getInstance().switchActivity(
                        mContext.get(),
                        WebFixContentActivity_.class,
                        BundleFactory.getInstance()
                                .putString("title", "网页")
                                .build());
                break;
            case R.id.id_btn_round:
                Navigator.getInstance().switchActivity(
                        mContext.get(),
                        RoundActivity_.class,
                        BundleFactory.getInstance()
                                .putString("title", "Round")
                                .build());
                break;
            case R.id.id_btn_edge:
                Navigator.getInstance().switchActivity(
                        mContext.get(),
                        EdgeActivity_.class,
                        BundleFactory.getInstance()
                                .putString("title", "Edge")
                                .build());
                break;
            case R.id.id_btn_material:
                Navigator.getInstance().switchActivity(
                        mContext.get(),
                        MaterialActivity_.class,
                        BundleFactory.getInstance()
                                .putString("title", "Material")
                                .build());
                break;
            case R.id.id_btn_recycler:

                break;

            case R.id.id_btn_transition:
                Navigator.getInstance().switchActivityWithTransition(
                        mContext.get(),
                        TransitionFirstActivity_.class,
                        BundleFactory.getInstance()
                                .putString("title", "Transition")
                                .build(), -1, -1, -1,
                        id_tool_bar, "header_1");
                break;

        }
    }

}
