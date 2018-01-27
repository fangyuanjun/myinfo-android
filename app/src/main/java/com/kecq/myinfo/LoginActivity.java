package com.kecq.myinfo;


import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import fyj.lib.HttpAsync;
import fyj.lib.HttpResult;
import fyj.lib.LogHelper;
import fyj.lib.SignHelper;
import fyj.lib.android.DialogHelper;
import fyj.lib.common.SecurityHelper;


public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        String tips=getIntent().getStringExtra("tips");
        if(tips!=null&&!tips.equals("")){
            DialogHelper.showToast(tips);
        }

        Button loginButton=(Button) findViewById(R.id.login_loginButton);

        loginButton.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View view) {

                String loginName = ((EditText) findViewById(R.id.login_loginName)).getText().toString();
                String userPassword = ((EditText) findViewById(R.id.login_userPassword)).getText().toString();

                if(loginName.equals(""))
                {
                    DialogHelper.showToast("用户名不能为空");
                    return;
                }

                if(userPassword.equals(""))
                {
                    DialogHelper.showToast("密码不能为空");
                    return;
                }

                Map<String,String> map=new HashMap<>();
                map.put("loginName",loginName);
                map.put("userPassword", SecurityHelper.sha1Encrypt(userPassword));
                Loading.showWaiting(LoginActivity.this,"正在登录...",true);
                HttpAsync.DoPost(Temp.BaseServerUrl+ SignHelper.SignUrl("/User/LoginByLoginName"),map, new HttpResult() {
                    @Override
                    public void Success(String result)  {

                        Loading.hideWaiting(LoginActivity.this);

                        try {
                            JSONObject json = new JSONObject(result);
                            int code=json.optInt("code");
                            if(code==1){
                                String userID=json.optString("userID");
                                String userToken=json.optString("userToken");
                                //((EditText) findViewById(R.id.login_loginName)).setText(userID);
                                Temp.TOKEN=userToken;
                                SharedPreferences settings = getSharedPreferences(Temp.SP_NAME, 0);
                                SharedPreferences.Editor editor = settings.edit();
                                editor.putString(Temp.USER_ID, userID);
                                editor.putString(Temp.USER_TOKEN, userToken);
                                editor.putString(Temp.USER_NAME,json.optString("userName"));
                                editor.putString(Temp.USER_NUMBER,json.optString("userNumber"));
                                editor.commit();

                                Intent intent = new Intent(new Intent(LoginActivity.this,
                                        MainActivity.class));
                                //Bundle extras = new Bundle();
                                //intent.putExtras(extras);
                                startActivity(intent);
                                finish();

                                //startActivityForResult(intent,0);
                                //finish();   加了这个返回不了..
                            }
                            else{
                                DialogHelper.showToast(json.optString("message"));
                            }
                        } catch (JSONException e) {
                            DialogHelper.showToast("登录失败");
                            LogHelper.DoException(e);
                        }
                    }

                    @Override
                    public void Error(Throwable tr) {
                        Loading.hideWaiting(LoginActivity.this);
                        DialogHelper.showToast("服务器异常");
                        LogHelper.DoException(tr);
                    }
                });

                /*
                HttpAsync.DoGet(Temp.BaseServerUrl+"/User/LoginByLoginName?loginName="+loginName+"&userPassword="+userPassword, new HttpResult() {
                    @Override
                    public void Success(String result) {
                        DialogHelper.showToast("登录成功");
                        Loading.hideWaiting(LoginActivity.this);
                        ((EditText) findViewById(R.id.login_loginName)).setText(result);
                    }
                });
                */
            }
        });
    }
    //忘记密码
    public  void login_forgetPasswdClick(View view)
    {
        DialogHelper.showToast("暂未开放");
    }

    //注册新用户
    public  void login_createNewUserClick(View view)
    {
        DialogHelper.showToast("暂未开放");
    }
}
