package com.kecq.myinfo;

import android.webkit.JavascriptInterface;

/**
 * Created by fyj on 2017/12/22.
 */

public class JsInteration {

    @JavascriptInterface
    public  String back(String value,String value2){
        return  "JAVA里面返回的值,参数"+value+","+value2;
    }

}
