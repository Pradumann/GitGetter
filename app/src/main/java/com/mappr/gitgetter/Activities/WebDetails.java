package com.mappr.gitgetter.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.mappr.gitgetter.Global.CommonFunctions;
import com.mappr.gitgetter.Global.GitGetterSingleton;
import com.mappr.gitgetter.R;

public class WebDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Details");
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();

        if(extras != null){
            String url = extras.getString(GitGetterSingleton.getInstance().getURL_KEY());
            setAndOpenWebView(url);
        }else {
            Toast.makeText(WebDetails.this, "No url to open..!", Toast.LENGTH_LONG).show();
        }

    }

    private void setAndOpenWebView(String url){
        WebView webView = (WebView) findViewById(R.id.web_view);
        webView.setWebViewClient(new MYWebViewClient());
        webView.loadUrl(url);
    }

    private class MYWebViewClient extends WebViewClient {
        CommonFunctions common = new CommonFunctions(WebDetails.this);
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error){

            common.showToast("Your Internet Connection May not be active Or " + error);
        }
    }
}
