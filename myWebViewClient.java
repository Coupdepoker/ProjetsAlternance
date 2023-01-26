package com.example.euglohapp;

import android.content.Intent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class myWebViewClient extends WebViewClient {
    private boolean res = false;
    private WebViewExample webViewExample;

    public myWebViewClient(WebViewExample webViewExample) {
        this.webViewExample = webViewExample;
    }

    public void onPageFinished(WebView webView, String url) {
        if(url.equals("https://adonis.u-psud.fr/utilisateur/index.php")){
            res = true;
            Intent intent = new Intent(webViewExample,MainActivity2.class);
            webViewExample.startActivity(intent);

        }
    }

    public boolean getResRam(){
        return res;
    }

}
