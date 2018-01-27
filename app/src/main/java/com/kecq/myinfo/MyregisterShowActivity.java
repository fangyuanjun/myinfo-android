package com.kecq.myinfo;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fyj.lib.HtmlInteration;
import fyj.lib.SignHelper;

public class MyregisterShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myregister_show);
        Toolbar mToolbar=null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {  //版本检测
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(mToolbar);  //将ToolBar设置成ActionBar
        }

        ActionBar actionBar =  getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); //设置返回键可用
        }

        Intent getIntent = getIntent();

        String url=getIntent.getStringExtra("url");
        //貌似无效  cookieManager.removeSessionCookie();    至少 api level 21
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setCookie(getIntent.getStringExtra("url"),"");
        CookieSyncManager.getInstance().sync();

        final WebView webView=findViewById(R.id.webView);
        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // 必须在loadUrl之前设置WebViewClient
      /*  webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                // 这里可以过滤一下url
                webView.loadUrl("javascript:android.processHTML(document.documentElement.outerHTML);");
            }
        });
        webView.addJavascriptInterface(new HtmlInteration(new HtmlInteration.OnHtmlListener() {
            @Override
            public void doHtml(String html) {
                Pattern p = Pattern.compile("url.*?/MyRegister/Edit.*?,");
                Matcher m = p.matcher(html);
                if(m.find()){
                    html=html.replace(m.group(),"url: \""+ SignHelper.SignUserUrl("/MyRegister/Edit/"+getIntent().getStringExtra("id"))+"\"");
                }

                p = Pattern.compile("url.*?/MyRegister/EditModel.*?,");
                m = p.matcher(html);
                if(m.find()){
                    html=html.replace(m.group(),"url: \""+ SignHelper.SignUserUrl("/MyRegister/EditModel/"+getIntent().getStringExtra("id"))+"\"");
                }
                webView.loadDataWithBaseURL(null, "ccccccc我我", "text/html", "utf-8", null);
            }
        }),"android");*/

        webView.loadUrl(url);
        Log.i("GET",url);

        /*String data = " Html 数据";
        webView.loadData(data, "text/html", "utf-8");
        实测会发现loadData会导致中文乱码，所以一般情况使用如下代码

        String data = " Html 数据";
        webView.loadDataWithBaseURL(null,data, "text/html", "utf-8",null);*/
    }

    public  void btnTestClick(View view){
        WebView webView=findViewById(R.id.webView);
        webView.loadUrl("javascript:save()");
        //Android调用有返回值js方法，安卓4.4以上才能用这个方法，当版本低于4.4的时候，常用的思路是 java调用js方法，js方法执行完毕，再次调用java代码将值返回。
       /* webView.evaluateJavascript("sum()", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {

            }
        });*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Intent mIntent = new Intent();
                getIntent().putExtra("id",getIntent().getStringExtra("id"));
                setResult(0,getIntent());
                finish();
                break;
        }
        return true;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event)
    {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK  ){
            if(event.getAction() != KeyEvent.ACTION_DOWN)  //解决监听2次的问题
            {
                return false;
            }

            getIntent().putExtra("id",getIntent().getStringExtra("id"));
            setResult(0,getIntent());
            finish();

            return false;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }
}
