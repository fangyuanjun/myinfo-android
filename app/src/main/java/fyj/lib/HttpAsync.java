package fyj.lib;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kecq.myinfo.ApplicationEx;

import org.json.JSONObject;
import java.util.Map;


/**
 * Created by fyj on 2017/12/11.
 */

public class HttpAsync {

    private  static  RequestQueue mQueue=Volley.newRequestQueue(ApplicationEx.getContext());

    public static void DoGet(String url, final HttpResult result) {
        Log.i("GET",url);
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.d("HttpAsync DoGet Error Success", response.toString());
                        result.Success(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                result.Error(error);
                Log.e("HttpAsync DoGet Error", error.getMessage(), error);
            }
        });

        //Volley中没有指定的方法来设置请求超时时间，可以设置RetryPolicy 来变通实现。DefaultRetryPolicy类有个initialTimeout参数，可以设置超时时间。要确保最大重试次数为1，以保证超时后不重新请求。
        mStringRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 1, 1.0f));
         //DefaultRetryPolicy.DEFAULT_MAX_RETRIES  默认最大尝试次数
        //DefaultRetryPolicy.DEFAULT_BACKOFF_MULT  ​对于请求失败之后的请求，并不会隔相同的时间去请求Server，不会以线性的时间增长去请求，而是一个曲线增长，一次比一次长，如果backoff因子是2，当前超时为3，即下次再请求隔6S​
        //mStringRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //将请求添加在请求队列中
        mQueue.add(mStringRequest);
    }

    public static void DoPost(String url,final Map<String, String> maps, final HttpResult result) {
        Log.i("POST",url);
        StringRequest mStringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.d("HttpAsync DoGet Error Success", response.toString());
                        result.Success(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                result.Error(error);
                Log.e("HttpAsync DoGet Error", error.getMessage(), error);
            }


        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return maps;
            }
        };
        //Volley中没有指定的方法来设置请求超时时间，可以设置RetryPolicy 来变通实现。DefaultRetryPolicy类有个initialTimeout参数，可以设置超时时间。要确保最大重试次数为1，以保证超时后不重新请求。
        mStringRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 1, 1.0f));
        //将请求添加在请求队列中
        mQueue.add(mStringRequest);
    }


}
