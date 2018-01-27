package com.kecq.myinfo;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_web_view);

        Intent getIntent = getIntent();
        String title=getIntent.getStringExtra("title");
        String url=getIntent.getStringExtra("url");
        if(title==null||title.equals(""))
        {
            title="详情";
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {  //版本检测
            Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
            mToolbar.setTitle(title);
            setSupportActionBar(mToolbar);  //将ToolBar设置成ActionBar
        }

        ActionBar actionBar =  getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); //设置返回键可用
        }

        WebView webView=findViewById(R.id.webView);

        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //覆盖系统浏览器打开，使目标在webview中打开
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;//ture为在webview中打开
            }
        });
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

       if(url!=null&&!url.equals("")){
           webView.loadUrl(url);
       }
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
