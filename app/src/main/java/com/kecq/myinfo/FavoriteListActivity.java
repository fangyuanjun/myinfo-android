package com.kecq.myinfo;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.kecq.myinfo.fragment.FavoriteListFragment;
import com.kecq.myinfo.fragment.MyregisterListFragment;

import fyj.lib.SignHelper;

public class FavoriteListActivity extends BaseListActivity {

    private FavoriteListFragment fragment=null;
    private CateSelectWindow popupwindow;
    @Override
    protected boolean isShowCateButton() {
        return  true;
    }

    @Override
    protected void initCateWindow() {
        popupwindow =new CateSelectWindow(this, new CateSelectWindow.OnItemSelectListener() {
            @Override
            public void onItemSelect(String id) {
                doSearch(id);
                popupwindow.dismiss();
            }

            @Override
            public String getCategoryDateUrl() {
                return  Temp.MyInfoBaseUrl+ SignHelper.SignUserUrl("/FavoriteCate/SelectData");
            }

        });
    }

    @Override
    protected void onCateButtonClick() {
        popupwindow.showAsDropDown(findViewById(R.id.menu_list_cate), 0, 10);
    }

    @Override
    protected Fragment getFragment() {
        if(fragment==null)
        {
            fragment=new FavoriteListFragment();
        }

        return fragment;
    }

    @Override
    protected void doSearch(String search) {
        fragment.doSearch(search);
    }

    @Override
    protected String getBarTitle() {
        return "收藏夹";
    }

    @Override
    protected boolean showSearchButton() {
        return true;
    }

    @Override
    protected boolean showPlusButton() {
        return false;
    }

    @Override
    protected void jumpPlusPage() {

    }
}
