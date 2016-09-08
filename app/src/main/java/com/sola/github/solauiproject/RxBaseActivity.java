package com.sola.github.solauiproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.lang.ref.WeakReference;

/**
 * 复用代码，项目中通用的有Toolbar的Activity
 * <p/>
 * Created by 禄骥
 * 2016/4/26.
 */
@EActivity
public abstract class RxBaseActivity extends AppCompatActivity {
    // ===========================================================
    //
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    @ViewById
    Toolbar id_tool_bar;

    @ViewById
    TextView id_text_title;

    @Extra("menu_id")
    int menu_id = -1;

    @Extra
    String title;

    protected WeakReference<Context> mContext = new WeakReference<Context>(this);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        if (menu_id == -1)
            return super.onCreateOptionsMenu(menu);
        else {
            getMenuInflater().inflate(menu_id, menu);
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: //注意这里是设置了getSupportActionBar().setDisplayHomeAsUpEnabled(true);之后才有效的
                onBackPressed();// 当返回键被主动点击之后
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        overridePendingTransition(R.anim.fade_in_left, R.anim.activity_back);
    }

    // ===========================================================
    // Methods
    // ===========================================================

    @AfterViews
    protected void afterViews() {
        if (id_text_title != null)
            id_text_title.setText(title);
        if (id_tool_bar != null) {
            id_tool_bar.setTitle("");// 这个是由于希望界面当中的Title的
            setSupportActionBar(id_tool_bar);// 这句话是为了让下面的调用系统返回键成立
            // 设置系统默认的back键
            if (getSupportActionBar() != null)
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        doAfterViews();
    }


    /**
     * @param newFrag    新的Fragment
     * @param isDestory  <code>false</code> 自定义返回键操作
     *                   {@link FragmentManager#popBackStack()},会依次唤醒在堆栈内,标志位为false的
     *                   {@link Fragment}; 当popBackStack()调用的是自身的时候,进入Fragment的自行销毁流程
     *                   <code>true</code> 自定义返回键操作
     *                   {@link FragmentManager#popBackStack()}
     *                   ,不会对该标志位为true的任何Fragment产生影响,不会调用该Fragment的生命周期,想要调用该声明周期的销毁流程
     *                   ,请参见方法{@link FragmentTransaction#remove(Fragment)}
     * @param resourceId 所要替换的Fragment的ResourceId
     */
    @SuppressWarnings("unused")
    public void replaceFragment(Fragment newFrag, boolean isDestory,
                                int resourceId) {
        if (getSupportFragmentManager() != null) {
            // mManager.
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(resourceId, newFrag);
            if (!isDestory)
            /****
             * 在后退栈中保存被替换的Fragment的状态 添加这句话后， 用户按返回键会将前面的所有动作反向执行一次（事务回溯）
             ***/
                transaction.addToBackStack(null);
            transaction.commitAllowingStateLoss();
        }
    }

    /**
     * 单向性质的添加{@link Fragment}到 @param resourceId中去,只是添加
     *
     * @param newFrag    新的Fragment
     * @param isDestory  {@see replaceFragment()}
     * @param resourceId 所要替换的Fragment的ResourceId
     */
    @SuppressWarnings("UnusedDeclaration")
    public void addFragment(Fragment newFrag, boolean isDestory, int resourceId) {
        if (getSupportFragmentManager() != null) {
            // mManager.
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(resourceId, newFrag, null);
            if (!isDestory)
            /****
             * 在后退栈中保存被替换的Fragment的状态 添加这句话后， 用户按返回键会将前面的所有动作反向执行一次（事务回溯）
             ***/
                transaction.addToBackStack(null);
            transaction.commitAllowingStateLoss();
        }
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

    /**
     * 让子类继承去实现界面更新操作
     */
    protected abstract void doAfterViews();

}
