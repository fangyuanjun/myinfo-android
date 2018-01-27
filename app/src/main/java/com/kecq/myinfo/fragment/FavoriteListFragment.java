package com.kecq.myinfo.fragment;

import android.content.Intent;
import android.net.Uri;

import com.kecq.myinfo.Temp;
import com.kecq.myinfo.model.FavoriteModel;
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

public class FavoriteListFragment extends ListTitleFragment {
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
        return Temp.MyInfoBaseUrl + SignHelper.SignUserUrl("/MyFavorite/GetList");
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
            FavoriteModel entity=new FavoriteModel();
            entity.setFavoriteTitle(itemobj.optString("favoriteTitle"));
            entity.setFavoriteID(itemobj.optString("favoriteID"));
            entity.setFavoriteUrl(itemobj.optString("favoriteUrl"));
            model.setTitle(itemobj.optString("favoriteTitle"));
            model.setGuid(itemobj.optString("favoriteID"));
           model.setTag(entity);
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
        return Temp.MyInfoBaseUrl+SignHelper.SignUserUrl("/MyFavorite/Remove");
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
        FavoriteModel entity=(FavoriteModel)(model.getTag());
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(entity.getFavoriteUrl());
        intent.setData(content_url);
        startActivity(intent);
    }

    @Override
    protected void jumpEdit(ListModel model) {

    }
}
