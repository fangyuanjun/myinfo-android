package com.kecq.myinfo.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by fyj on 2017/12/13.
 */

public class MainViewAdapter extends PagerAdapter {

    private List<View> mViewList;
    public MainViewAdapter(List<View> mViewList) {
        this.mViewList = mViewList;
    }
    @Override
    public int getCount() {     //必须实现
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {  //必须实现
        return view == object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {//实例化
        container.addView(mViewList.get(position));
        return mViewList.get(position);
    }

/*
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {//销毁
        container.removeView(mViewList.get(position));
    }
*/

}
