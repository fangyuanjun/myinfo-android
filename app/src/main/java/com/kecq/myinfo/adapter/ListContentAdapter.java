package com.kecq.myinfo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.kecq.myinfo.ApplicationEx;
import com.kecq.myinfo.R;
import com.kecq.myinfo.model.ListModel;

import java.util.List;

import fyj.lib.android.BitmapCache;

/**
 * Created by fyj on 2017/12/16.
 */

public class ListContentAdapter extends BaseAdapter {

    private List<ListModel> list;
    private LayoutInflater inflater;

    public ListContentAdapter(LayoutInflater inflater, List<ListModel> list)
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
        return list.get(i).getId();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        //加载布局为一个视图
        View view=inflater.inflate(R.layout.list_item_content,null);
        ListModel model=(ListModel) getItem(i);
        TextView titleView=view.findViewById(R.id.list_item_content_title);
        titleView.setText(model.getTitle());
        TextView leftView=view.findViewById(R.id.list_item_content_left);
        leftView.setText("");
        TextView rightView=view.findViewById(R.id.list_item_content_right);
        rightView.setText(model.getShowtimeString());

        NetworkImageView nv_image = (NetworkImageView) view.findViewById(R.id.list_item_content_image);
        RequestQueue mQueue = Volley.newRequestQueue(ApplicationEx.getContext());
        ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache());
        nv_image.setDefaultImageResId(R.mipmap.nopic_foreground);
        nv_image.setErrorImageResId(R.mipmap.nopic_foreground);
        String url=model.getPic();
        if(url!=null&&!url.equals("")){
            nv_image.setImageUrl(model.getPic(),
                    imageLoader);
        }

        return view;
    }
}
