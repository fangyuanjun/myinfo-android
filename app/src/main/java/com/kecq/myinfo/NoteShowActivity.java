package com.kecq.myinfo;

import android.support.v7.app.AppCompatActivity;
import java.lang.ref.WeakReference;
import java.util.Map;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import fyj.lib.android.DialogHelper;


public class NoteShowActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_show);

        new Thread(task).start();
    }

    private Handler handler = new Handler(){
        //WeakReference<NoteContentActivity> mActivity;

        //MyHandler(NoteContentActivity activity) {
        //    mActivity = new WeakReference<NoteContentActivity>(activity);
       // }

        public void handleMessage(Message msg) {
            //NoteContentActivity theActivity = mActivity.get();
            if (msg.what == 0) {
                DialogHelper.showToast("正在加载...");
                Loading.showWaiting(NoteShowActivity.this);
            }

            if (msg.what == 1) {

                Map<String, Object> map = (Map<String, Object>) msg.obj;
                if (map != null) {
                    /*
                    TextView noteContent=(TextView)NoteShowActivity.this.findViewById(R.id.noteContent);
                    TextView noteDatetime=(TextView)theActivity.findViewById(R.id.noteDatetime);
                    TextView noteDevice=(TextView)theActivity.findViewById(R.id.noteDevice);
                    TextView noteFrom=(TextView)theActivity.findViewById(R.id.noteFrom);
                    TextView noteLocation=(TextView)theActivity.findViewById(R.id.noteLocation);

                    CharSequence charSequence=Html.fromHtml(map.get("noteContent").toString());
                    noteContent.setText(charSequence);
                    noteContent.setMovementMethod(LinkMovementMethod.getInstance());

                    noteDatetime.setText("时间"+map.get("UPDATE_DATE").toString());
                    noteDevice.setText("设备:"+map.get("noteDevice").toString());
                    noteFrom.setText("来自:"+map.get("noteFrom").toString());
                    noteLocation.setText("位置:"+map.get("noteLocation").toString());
                    */
                }
                Loading.hideWaiting(NoteShowActivity.this);
            }

            if (msg.what == -1) {
                Loading.hideWaiting(NoteShowActivity.this);
            }
        }
    };


    Runnable task = new Runnable() {

        public void run() {
            // TODO Auto-generated method stub
            Message msg = Message.obtain();
            msg.what = 0;
            handler.sendMessage(msg);
            msg = Message.obtain();
            msg.what = 1;
            Bundle extra = getIntent().getExtras();
            String noteID = extra.getString("noteID");
            SharedPreferences settings = getSharedPreferences(Temp.SP_NAME, 0);
            try
            {
                //Map<String, Object> map = dao.GetSingleNote(noteID);
                //msg.obj=map;
            }
            catch(Exception ex)
            {
                msg.what = -1;
            }
            handler.sendMessage(msg);
        }
    };

    public void backClick(View v)
    {
        this.finish();
    }
}

