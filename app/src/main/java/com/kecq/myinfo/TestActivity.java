package com.kecq.myinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import cz.msebera.android.httpclient.Header;
import fyj.lib.SignHelper;
import fyj.lib.android.DialogHelper;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

    }

    public  void  testButtonClick(View view){
       //String url=Temp.BaseServerUrl + SignHelper.SignUserUrl("/api/Article/QueryList");
        if(view.getId()==R.id.btn_contact)
        startActivity(new Intent(getApplication(),ContactActivity.class));

        if(view.getId()==R.id.btn_sms)
            startActivity(new Intent(getApplication(),SmsActivity.class));

        if(view.getId()==R.id.btn_call)
            startActivity(new Intent(getApplication(),CallActivity.class));

        if(view.getId()==R.id.btn_upload)
            startActivity(new Intent(getApplication(),UploadActivity.class));

        if(view.getId()==R.id.btn_download)
            startActivity(new Intent(getApplication(),DownloadActivity.class));

    }

    private  void DoGet()
    {
        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, "https://passport.kecq.com/Login",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //DialogHelper.showToast(response);
                        Log.d("wangshu", response.toString());
                        //Loading.hideWaiting(LoginActivity.this);
                        ((EditText) findViewById(R.id.login_loginName)).setText("ok");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("wangshu", error.getMessage(), error);
                //Loading.hideWaiting(LoginActivity.this);
                DialogHelper.showToast(error.getMessage());
            }
        });
        //将请求添加在请求队列中
        mQueue.add(mStringRequest);
    }

    private  void DoGet2(){
         /*
                HttpHelperAsync h=new HttpHelperAsync(new ResponseCallback(){
                    @Override
                    public void Success(String result) {
                       // Log.d("success", result);
                        ((EditText) findViewById(R.id.login_loginName)).setText(result);

                    }

                    @Override
                    public void Requesting() {
                        DialogHelper.showToast("正在登录...");
                    }
                });

                h.DoGet("https://passport.kecq.com/Login");
                h.DoGet("https://passport.kecq.com/Login");
                */
    }

    private  void DoLogin(String loginName,String password)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestHandle requestHandle = client.get("https://passport.kecq.com", new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String reusult=new String(responseBody);
                DialogHelper.showToast("success");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                String reusult=new String(responseBody);
                DialogHelper.showToast("error"+statusCode);
            }


        });

    }

    private  void GetTelphoneInfo()
    {

/*
TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
StringBuilder sb = new StringBuilder();
sb.append("\n手机型号"+android.os.Build.MODEL );
sb.append("\nDeviceId(IMEI) = " + tm.getDeviceId());
sb.append("\nDeviceSoftwareVersion = " + tm.getDeviceSoftwareVersion());
sb.append("\nLine1Number = " + tm.getLine1Number());
sb.append("\nNetworkCountryIso = " + tm.getNetworkCountryIso());
sb.append("\nNetworkOperator = " + tm.getNetworkOperator());
sb.append("\nNetworkOperatorName = " + tm.getNetworkOperatorName());
sb.append("\nNetworkType = " + tm.getNetworkType());
sb.append("\nPhoneType = " + tm.getPhoneType());
sb.append("\nSimCountryIso = " + tm.getSimCountryIso());
sb.append("\nSimOperator = " + tm.getSimOperator());
sb.append("\nSimOperatorName = " + tm.getSimOperatorName());
sb.append("\nSimSerialNumber = " + tm.getSimSerialNumber());
sb.append("\nSimState = " + tm.getSimState());
sb.append("\nSubscriberId(IMSI) = " + tm.getSubscriberId());
sb.append("\nVoiceMailNumber = " + tm.getVoiceMailNumber());
Log.e("info", sb.toString());
*/


/*
		SoapObject rpc =new SoapObject("http://tempuri.org/","GetUserInfo");
		rpc.addProperty("loginName", loginName);
		rpc.addProperty("userPassword", Encrypt(userPassword, "SHA-1").toUpperCase());
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.bodyOut = rpc;
		envelope.dotNet = true;
		envelope.setOutputSoapObject(rpc);
		HttpTransportSE httpTranstation=new HttpTransportSE("http://user.ztku.com/Service/User.svc");
    	httpTranstation.debug=true;
    	httpTranstation.call("http://tempuri.org/IUserService/GetUserInfo", envelope);
    	Object result=envelope.getResponse();
    	*/
    }

    //获取分辨率
    private  void GetDisplayInfo()
    {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int displayWidth = dm.widthPixels;// 获取分辨率宽度
    }
}
