package com.sola.github.solauiproject.tools;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * author: Sola
 * 2016/3/14
 */
public class DefaultWebKitClient extends WebViewClient {
    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    private WebClientCallback callback;

    // ===========================================================
    // Constructors
    // ===========================================================

    public DefaultWebKitClient() {
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    public WebClientCallback getCallback() {
        return callback;
    }

    public void setCallback(WebClientCallback callback) {
        this.callback = callback;
    }

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (url.startsWith("alipays:") || url.startsWith("alipay")) {
            try {
                view.getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
            } catch (Exception e) {
                new AlertDialog.Builder(view.getContext())
                        .setMessage("未检测到支付宝客户端，请安装后重试。")
                        .setPositiveButton("立即安装", (dialog, which) -> {
                            Uri alipayUrl = Uri.parse("https://d.alipay.com");
                            view.getContext().startActivity(
                                    new Intent("android.intent.action.VIEW", alipayUrl));
                        })
                        .setNegativeButton("取消", null).show();
            }
        }
        view.loadUrl(url);
        return true;
//        return super.shouldOverrideUrlLoading(view, url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        if (callback != null)
            callback.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        if (callback != null)
            callback.onPageFinished(view, url);
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
    }

    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

    public interface WebClientCallback {

        void onPageStarted(WebView view, String url, Bitmap favicon);

        void onPageFinished(WebView view, String url);
    }

}
