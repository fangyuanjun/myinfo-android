package com.kecq.myinfo;


import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.kecq.myinfo.fragment.MyregisterListFragment;

import fyj.lib.SignHelper;

public abstract class BaseListActivity extends AppCompatActivity {

    protected  abstract Fragment getFragment();

    protected  abstract void doSearch(String search);

    protected  abstract  String getBarTitle();

    protected  abstract  boolean showSearchButton();

    protected abstract boolean showPlusButton();

    //跳转到添加数据页面
    protected abstract  void jumpPlusPage();


    protected boolean isShowCateButton()
    {
        return  false;
    }

    protected void initCateWindow()
    {

    }

    protected  void onCateButtonClick()
    {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_list);
        initTopBar();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // ---get the current display info---
        //WindowManager wm = getWindowManager();
        //Display d = wm.getDefaultDisplay();
        //if (d.getWidth() > d.getHeight())
        //fragmentTransaction.replace(android.R.id.content, new MyregisterListFragment());
        fragmentTransaction.replace(R.id.fragment_container, getFragment());
        // ---add to the back stack---
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        initCateWindow();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list,menu);
        MenuItem searchItem = menu.findItem(R.id.menu_list_search);
        //通过MenuItem得到SearchView
        SearchView mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        //搜索框文字变化监听
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                doSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }
        });

        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                doSearch(null);
                return false;
            }
        });

        return  true;
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

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.menu_list_plus).setVisible(showPlusButton());
        menu.findItem(R.id.menu_list_search).setVisible(showSearchButton());
        menu.findItem(R.id.menu_list_cate).setVisible(isShowCateButton());
        return super.onPrepareOptionsMenu(menu);
    }

    private void initTopBar(){
        Toolbar toolbar=null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {  //版本检测
            //SystemBarTintManager tintManager = new SystemBarTintManager(this);
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle(getBarTitle());
            //toolbar.setSubtitle("SubTitle");
            //tintManager.setStatusBarTintEnabled(true);  //更改状态栏设置

            //tintManager.setStatusBarTintResource(android.R.color.holo_blue_bright);
        }

        //设置导航图标要在setSupportActionBar方法之后
        setSupportActionBar(toolbar);

        //toolbar.setNavigationIcon(R.mipmap.ic_drawer_home);
        ActionBar actionBar =  getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); //设置返回键可用
            //actionBar.setDisplayShowTitleEnabled(false);
            //actionBar.setDisplayShowHomeEnabled(true);
        }

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_list_plus:
                        jumpPlusPage();
                        /*Intent intent = new Intent(MyregisterListActivity.this, MyregisterShowActivity.class);
                        intent.putExtra("id","");
                        intent.putExtra("url",Temp.MyInfoBaseUrl+ SignHelper.SignUserUrl("/MyRegister/Edit/0") );

                        startActivityForResult(intent,1);*/
                        break;
                    case R.id.menu_list_cate:
                        onCateButtonClick();
                        break;
                }
                return true;
            }
        });
    }
}
