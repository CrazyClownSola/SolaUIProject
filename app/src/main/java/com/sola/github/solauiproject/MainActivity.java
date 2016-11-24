package com.sola.github.solauiproject;

import com.sola.github.solauiproject.tools.BundleFactory;
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

    @Click
    public void id_btn_round() {
        Navigator.getInstance().switchActivity(
                mContext.get(),
                RoundActivity_.class,
                BundleFactory.getInstance()
                        .putString("title", "Round")
                        .build());
    }

    @Click
    public void id_btn_edge() {
        Navigator.getInstance().switchActivity(
                mContext.get(),
                EdgeActivity_.class,
                BundleFactory.getInstance()
                        .putString("title", "Edge")
                        .build());
    }

    @Click
    public void id_btn_web() {
        Navigator.getInstance().switchActivity(
                mContext.get(),
                WebFixContentActivity_.class,
                BundleFactory.getInstance()
                        .putString("title", "网页")
                        .build());
    }

    @Click
    public void id_btn_scan() {
        Navigator.getInstance().switchActivity(
                mContext.get(),
                ScanViewActivity_.class,
                BundleFactory.getInstance()
                        .putString("title", "扫描")
                        .build());
    }

    @Click
    public void id_btn_wow() {
        Navigator.getInstance().switchActivity(
                mContext.get(),
                WowActivity_.class);
    }

    @Click
    public void id_btn_Empty() {
        Navigator.getInstance().switchActivity(
                mContext.get(),
                EmptyActivity_.class);
    }

    @Click
    public void id_btn_fuzzy() {
        Navigator.getInstance().switchActivity(
                mContext.get(),
                FuzzyImageActivity_.class);
    }

}
