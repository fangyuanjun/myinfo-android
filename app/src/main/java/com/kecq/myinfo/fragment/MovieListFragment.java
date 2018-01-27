package com.kecq.myinfo.fragment;

import android.content.Intent;

import com.kecq.myinfo.MovieListActivity;
import com.kecq.myinfo.MyWebViewActivity;
import com.kecq.myinfo.Temp;
import com.kecq.myinfo.model.ListModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fyj.lib.SignHelper;

/**
 * Created by fyj on 2017/12/18.
 */

public class MovieListFragment extends ListTitleFragment {
    @Override
    protected Map<String, String> getQueryParameters(int start, int size,String search) {
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
        return Temp.MyInfoBaseUrl + SignHelper.SignUserUrl("/MyMovie/GetList");
    }

    @Override
    protected List<ListModel> resultToListModel(String result) throws JSONException {
        JSONObject json = null;
        json = new JSONObject(result);

        JSONArray arry = json.getJSONArray("rows");
        List<ListModel> newlist = new ArrayList<ListModel>();
        for (int i = 0; i < arry.length(); i++) {
            JSONObject itemobj = arry.getJSONObject(i);
            ListModel model = new ListModel();

            model.setTitle(itemobj.optString("movieName"));
            model.setGuid(itemobj.optString("movieID"));

            newlist.add(model);
        }

        return newlist;
    }

    @Override
    protected boolean isResultSuccess(String result) throws JSONException {
        JSONObject json = null;
        json = new JSONObject(result);

        return json.has("total");
    }

    @Override
    protected int getResultTotal(String result) throws JSONException {
        JSONObject json = null;
        json = new JSONObject(result);

        return json.optInt("total");
    }

    @Override
    protected String getResultMessage(String result) throws JSONException {

        JSONObject json = new JSONObject(result);
        return json.optString("message");
    }

    @Override
    protected String getDeleteUrl(String id) {
        return Temp.MyInfoBaseUrl+SignHelper.SignUserUrl("/MyMovie/Delete");
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
        Intent intent = new Intent(getActivity(), MyWebViewActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("title","修改电影记录");
        intent.putExtra("url",Temp.MyInfoBaseUrl+ SignHelper.SignUserUrl("/MyMovie/Edit/"+id) );

        startActivityForResult(intent,1);
    }

    @Override
    protected void jumpEdit(ListModel model) {

    }
}

