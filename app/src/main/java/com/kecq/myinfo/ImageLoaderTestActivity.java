package com.kecq.myinfo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import fyj.lib.android.BitmapCache;

public class ImageLoaderTestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private  void LoadImg(){
        /*
        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
        ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache());
        ImageView imgView=(ImageView)findViewById(R.id.login_image) ;
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imgView,R.drawable.ic_launcher_background, R.drawable.ic_launcher_foreground);
        imageLoader.get("https://static.kecq.com/style/passport/1.jpg", listener);
        */
    }


    private  void LoadImg2(){
        /*
         <com.android.volley.toolbox.NetworkImageView
        android:id="@+id/network_image_view"
        android:layout_width="200dp"
        android:layout_height="200dp"
        android:layout_gravity="center_horizontal"
                />
        */

        /*
        NetworkImageView iv_image = (NetworkImageView) this.findViewById(R.id.login_createNewUser);
        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
        ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache());
        iv_image.setDefaultImageResId(R.drawable.ic_launcher_background);
        iv_image.setErrorImageResId(R.drawable.ic_launcher_foreground);
        iv_image.setImageUrl("https://static.kecq.com/style/passport/1.jpg",imageLoader);
        */
    }
}
