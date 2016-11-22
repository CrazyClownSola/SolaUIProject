package com.sola.github.solauiproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.sola.github.solauiproject.param.UserInfoDTO;
import com.sola.github.solauiproject.tools.DefaultWebKitClient;
import com.sola.github.solauiproject.tools.MyWebChromeClient;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EditorAction;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

/**
 * 实在忍不住了，修改一下WebView容器界面的代码，相对完善一些功能
 * <p>
 * author: Sola
 * 2016/3/14
 */
@EActivity(R.layout.activity_show_h5_content)
public class WebFixContentActivity extends RxBaseActivity {
    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    @ViewById
    WebView id_web_view;

    @ViewById
    ProgressBar id_progress_bar;

    DefaultWebKitClient kitClient;

    private boolean isLogin;

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
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        initWebSetting();
//        id_web_view.loadUrl("file:///android_asset/index.html");
//        id_web_view.loadUrl(itemDto.getExtraLink());
    }

    @Override
    public void onBackPressed() {
        if (id_web_view.canGoBack()) {
            id_web_view.goBack();
        } else {
            super.onBackPressed();
        }
    }

    // ===========================================================
    // Methods
    // ===========================================================

    @EditorAction
    public void id_edit_clan_name(TextView view, int actionId) {
        if (actionId == EditorInfo.IME_ACTION_DONE && view.getText().length() != 0) {
            String url = view.getText().toString();
            id_web_view.loadData("", "text/html", null);
            id_web_view.loadUrl(url);
            Toast.makeText(mContext.get(), "URL[" + url + "]跳转", Toast.LENGTH_LONG).show();
        }
    }

    @Click
    public void id_btn_change_login(View v) {
        if (isLogin) {
            ((TextView) v).setText("未登录");
        } else {
            ((TextView) v).setText("已登录");
        }
        isLogin = !isLogin;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @SuppressWarnings("deprecation")
    private void initWebSetting() {
        WebSettings webSettings = id_web_view.getSettings();
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);
//        webSettings.setDatabaseEnabled(true);
//        String dataPath = getDir("database", Context.MODE_PRIVATE).getPath();
//        webSettings.setDatabasePath(dataPath);
//        webSettings.setGeolocationEnabled(true);
//        webSettings.setGeolocationDatabasePath(dataPath);
//        webSettings.setBuiltInZoomControls(true);
//        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
//        webSettings.setAllowFileAccess(true);
//        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//        webSettings.setLoadWithOverviewMode(true);
//        webSettings.setSupportZoom(false);
//        webSettings.setAppCacheEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        id_web_view.requestFocusFromTouch();
        kitClient = new DefaultWebKitClient();
        kitClient.setCallback(new DefaultWebKitClient.WebClientCallback() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (view.getTitle() != null && !view.getTitle().isEmpty())
                    id_text_title.setText(view.getTitle());
            }
        });
        id_web_view.setWebViewClient(kitClient);
        MyWebChromeClient client = new MyWebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (id_progress_bar != null) {
                    if (newProgress == 100) {
                        id_progress_bar.setVisibility(View.GONE);
                    } else {
                        if (View.INVISIBLE == id_progress_bar.getVisibility()) {
                            id_progress_bar.setVisibility(View.VISIBLE);
                        }
                        id_progress_bar.setProgress(newProgress);
                    }
                }
            }
        };
        id_web_view.setWebChromeClient(client);
        JsInterface uf = new JsInterface() {
            @JavascriptInterface
            @Override
            public String getUserInfo() {
                String retStr = userInfoStr();
                return retStr;
            }

            @JavascriptInterface
            @Override
            public void switchToLogin() {
                Toast.makeText(mContext.get(), "跳转登录", Toast.LENGTH_LONG).show();
            }

            @JavascriptInterface
            @Override
            public void doCopy(String text) {
                copyStringToSystem(text);
            }

        };
        id_web_view.addJavascriptInterface(uf, "app_bridge");
    }

    @Click
    public void tool_bar_right_button(View v) {
        this.finish();
    }

    private String userInfoStr() {
        UserInfoDTO retDto = new UserInfoDTO();
        if (isLogin) {
//            SDMemoryManage memoryManage = SDMemoryManage.getInstance(mContext.get());
            retDto.setAccessToken("token___");
            retDto.setUserId("u_id");
            retDto.setUsername("user_name");
            retDto.setNickname("nick_name");
            retDto.setGender("gender");
            retDto.setHeadPortrait("head_portrait");
        } else {
//            new AlertDialog.Builder(mContext.get())
//                    .setMessage("这位客官，您还没没有登录，请先登录")
//                    .setPositiveButton("登录", (dialog, which) -> {
//                        // 跳转到修改记录界面
////                        Navigator_Factory.create().get().switchActivity(mContext.get(),
////                                LoginActivity_.class);
//                        Toast.makeText(mContext.get(), "跳转登录", Toast.LENGTH_LONG).show();
//                        dialog.dismiss();
//                    })
//                    .setNegativeButton("取消", (dialog, which1) -> dialog.dismiss())
//                    .show();
        }
        return new Gson().toJson(retDto);
    }

    @SuppressWarnings("deprecation")
    public boolean copyStringToSystem(String text) {
        try {
            if (android.os.Build.VERSION.SDK_INT > 11) {
                android.content.ClipboardManager c = (android.content.ClipboardManager)
                        mContext.get().getSystemService(Context.CLIPBOARD_SERVICE);
                c.setText(text);
            } else {
                android.text.ClipboardManager c = (android.text.ClipboardManager)
                        mContext.get().getSystemService(Context.CLIPBOARD_SERVICE);
                c.setText(text);
            }
        } catch (Exception e) {
            return false;
        }
//        Toast.makeText(mContext.get(), HtmlUtils.fromHtml(getString(R.string.string_copy_system_text, text)), Toast.LENGTH_SHORT).show();
        return true;
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

    public interface JsInterface {

        @SuppressWarnings("unused")
        @JavascriptInterface
        String getUserInfo();

        @SuppressWarnings("unused")
        @JavascriptInterface
        void switchToLogin();

        @SuppressWarnings("unused")
        @JavascriptInterface
        void doCopy(String text);

    }

}
