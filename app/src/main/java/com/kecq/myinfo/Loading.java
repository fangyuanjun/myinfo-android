package com.kecq.myinfo;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

public class Loading {

    private static View waitView;

    public static void showWaiting(Activity activity,String text, boolean isBlur) {
        try {
            WindowManager.LayoutParams lp = null;
            if (isBlur) {
                lp = new WindowManager.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.TYPE_APPLICATION,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN
                                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                        PixelFormat.TRANSLUCENT);
            } else {
                lp = new WindowManager.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.TYPE_APPLICATION,
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                        PixelFormat.TRANSLUCENT);
            }

            WindowManager mWindowManager = (WindowManager) activity
                    .getSystemService(Context.WINDOW_SERVICE);
            if (waitView == null) {
                LayoutInflater inflate = (LayoutInflater) activity
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                waitView = inflate.inflate(R.layout.loading, null);
                TextView textView=(TextView)waitView.findViewById(R.id.loading_text);
                textView.setText(text);
            }

            mWindowManager.addView(waitView, lp);
        } catch (Throwable e) {
            Log.e("showLoading", e.getMessage());


        }
    }

    public static void showWaiting(Activity activity) {
        showWaiting(activity,"loading...",true);
    }

    public static void hideWaiting(Activity activity) {
        try {
            if (waitView != null) {
                WindowManager mWindowManager = (WindowManager) activity
                        .getSystemService(Context.WINDOW_SERVICE);
                mWindowManager.removeView(waitView);
                waitView = null;
            }
        } catch (Throwable e) {
            Log.e("hideWaiting", e.getMessage(), e);
        }
    }
}
