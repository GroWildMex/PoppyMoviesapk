package com.poppymovies.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    public static final String SITE_URL = "https://heartivelovestv.pages.dev";

    private WebView webView;
    private String rebrandJs;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize AdMob
        MobileAds.initialize(this, initializationStatus -> {});

        FrameLayout root = new FrameLayout(this);
        setContentView(root);

        // WebView
        webView = new WebView(this);
        FrameLayout.LayoutParams webParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        webParams.bottomMargin = 120; // space for banner
        root.addView(webView, webParams);

        // Real Banner Ad
        AdView adView = new AdView(this);
        adView.setAdUnitId("ca-app-pub-8495478394744279/3857000788");
        adView.setAdSize(AdSize.BANNER);
        FrameLayout.LayoutParams adParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        adParams.gravity = android.view.Gravity.BOTTOM;
        root.addView(adView, adParams);

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        rebrandJs = loadAsset("rebrand.js");

        WebSettings ws = webView.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setDomStorageEnabled(true);
        ws.setMediaPlaybackRequiresUserGesture(false);
        ws.setUseWideViewPort(true);
        ws.setLoadWithOverviewMode(true);

        webView.setBackgroundColor(0xFF12060A);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                view.evaluateJavascript(rebrandJs, null);
            }
        });

        webView.loadUrl(SITE_URL);
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private String loadAsset(String name) {
        try (InputStream is = getAssets().open(name);
             BufferedReader r = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) sb.append(line).append('\n');
            return sb.toString();
        } catch (Exception e) {
            return "/* rebrand.js missing */";
        }
    }
}
