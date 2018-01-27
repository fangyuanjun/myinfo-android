package com.kecq.myinfo;


import android.support.v4.app.Fragment;
import android.content.Intent;
import com.kecq.myinfo.fragment.NoteListFragment;

import fyj.lib.SignHelper;

public class NoteListActivity extends BaseListActivity {

    private NoteListFragment fragment=null;

    @Override
    protected Fragment getFragment() {
        if(fragment==null)
        {
            fragment=new NoteListFragment();
        }

        return fragment;
    }

    @Override
    protected void doSearch(String search) {
        fragment.doSearch(search);
    }

    @Override
    protected String getBarTitle() {
        return "我的随记";
    }

    @Override
    protected boolean showSearchButton() {
        return false;
    }

    @Override
    protected boolean showPlusButton() {
        return true;
    }

    @Override
    protected void jumpPlusPage() {
        Intent intent = new Intent(NoteListActivity.this, MyWebViewActivity.class);
        intent.putExtra("id","");
        intent.putExtra("title","添加我的随记");
        intent.putExtra("url",Temp.MyInfoBaseUrl+ SignHelper.SignUserUrl("/Note/PhoneEdit/0") );

        startActivityForResult(intent,1);
    }

}
