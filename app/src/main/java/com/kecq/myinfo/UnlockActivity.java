package com.kecq.myinfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class UnlockActivity extends AppCompatActivity {

    private EditText editText;
    private String localPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {  //版本检测
            Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
            mToolbar.setTitle(getIntent().getStringExtra("articleTitle"));
            setSupportActionBar(mToolbar);  //将ToolBar设置成ActionBar
        }

        ActionBar actionBar =  getSupportActionBar();
        if(actionBar != null) {
            //如果是初始界面  设置返回按钮不可用
            if(getIntent().getBooleanExtra("isInitMain",false)){
                //actionBar.setDisplayHomeAsUpEnabled(false); //设置返回键不可用
            }
            else{
                actionBar.setDisplayHomeAsUpEnabled(true); //设置返回键可用
            }
        }

        SharedPreferences settings = getSharedPreferences(Temp.SP_NAME, 0);
        localPassword = settings.getString(Temp.LOCAL_PASSWORD, null);
        this.setTitle("解锁");
        editText=(EditText)findViewById(R.id.unlock_password);
        EditTextListener watcher=new EditTextListener();
        editText.addTextChangedListener(watcher);
        new Handler().postDelayed(initTask, 1000);
    }

    Runnable initTask = new Runnable() {
        public void run() {
            // TODO Auto-generated method stub
            InputMethodManager inputManager = (InputMethodManager) getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    private class EditTextListener implements TextWatcher
    {

        public void afterTextChanged(Editable arg0) {
            // TODO Auto-generated method stub
            if(localPassword.equals(editText.getText().toString()))
            {
                if(getIntent().getBooleanExtra("isInitMain",false)){
                    Intent intent = new Intent(new Intent(UnlockActivity.this,
                            MainActivity.class));
                    startActivity(intent);
                    finish();
                }
                else{
                    SharedPreferences settings = getSharedPreferences(Temp.SP_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.remove(Temp.LOCAL_PASSWORD);
                    editor.commit();

                    finish();
                }

            }
        }

        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
            // TODO Auto-generated method stub

        }

        public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                  int arg3) {
            // TODO Auto-generated method stub

        }

    }
}
