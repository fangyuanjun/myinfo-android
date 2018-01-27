package com.kecq.myinfo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.kecq.myinfo.fragment.MyProfileFragment;

import fyj.lib.android.DialogHelper;

public class LockActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {  //版本检测
            Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
            mToolbar.setTitle(getIntent().getStringExtra("articleTitle"));
            setSupportActionBar(mToolbar);  //将ToolBar设置成ActionBar
        }

        ActionBar actionBar =  getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true); //设置返回键可用
        }
    }

    public void lock_btnLockClick(View v)
    {
        EditText editText1=(EditText)findViewById(R.id.lock_password1);
        EditText editText2=(EditText)findViewById(R.id.lock_password2);
        if(editText1.getText().toString().length()<4)
        {
            DialogHelper.showToast("密码不能小于4位");
            return ;
        }

        if(!editText1.getText().toString().equals(editText2.getText().toString()))
        {
            DialogHelper.showToast("两次密码不一致");
            return ;
        }

        //getIntent() 就可以了 不用new
        //Intent intent=new Intent();  //获取还是 new
        //intent.setClass(this, MyProfileFragment.class);
        //intent.putExtra(Temp.LOCAL_PASSWORD,editText1.getText().toString() );

        //setResult(5,getIntent());

        SharedPreferences settings = getSharedPreferences(Temp.SP_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(Temp.LOCAL_PASSWORD, editText1.getText().toString());
        editor.commit();
        this.finish();
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
