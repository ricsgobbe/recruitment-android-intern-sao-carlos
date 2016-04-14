package com.example.eunicekuba.bmiproject.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.eunicekuba.bmiproject.R;

/**
 * Created by eu.nicekuba on 10/04/2016.
 */
public class WebFragment extends Fragment {

    private WebView mWebView;
    private String mURL;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_fragment_web, container, false);
        mWebView = (WebView) v.findViewById(R.id.id_webview);
        configWebView();
        Bundle bundle = this.getArguments();
        mURL = bundle.getString("URL");
        mWebView.loadUrl(mURL);

        return v;
    }

    private void configWebView() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadsImagesAutomatically(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });
    }
}
