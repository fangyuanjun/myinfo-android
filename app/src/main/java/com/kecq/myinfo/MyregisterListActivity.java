package com.kecq.myinfo;


import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.kecq.myinfo.fragment.MyregisterListFragment;

import fyj.lib.SignHelper;


public class MyregisterListActivity extends BaseListActivity {

    private MyregisterListFragment fragment=null;

    @Override
    protected Fragment getFragment() {
        if(fragment==null)
        {
            fragment=new MyregisterListFragment();
        }

        return fragment;
    }

    @Override
    protected void doSearch(String search) {
        fragment.doSearch(search);
    }

    @Override
    protected String getBarTitle() {
        return "注册信息";
    }

    @Override
    protected boolean showSearchButton() {
        return true;
    }

    @Override
    protected boolean showPlusButton() {
        return true;
    }

    @Override
    protected void jumpPlusPage() {
        Intent intent = new Intent(MyregisterListActivity.this, MyWebViewActivity.class);
        intent.putExtra("id","");
        intent.putExtra("title","添加注册信息");
        intent.putExtra("url",Temp.MyInfoBaseUrl+ SignHelper.SignUserUrl("/MyRegister/Edit/0") );

        startActivityForResult(intent,1);
    }

}
