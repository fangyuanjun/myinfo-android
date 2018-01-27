package com.kecq.myinfo;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import fyj.lib.HttpAsync;
import fyj.lib.HttpResult;
import fyj.lib.SignHelper;
import fyj.lib.android.DialogHelper;
import fyj.lib.android.update.IUpdateCallback;
import fyj.lib.common.HttpHelperAsync;


public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉标题栏  方法1 无效？
        // 去掉标题栏 方法2  StartActivity继承AppCompatActivity   无效?
        // if (getSupportActionBar() != null){
        // getSupportActionBar().hide();
        //  }

        //全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_start);

		/*
        UpdateManager update=new UpdateManager(this,callbak);
		update.checkUpdateInfo();
		*/

        Init();

        //InitTestActivity();
    }

    //测试用  直接登录跳到主界面
    private void InitTest()
    {
        SharedPreferences settings = getSharedPreferences(Temp.SP_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(Temp.USER_ID, "8dc2885cd7bb44aa9344557dbc0c1630");
        editor.commit();

        Intent intent = new Intent(new Intent(StartActivity.this,
                MainActivity.class));
        //Bundle extras = new Bundle();
        //intent.putExtras(extras);
        startActivity(intent);
        finish();
    }

    private void InitTestActivity()
    {

        Intent intent = new Intent(new Intent(StartActivity.this,
                TestActivity.class));
        startActivity(intent);
        finish();
    }



    private  void Init(){

        final SharedPreferences settings = getSharedPreferences(Temp.SP_NAME, 0);
        String userID = settings.getString(Temp.USER_ID, null);
        //如果登录过
        if (userID != null) {
            Temp.TOKEN=settings.getString(Temp.USER_TOKEN,null);
            String url= Temp.BaseServerUrl+ SignHelper.SignUrl("/User/ValidateTokenAvailable?userID="+userID+"&token="+Temp.TOKEN);
            HttpAsync.DoGet(url, new HttpResult() {
                @Override
                public void Success(String result) {
                    try {
                        JSONObject json = new JSONObject(result);
                        int code=json.optInt("code");
                        if(code>0){
                            String localPassword = settings.getString(Temp.LOCAL_PASSWORD, null);
                            //如果需要输入本地解锁密码
                            if (localPassword != null) {
                                Intent intent = new Intent(new Intent(StartActivity.this,
                                        InputLocalPasswordActivity.class));
                                intent.putExtra("isInitMain",true);
                                startActivity(intent);
                                finish();

                                return;
                            } else {
                                //跳到主页
                                Intent intent = new Intent(new Intent(StartActivity.this,
                                        MainActivity.class));
                                //Bundle extras = new Bundle();
                                //intent.putExtras(extras);
                                startActivity(intent);
                                finish();
                            }
                        }
                        else{
                            Intent intent = new Intent(StartActivity.this,
                                    LoginActivity.class);
                            intent.putExtra("tips","登录过期,请重新登录");
                            startActivity(intent);
                            finish();
                        }
                    } catch (JSONException e) {
                        DialogHelper.showToast("数据解析失败");
                        e.printStackTrace();
                    }
                }

                @Override
                public void Error(Throwable tr) {
                    DialogHelper.showToast("初始化失败");
                }
            });

        } else {
            Intent intent = new Intent(StartActivity.this,
                    LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void Init_old(){
         /*
        //当计时结束时，跳转至主界面
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(StartActivity.this, MainActivity.class));
                StartActivity.this.finish();
            }
        }, 3000);
       */



        //后台处理耗时任务
        new Thread(new Runnable() {
            @Override
            public void run() {
                //耗时任务
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences settings = getSharedPreferences(Temp.SP_NAME, 0);
                        String userID = settings.getString(Temp.USER_ID, null);
                        //如果登录过
                        if (userID != null) {
                            Temp.TOKEN=settings.getString(Temp.USER_TOKEN,null);
                            String localPassword = settings.getString(Temp.LOCAL_PASSWORD, null);
                            //为静态变量赋值

                            //如果需要输入本地解锁密码
                            if (localPassword != null) {
                                Intent intent = new Intent(new Intent(StartActivity.this,
                                        InputLocalPasswordActivity.class));
                                intent.putExtra("isInitMain",true);
                                startActivity(intent);
                                finish();

                                return;
                            } else {
                                //跳到主页
                                Intent intent = new Intent(new Intent(StartActivity.this,
                                        MainActivity.class));
                                //Bundle extras = new Bundle();
                                //intent.putExtras(extras);
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            Intent intent = new Intent(StartActivity.this,
                                    LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        }).start();

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //不知道为什么，不管是否点击安装、取消，返回的resultCode都是0，即RESULT_CANCELED，待查
        if (resultCode == RESULT_CANCELED) {
            if (requestCode == 0) {
                // start();
            }
        }
    }

    IUpdateCallback callbak = new IUpdateCallback() {

        @Override
        public void checkUpdateCompleted(boolean isUpdate) {
            // TODO Auto-generated method stub
            if (isUpdate == false) {

            }
        }

        @Override
        public void downloadCanceled() {
            // TODO Auto-generated method stub

        }

        @Override
        public void downloadCompleted(Boolean sucess, CharSequence errorMsg) {
            // TODO Auto-generated method stub
            if (sucess == false) {
                DialogHelper.showToast("更新错误");
            } else {
                java.io.File apkFile = new java.io.File((String) errorMsg);
                if (apkFile.exists()) {
                    //不能使用Intent.FLAG_ACTIVITY_NEW_TASK是因为Intent.FLAG_ACTIVITY_NEW_TASK无法获得返回的结果；不能使用Intent.FLAG_ACTIVITY_CLEAR_TOP是因为可能会有多个apk同时安装
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);//或FLAG_ACTIVITY_PREVIOUS_IS_TOP
                    i.setDataAndType(Uri.parse("file://" + apkFile.toString()), "application/vnd.android.package-archive");
                    startActivityForResult(i, 0);
                }
            }
        }

        @Override
        public void checkCanceled() {
            // TODO Auto-generated method stub

        }

    };
}
