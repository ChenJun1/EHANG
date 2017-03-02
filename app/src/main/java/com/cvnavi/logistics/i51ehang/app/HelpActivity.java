package com.cvnavi.logistics.i51ehang.app;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cvnavi.logistics.i51ehang.app.widget.dialog.SweetAlert.SweetAlertDialog;

import static android.webkit.WebSettings.LOAD_NO_CACHE;

/**
 * 版权所有 上海势航
 *
 * @author chenJun and johnnyYuan
 * @version 1.0.0
 * @description
 * @date 2016-5-17 下午1:12:36
 * @email yuanlunjie@163.com
 */
public class HelpActivity extends BaseActivity implements View.OnClickListener {

    private TextView titlt = null;
    private WebView webView = null;
    private LinearLayout back_linearLayout = null;
    private SweetAlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        titlt = (TextView) findViewById(R.id.title_tv);
        webView = (WebView) findViewById(R.id.webview);
        webView.setOnClickListener(this);
        back_linearLayout = (LinearLayout) findViewById(R.id.back_llayout);
        back_linearLayout.setOnClickListener(this);
        titlt.setText("帮助手册");
        dialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDefaultTextEncodingName("GBK");
        webView.getSettings().setCacheMode(LOAD_NO_CACHE);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                dialog.dismiss();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                dialog.show();
            }
        });
        webView.loadUrl(Constants.HelpManual_URL);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_llayout:
                finish();
                break;
        }

    }
}
