package com.sola.github.solauiproject;

import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sola.github.tools.RxBaseActivity;
import com.sola.github.wow.WoWUtil;
import com.sola.github.wow.WoWViewPager;
import com.sola.github.wow.WoWViewPagerAdapter;
import com.sola.github.wow.animations.ViewAnimation;
import com.sola.github.wow.animations.WoWAlphaAnimation;
import com.sola.github.wow.animations.WoWAntiPathAnimation;
import com.sola.github.wow.animations.WoWPathAnimation;
import com.sola.github.wow.animations.WoWRotationAnimation;
import com.sola.github.wow.animations.WoWScaleAnimation;
import com.sola.github.wow.animations.WoWTextViewSizeAnimation;
import com.sola.github.wow.animations.WoWTranslationAnimation;
import com.sola.github.wow.eases.EaseType;
import com.sola.github.wow.view.WoWPathView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by slove
 * 2016/11/23.
 */
@EActivity(R.layout.activity_wow)
public class WowActivity extends RxBaseActivity {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    @ViewById
    WoWViewPager id_view_pager_normal;

    @ViewById
    RecyclerView id_recycler_view;

    @ViewById
    TextView id_text_title;

    @ViewById
    ImageView id_image_pic;

    @ViewById
    ViewGroup id_include_title_bottom, id_include_text_bottom;

    @ViewById
    WoWPathView id_path_view_line, id_path_view_image_light, id_path_view_image_light_45, id_path_view_image_low;

    private WoWViewPagerAdapter adapter;

    private int screenW = 1, screenH = 1, circleR = 1;

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 全屏显示
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    @Override
    protected void doAfterViews() {
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        adapter = new WoWViewPagerAdapter(getSupportFragmentManager());
        adapter.setFragmentsNumber(3);
        adapter.setColorRes(android.R.color.transparent);
        id_view_pager_normal.setAdapter(adapter);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        screenW = WoWUtil.getScreenWidth(mContext.get());
        screenH = WoWUtil.getScreenHeight(mContext.get());
        circleR = (int) (Math.sqrt(screenW * screenW + screenH * screenH) + 10);


        RelativeLayout base = (RelativeLayout) findViewById(R.id.base);
        ViewGroup.LayoutParams layoutParams = base.getLayoutParams();
        layoutParams.height = circleR * 2;
        layoutParams.width = circleR * 2;
        base.setLayoutParams(layoutParams);

        RelativeLayout subBase = (RelativeLayout) findViewById(R.id.sub_base);
        layoutParams = subBase.getLayoutParams();
        layoutParams.height = screenH;
        layoutParams.width = screenW;
        subBase.setLayoutParams(layoutParams);

        setLogo();
        setName();
        setLight();
        setLight45();
        setLow();
        setReplyTitle();
        setReplyCount();

//        setProjects();
        setPathLine();
        setRecyclerContainer();
//        id_view_pager_normal.onPageScrolled();
        initViewAnimation();
        id_view_pager_normal.onPageScrolled(0, 0, 0);
    }


    // ===========================================================
    // Methods
    // ===========================================================

    private void initViewAnimation() {

    }

    private void setLogo() {
        ViewAnimation animation = new ViewAnimation(id_image_pic);
        animation.addPageAnimation(new WoWScaleAnimation(
                0, 0, 1,
                0.5f, 0.5f,
                EaseType.EaseOutExpo,
                false
        ));
        animation.addPageAnimation(new WoWTranslationAnimation(
                0, 0, 1,
                id_image_pic.getTranslationX(),
                id_image_pic.getTranslationY(),
                -screenW / 2 + 150,
                -screenH / 2 + 200,
                EaseType.EaseInExpo,
                false
        ));
        id_view_pager_normal.addAnimation(animation);
    }

    private void setName() {
        ViewAnimation animation = new ViewAnimation(id_text_title);
        animation.addPageAnimation(new WoWTranslationAnimation(
                0, 0, 1,
                id_text_title.getTranslationX(),
                id_text_title.getTranslationY(),
                -screenW / 2 + 150 + WoWUtil.dp2px(80, mContext.get()),
                id_image_pic.getTranslationY() + WoWUtil.dp2px(10, mContext.get()),
                EaseType.EaseOutBack,
                false
        ));
        animation.addPageAnimation(new WoWTextViewSizeAnimation(
                0, 0, 1,
                30f,
                22f,
                EaseType.Linear,
                false
        ));
        id_view_pager_normal.addAnimation(animation);
    }

    private void setLight() {
        ViewGroup.LayoutParams layoutParams = id_path_view_image_light.getLayoutParams();
        layoutParams.height = screenH;
        layoutParams.width = screenW;
        id_path_view_image_light.setLayoutParams(layoutParams);

        int mRadius = id_image_pic.getMeasuredWidth();
        int lineLength = 50;
        int offset = (mRadius >> 1) + 50;
        Path path = new Path();
        path.moveTo((screenW >> 1) - offset, screenH >> 1);
        path.rLineTo(-lineLength, 0);
        path.moveTo((screenW >> 1), (screenH >> 1) - offset);
        path.rLineTo(0, -lineLength);
        path.moveTo((screenW >> 1) + offset, screenH >> 1);
        path.rLineTo(lineLength, 0);
        path.moveTo((screenW >> 1), (screenH >> 1) + offset);
        path.rLineTo(0, lineLength);

        id_path_view_image_light.setPath(path);
        ViewAnimation animation = new ViewAnimation(id_path_view_image_light);
        animation.addPageAnimation(new WoWAntiPathAnimation(
                0, 0f, 1f,
                EaseType.EaseInExpo,
                true));
        id_view_pager_normal.addAnimation(animation);
    }

    private void setLight45() {
        ViewGroup.LayoutParams layoutParams = id_path_view_image_light_45.getLayoutParams();
        layoutParams.height = screenH;
        layoutParams.width = screenW;
        id_path_view_image_light_45.setLayoutParams(layoutParams);

        int mRadius = id_image_pic.getMeasuredWidth();
        int lineLength = (int) (50 * Math.sqrt(2) / 2);
        int offsetRange = (int) (Math.sqrt(2) * mRadius / 4) + 50;

        Path path = new Path();
        path.moveTo((screenW >> 1) - offsetRange, (screenH >> 1) - offsetRange);
        path.rLineTo(-lineLength, -lineLength);
        path.moveTo((screenW >> 1) + offsetRange, (screenH >> 1) - offsetRange);
        path.rLineTo(lineLength, -lineLength);
        path.moveTo((screenW >> 1) + offsetRange, (screenH >> 1) + offsetRange);
        path.rLineTo(lineLength, lineLength);
        path.moveTo((screenW >> 1) - offsetRange, (screenH >> 1) + offsetRange);
        path.rLineTo(-lineLength, lineLength);

        id_path_view_image_light_45.setPath(path);
        ViewAnimation animation = new ViewAnimation(id_path_view_image_light_45);
        animation.addPageAnimation(new WoWAntiPathAnimation(
                0, 0f, 1f,
                EaseType.EaseInExpo,
                true));
        id_view_pager_normal.addAnimation(animation);
    }

    public void setLow() {
        ViewGroup.LayoutParams layoutParams = id_path_view_image_low.getLayoutParams();
        layoutParams.height = screenH;
        layoutParams.width = screenW;
        id_path_view_image_low.setLayoutParams(layoutParams);

        int mRadius = id_image_pic.getMeasuredWidth();
        int lineLength45 = (int) (40 * Math.sqrt(2) / 2);
        int lineLength = 40;
        int offset = (mRadius >> 1) + 20;
        int offsetRange = (int) (Math.sqrt(2) * mRadius / 4) + 20;

        Path path = new Path();
        path.moveTo((screenW >> 1) - offset, screenH >> 1);
        path.rLineTo(-lineLength, 0);
        path.moveTo((screenW >> 1), (screenH >> 1) - offset);
        path.rLineTo(0, -lineLength);
        path.moveTo((screenW >> 1) + offset, screenH >> 1);
        path.rLineTo(lineLength, 0);
        path.moveTo((screenW >> 1), (screenH >> 1) + offset);
        path.rLineTo(0, lineLength);
        path.moveTo((screenW >> 1) - offsetRange, (screenH >> 1) - offsetRange);
        path.rLineTo(-lineLength45, -lineLength45);
        path.moveTo((screenW >> 1) + offsetRange, (screenH >> 1) - offsetRange);
        path.rLineTo(lineLength45, -lineLength45);
        path.moveTo((screenW >> 1) + offsetRange, (screenH >> 1) + offsetRange);
        path.rLineTo(lineLength45, lineLength45);
        path.moveTo((screenW >> 1) - offsetRange, (screenH >> 1) + offsetRange);
        path.rLineTo(-lineLength45, lineLength45);

        id_path_view_image_low.setPath(path);
        ViewAnimation animation = new ViewAnimation(id_path_view_image_low);
        animation.addPageAnimation(new WoWAlphaAnimation(
                0, 0, 0,
                0.3f,
                0.3f,
                EaseType.Linear,
                false
        ));
        animation.addPageAnimation(new WoWRotationAnimation(
                0, 0, 0,
                id_path_view_image_low.getPivotX(),
                id_path_view_image_low.getPivotY(),
                0,
                0,
                22.5f,
                EaseType.Linear,
                false
        ));
        animation.addPageAnimation(new WoWAntiPathAnimation(
                0, 0f, 1f,
                EaseType.EaseInExpo,
                true));
        id_view_pager_normal.addAnimation(animation);
    }

    private void setReplyCount() {
        ViewAnimation animation = new ViewAnimation(id_include_title_bottom);
        animation.addPageAnimation(new WoWTranslationAnimation(
                0, 0, 1,
                id_include_title_bottom.getTranslationX(),
                id_include_title_bottom.getTranslationY(),
                -screenW,
                0,
                EaseType.EaseInOutQuint,
                false
        ));
        id_view_pager_normal.addAnimation(animation);
    }

    private void setReplyTitle() {
        ViewAnimation animation = new ViewAnimation(id_include_text_bottom);
        animation.addPageAnimation(new WoWTranslationAnimation(
                0, 0, 1,
                id_include_title_bottom.getTranslationX(),
                id_include_title_bottom.getTranslationY(),
                screenW,
                0,
                EaseType.EaseInOutQuint,
                false
        ));
        id_view_pager_normal.addAnimation(animation);
    }

    private void setRecyclerContainer() {
        ViewAnimation animation = new ViewAnimation(id_recycler_view);
        animation.addPageAnimation(new WoWTranslationAnimation(
                0, 0, 1,
                id_recycler_view.getTranslationX(),
                screenH,
                0,
                -screenH,
                EaseType.EaseInQuart,
                false
        ));
        animation.addPageAnimation(new WoWTranslationAnimation(
                1, 0, 1,
                id_recycler_view.getTranslationX(),
                id_recycler_view.getTranslationY(),
                -screenW,
                0,
                EaseType.EaseInOutBack,
                false
        ));
        animation.addPageAnimation(new WoWRotationAnimation(
                1, 0, 1,
                id_recycler_view.getPivotX(),
                0,
                -90,
                0,
                0,
                EaseType.EaseOutQuart,
                false
        ));
        id_view_pager_normal.addAnimation(animation);
    }

    private void setPathLine() {
        ViewGroup.LayoutParams layoutParams = id_path_view_line.getLayoutParams();
        layoutParams.height = screenH;
        layoutParams.width = screenW;
        id_path_view_line.setLayoutParams(layoutParams);

        Path path = new Path();
        path.moveTo(screenW, 0);
        path.lineTo(0, 0);

        id_path_view_line.setPath(path);
        ViewAnimation animation = new ViewAnimation(id_path_view_line);
        animation.addPageAnimation(new WoWPathAnimation(
                0, 0f, 1f,
                EaseType.Linear,
                true));
        id_view_pager_normal.addAnimation(animation);
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
