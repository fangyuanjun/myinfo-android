package com.kecq.myinfo;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import fyj.lib.HttpAsync;
import fyj.lib.HttpResult;
import fyj.lib.LogHelper;
import fyj.lib.android.DialogHelper;

public class ArticleShowActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_show);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {  //版本检测
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            mToolbar.setTitle(getIntent().getStringExtra("articleTitle"));
            setSupportActionBar(mToolbar);  //将ToolBar设置成ActionBar
        }

        ActionBar actionBar =  getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); //设置返回键可用
        }

        Intent getIntent = getIntent();
        long articleID = getIntent.getLongExtra("articleID", 0);
        //LoadData(articleID);

        WebView webView=findViewById(R.id.webView);

        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
       /* webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                ProgressBar progressBar=findViewById(R.id.progressBar1);
                if(newProgress==100){
                    progressBar.setVisibility(View.GONE);//加载完网页进度条消失
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    progressBar.setProgress(newProgress);//设置进度值
                }
            }
        });*/

        String url=getIntent.getStringExtra("articleUrl");

        //覆盖系统浏览器打开，使目标在webview中打开
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;//ture为在webview中打开
            }
        });
        webView.loadUrl(url);
    }

    private void LoadData(long articleID) {
        Loading.showWaiting(this, "获取数据...", true);
        HttpAsync.DoGet(Temp.BaseServerUrl + "/api/Article/QuerySingle?id=" + articleID, new HttpResult() {
            @Override
            public void Success(String result) {
                Loading.hideWaiting(ArticleShowActivity.this);
                try {
                    JSONObject json = new JSONObject(result);
                    int code = json.optInt("code");
                    if (code > 0) {
                        JSONObject itemobj = json.getJSONObject("data");
                        mToolbar.setTitle(itemobj.getString("articleTitle"));
                        String content = itemobj.getString("articleContent");
                        //TextView textView = findViewById(R.id.textView);
                        //textView.setText(Html.fromHtml(content));
                        WebView webView=findViewById(R.id.webView);
                        webView.loadUrl("http://fyj.me/artic-"+itemobj.getString("articleID")+".html");
                    } else {
                        DialogHelper.showToast(json.optString("message"));
                    }
                } catch (JSONException e) {
                    DialogHelper.showToast("获取数据失败");
                    LogHelper.DoException(e);
                }
            }

            @Override
            public void Error(Throwable tr) {
                Loading.hideWaiting(ArticleShowActivity.this);
                DialogHelper.showToast(tr.getMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
