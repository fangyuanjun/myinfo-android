package com.kecq.myinfo.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kecq.myinfo.ApplicationEx;
import com.kecq.myinfo.LockActivity;
import com.kecq.myinfo.LoginActivity;
import com.kecq.myinfo.R;
import com.kecq.myinfo.SettingsActivity;
import com.kecq.myinfo.Temp;
import com.kecq.myinfo.UnlockActivity;

import fyj.lib.android.DialogHelper;

/**
 * Created by fyj on 2017/12/18.
 */

public class MyProfileFragment extends Fragment implements View.OnClickListener {

    private boolean isLock = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View thisView = inflater.inflate(R.layout.fragment_myprofile, container, false);
        thisView.findViewById(R.id.myprofile_setting).setOnClickListener(this);
        thisView.findViewById(R.id.myprofile_logout).setOnClickListener(this);
        thisView.findViewById(R.id.myprofile_lock).setOnClickListener(this);

        TextView  myprofile_username= thisView.findViewById(R.id.myprofile_username);
        TextView  myprofile_usernumber=thisView.findViewById(R.id.myprofile_usernumber);

        SharedPreferences settings = ApplicationEx.getContext().getSharedPreferences(Temp.SP_NAME, 0);
        String localPassword = settings.getString(Temp.LOCAL_PASSWORD, null);
        TextView lockTextView=  thisView.findViewById(R.id.myprofile_lock);
        myprofile_username.setText(settings.getString(Temp.USER_NAME,""));
        myprofile_usernumber.setText(settings.getString(Temp.USER_NUMBER,null));
        String userID = settings.getString(Temp.USER_ID, null);
        //ImageView imageView = (ImageView) findViewById(R.id.localPwdSet);
        if (localPassword == null || localPassword.equals("")) {
            //imageView.setImageResource(R.drawable.com_tencent_open_agent_authority_select_all);
            lockTextView.setText("锁定");
        } else {
            //imageView.setImageResource(R.drawable.com_tencent_open_agent_authority_select_none);
            lockTextView.setText("解锁");
            isLock = true;
        }

        return thisView;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.myprofile_setting) {
            startActivity(new Intent(getActivity(), SettingsActivity.class));
        } else if (v.getId() == R.id.myprofile_logout) {
            logout();
        }else if (v.getId() == R.id.myprofile_lock) {
            if(isLock){
                setUnLock();
            }else{
                setLock();
            }
        }
    }

    private void logout() {
        SharedPreferences settings = getActivity().getSharedPreferences(Temp.SP_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();
        Temp.TOKEN=null;

        Intent intent = new Intent(new Intent(getActivity(),
                LoginActivity.class));
        this.startActivity(intent);
        getActivity().finish();
    }

    public  void setLock(){
        startActivityForResult (new Intent(getActivity(), LockActivity.class), 1);
    }

    public  void setUnLock(){
        startActivityForResult (new Intent(getActivity(), UnlockActivity.class), 2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        SharedPreferences settings = ApplicationEx.getContext().getSharedPreferences(Temp.SP_NAME, 0);
        String localPassword = settings.getString(Temp.LOCAL_PASSWORD, null);
        TextView lockTextView=  getView().findViewById(R.id.myprofile_lock);
        if (localPassword == null || localPassword.equals("")) {
           isLock=false;
            lockTextView.setText("锁定");
        } else {
            isLock = true;
            lockTextView.setText("解锁");
        }

    }

    /*    public void pwdSetClick(View v) {
        //ImageView imageView = (ImageView) findViewById(R.id.localPwdSet);
        if (isLock) // 如果之前已经锁定点击按钮解锁
        {
            //imageView.setImageResource(R.drawable.com_tencent_open_agent_authority_select_all);
            SharedPreferences settings = getSharedPreferences("note", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.remove(Temp.LOCAL_PASSWORD);
            editor.commit();
            isLock=false;
        } else {
            //imageView.setImageResource(R.drawable.com_tencent_open_agent_authority_select_none);
            Intent intent = new Intent(new Intent(SettingActivity.this,
                    LockActivity.class));
            startActivityForResult(intent, 0);
            isLock=true;
        }
    }*/


  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-gcenerated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == 0) {
                Bundle bundle = data.getExtras();
                String localPassword = bundle
                        .getString(Temp.LOCAL_PASSWORD);
                if (localPassword != null && (!localPassword.equals(""))) {
                    ImageView imageView = (ImageView) findViewById(R.id.localPwdSet);
                    //imageView.setImageResource(R.drawable.com_tencent_open_agent_authority_select_none);
                    SharedPreferences settings = getSharedPreferences("note", 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString(Temp.LOCAL_PASSWORD, localPassword);
                    editor.commit();
                }
            }
        }
    }*/
}
