package com.kecq.myinfo;

import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListTestActivity extends AppCompatActivity implements AbsListView.OnScrollListener {


    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayAdapter adapter;
    private List<String> list;
    private int totalItemsCount;   //数据总条数
    private boolean isLoadingNext;   //是否正在加载更多
    private ListView listView;
    private View footerView;   //底部加载更多
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_test);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.list_test_refreshLayout);
        listView = (ListView) findViewById(R.id.list_test_listview);

        footerView= getLayoutInflater().inflate(R.layout.listview_footer,null);

        list = new ArrayList<>();
        list.addAll(Arrays.asList("Java","php","C++","C#","IOS","html","C","J2ee","j2se","VB",".net","Http","tcp","udp","www"));

        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,
                android.R.id.text1,list);
        listView.setAdapter(adapter);

        listView.setOnScrollListener(this);

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);

        totalItemsCount=100;

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new ListTestActivity.LoadDataThread().start();
            }
        });
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(scrollState == SCROLL_STATE_IDLE){
            if(isLoadingNext)
            {
                return;
            }
            //如果滚动到了最底部
            if(listView.getLastVisiblePosition() == (adapter.getCount() - 1))
            {

                //如果没有加载完
                if(adapter.getCount()<totalItemsCount)
                {
                    isLoadingNext=true;
                    listView.addFooterView(footerView);
                    new ListTestActivity.LoadNextDataThread().start();
                }
            }


        }


    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0x101:
                    if (swipeRefreshLayout.isRefreshing()){
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);//设置不刷新
                    }
                    break;
                case 0x102:
                    List<String> newlist=(List<String>) msg.obj;
                    list.addAll(newlist);
                    adapter.notifyDataSetChanged();

                    isLoadingNext=false;
                    listView.removeFooterView(footerView);

                    break;
            }
        }
    };


    /**
     * 模拟加载数据的线程
     */
    class LoadDataThread extends  Thread{
        @Override
        public void run() {

            try {
                list.addAll(0,Arrays.asList("xxxx","yyyy","dddd","aaaa"));
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(0x101);//通过handler发送一个更新数据的标记
        }
    }

    //追加尾部
    class LoadNextDataThread extends  Thread{
        @Override
        public void run() {

            try {
                List<String> newlist=Arrays.asList("Json","XML","UDP","http");
                Thread.sleep(2000);
                Message msg = Message.obtain();
                msg.obj=newlist;
                msg.what = 0x102;
                handler.sendMessage(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}

