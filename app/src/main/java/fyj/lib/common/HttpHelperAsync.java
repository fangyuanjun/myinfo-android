package fyj.lib.common;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import fyj.lib.HttpResult;

/**
 * Created by fyj on 2017/12/10.
 */

public class HttpHelperAsync {

    int timeout = 10000;   //超时时间默认10s
    HttpResult callback;

    public HttpHelperAsync(HttpResult callback) {
        this.callback = callback;
    }

    public void SetTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void DoGet(final String url) {
        Runnable task = new Runnable() {
            public void run() {
                try {
                    Message msg = Message.obtain();
                    msg.what = 0;
                    mHandler.sendMessage(msg);
                    msg = Message.obtain(); // 重新获取 很重要
                    msg.what = 1;
                    String result = HttpHelper.DoGet(url);
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                } catch (Exception e) {
                    Message msg = Message.obtain();
                    msg.what = -2;
                    msg.obj = e;
                    mHandler.sendMessage(msg);
                }
            }
        };

        new Thread(task).start();
        mHandler.postDelayed(timeOutTask, timeout);
    }



    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) // initing
            {
                callback.Requesting();
            }

            if (msg.what == 1) // success
            {
                //if (mHandler != null && task != null) {
                //   mHandler.removeCallbacks(task);
                //}

                if (mHandler != null && timeOutTask != null) {
                    mHandler.removeCallbacks(timeOutTask);
                }

                callback.Success((String) msg.obj);
            }

            if (msg.what == -1) // 超时
            {
                if (mHandler != null && timeOutTask != null) {
                    mHandler.removeCallbacks(timeOutTask);
                }

                callback.TimeOut();
            }

            if (msg.what == -2) // 错误
            {
                if (mHandler != null && timeOutTask != null) {
                    mHandler.removeCallbacks(timeOutTask);
                }

                callback.Error((Exception) msg.obj);
            }
        }
    };

    Runnable timeOutTask = new Runnable() {
        public void run() {
            Message msg = Message.obtain();
            msg.what = -1;
            mHandler.sendMessage(msg);
        }
    };

}

