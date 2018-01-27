package com.kecq.myinfo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import fyj.lib.android.DialogHelper;

public class InputLocalPasswordActivity extends AppCompatActivity {
    private EditText editText;
    private String localPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_local_password);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {  //版本检测
            Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
            mToolbar.setTitle(getIntent().getStringExtra("articleTitle"));
            setSupportActionBar(mToolbar);  //将ToolBar设置成ActionBar
        }

        ActionBar actionBar =  getSupportActionBar();
        if(actionBar != null) {
            //actionBar.setDisplayHomeAsUpEnabled(false); //设置返回键不可用
        }

        SharedPreferences settings = getSharedPreferences(Temp.SP_NAME, 0);
        localPassword = settings.getString(Temp.LOCAL_PASSWORD, null);

        editText=(EditText)findViewById(R.id.unlock_password);
        InputLocalPasswordActivity.EditTextListener watcher=new InputLocalPasswordActivity.EditTextListener();
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

    private boolean isExit;

    //onKeyDown  无效  应该是ViewPaer的问题
    @Override
    public boolean dispatchKeyEvent(KeyEvent event)
    {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 ){
            if(event.getAction() != KeyEvent.ACTION_DOWN)  //解决监听2次的问题
            {
                return false;
            }
            exit();
            return false;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }
    public void exit(){
        if (!isExit) {
            isExit = true;
            DialogHelper.showToast("再按一次退出程序");
            existHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            System.exit(0);
        }
    }

    Handler existHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            isExit = false;
        }
    };

    private class EditTextListener implements TextWatcher
    {

        public void afterTextChanged(Editable arg0) {
            // TODO Auto-generated method stub
            if(localPassword.equals(editText.getText().toString()))
            {
                Intent intent = new Intent(new Intent(InputLocalPasswordActivity.this,
                        MainActivity.class));
                startActivity(intent);
                finish();
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