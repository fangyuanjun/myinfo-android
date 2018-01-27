package com.kecq.myinfo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kecq.myinfo.R;
import com.kecq.myinfo.model.ListModel;

import java.util.List;

/**
 * Created by fyj on 2017/12/16.
 */

public class ListTitleAdapter extends BaseAdapter {
    private List<ListModel> list;
    private LayoutInflater inflater;

    public ListTitleAdapter(LayoutInflater inflater, List<ListModel> list)
    {
        this.list=list;
        this.inflater=inflater;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        //加载布局为一个视图
        View view=inflater.inflate(R.layout.list_item_title,null);
        TextView textView=view.findViewById(R.id.list_item_name);
        ListModel model=(ListModel) getItem(i);
        textView.setText(model.toString());
        return view;
    }
}
