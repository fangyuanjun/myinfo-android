package com.kecq.myinfo.fragment;

import android.content.Intent;

import com.kecq.myinfo.Loading;
import com.kecq.myinfo.MyregisterShowActivity;
import com.kecq.myinfo.Temp;
import com.kecq.myinfo.model.ListModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fyj.lib.HttpAsync;
import fyj.lib.HttpResult;
import fyj.lib.SignHelper;


/**
 * Created by fyj on 2017/12/18.
 */

public class MyregisterListFragment extends ListTitleFragment {
    @Override
    protected Map<String, String> getQueryParameters(int start,int size,String search) {
        Map<String, String> map = new HashMap<>();
        map.put("offset", start + "");
        map.put("limit", size + "");
        if(search!=null&&!search.equals("")){
            map.put("search", search);
        }

        return map;
    }

    @Override
    protected String getQueryDataUrl() {
        return Temp.MyInfoBaseUrl + SignHelper.SignUserUrl("/MyRegister/GetList");
    }

    @Override
    protected List<ListModel> resultToListModel(String result) throws JSONException{
        JSONObject json = new JSONObject(result);

        JSONArray arry = json.getJSONArray("rows");
        List<ListModel> newlist = new ArrayList<ListModel>();
        for (int i = 0; i < arry.length(); i++) {
            JSONObject itemobj = arry.getJSONObject(i);
            ListModel model = new ListModel();

            model.setTitle(itemobj.optString("registerUrlName"));
            model.setGuid(itemobj.optString("registerID"));

            newlist.add(model);
        }

        return newlist;
    }

    @Override
    protected boolean isResultSuccess(String result) throws JSONException {
        JSONObject json = new JSONObject(result);

        return json.has("total");
    }

    @Override
    protected int getResultTotal(String result) throws JSONException {
        JSONObject json = new JSONObject(result);

        return json.optInt("total");
    }

    @Override
    protected String getResultMessage(String result) throws JSONException {
        JSONObject json = new JSONObject(result);
        return json.optString("message");
    }

    @Override
    protected String getDeleteUrl(String id) {
        return Temp.MyInfoBaseUrl+SignHelper.SignUserUrl("/MyRegister/Remove");
    }

    @Override
    protected Map<String, String> getDeleteParameters(String id) {
        Map<String, String> map = new HashMap<>();
        map.put("ids",id);
        return map;
    }

    @Override
    protected boolean isDeleteSuccess(String result) throws JSONException{
        JSONObject json = new JSONObject(result);
        return json.optInt("code")>0;
    }

    @Override
    protected String getDeleteMessage(String result) throws JSONException{
        JSONObject json = new JSONObject(result);
        return json.optString("message");
    }

    @Override
    protected void jumpDetail(ListModel model) {
        String id=model.getGuid();
        Intent intent = new Intent(getActivity(), MyregisterShowActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("url",Temp.MyInfoBaseUrl+SignHelper.SignUserUrl("/MyRegister/Edit/"+id) );

        startActivityForResult(intent,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
      /* String id=data.getStringExtra("id");
       String url=Temp.MyInfoBaseUrl+SignHelper.SignUserUrl("/MyRegister/EditModel/"+id);
        Loading.showWaiting(getActivity());
        HttpAsync.DoGet(url, new HttpResult() {
            @Override
            public void Success(String result) {
                Loading.hideWaiting(getActivity());
                try {
                    JSONObject  json = new JSONObject(result);
                    ListModel model = new ListModel();
                    model.setTitle(json.optString("registerUrlName"));
                    model.setGuid(json.optString("registerID"));
                    MyregisterListFragment.this.setListItem(model);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void Error(Throwable tr) {
                super.Error(tr);
                Loading.hideWaiting(getActivity());
            }
        });*/
    }

    @Override
    protected void jumpEdit(ListModel model) {
        String id=model.getGuid();
        Intent intent = new Intent(getActivity(), MyregisterShowActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("url",Temp.MyInfoBaseUrl+SignHelper.SignUserUrl("/MyRegister/Edit/"+id) );

        startActivity(intent);
    }
}
