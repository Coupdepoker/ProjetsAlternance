package com.example.euglohapp;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class WebViewExample extends AppCompatActivity {
    private WebView webView;
    String[] currenturl;
    Runnable myRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_example);
        webView = findViewById(R.id.webview);
        webView.setWebViewClient(new myWebViewClient(WebViewExample.this));
        webView.loadUrl("https://adonis.u-psud.fr/utilisateur/index.php");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

    }

    public WebView getWebView() {
        return webView;
    }
}

