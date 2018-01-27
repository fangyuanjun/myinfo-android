package com.kecq.myinfo.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.kecq.myinfo.R;
import com.kecq.myinfo.adapter.ListContentAdapter;
import com.kecq.myinfo.model.ListModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fyj.lib.android.DialogHelper;

/**
 * Created by fyj on 2017/12/16.
 */

public class ListContentFragment extends Fragment{

    private SwipeRefreshLayout swipeRefreshLayout;
    private ListContentAdapter adapter;
    private List<ListModel> list;
    private int totalItemsCount;   //数据总条数
    private boolean isLoadingNext;   //是否正在加载更多
    private ListView listView;
    private View footerView;   //底部加载更多
    private  View thisView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        thisView =inflater.inflate(R.layout.fragment_list_content, container,false);

        swipeRefreshLayout = (SwipeRefreshLayout) thisView.findViewById(R.id.fragment_list_content_refreshLayout);
        listView = (ListView) thisView.findViewById(R.id.fragment_list_content_listview);

        footerView= getLayoutInflater().inflate(R.layout.listview_footer,null);

        list = new ArrayList<ListModel>();
        for (int i=0;i<20;i++)
        {
            list.add(new ListModel(i+""));
        }

        adapter = new ListContentAdapter(getLayoutInflater(),list);

        List<String> list2= new ArrayList<>();
        list2.addAll(Arrays.asList("Java","php","C++","C#","IOS","html","C","J2ee","j2se","VB",".net","Http","tcp","udp","www"));

        ArrayAdapter adapter2 = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,
                android.R.id.text1,list2);

        listView.setAdapter(adapter);

        listView.setOnScrollListener(new AbsListView.OnScrollListener(){

            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if(i == SCROLL_STATE_IDLE){
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
                            new ListContentFragment.LoadNextDataThread().start();
                        }
                    }


                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);

        totalItemsCount=100;

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new ListContentFragment.LoadDataThread().start();
            }
        });

        initListViewEvent();
        return thisView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    private  void  initListViewEvent()
    {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DialogHelper.showToast("i"+i+",L"+l);
            }
        });

       /*    长按
         listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                DialogHelper.showToast("长按i"+i+",L"+l);
                return true;   //返回true 则不会继续触发onItemClick
            }
        });*/

        listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener(){

            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                //contextMenu.setHeaderTitle("选择操作");

                contextMenu.add(0, 0, 0, "更新该条");
                contextMenu.add(0, 1, 0, "删除该条");

            /*    //获取对应的item的positon
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                item_id = info.position;
                //设置菜单布局
                MenuInflater inflater = new MenuInflater(FirstActivity.this);
                inflater.inflate(R.menu.menu,menu);*/
            }
        });
    }


    //给菜单项添加事件
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        //info.id得到listview中选择的条目绑定的id
        String id = String.valueOf(info.id);
        switch (item.getItemId()) {
            case 0:

                return true;
            case 1:

                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0x101:
                    if (swipeRefreshLayout.isRefreshing()){
                        List<ListModel> newlist=(List<ListModel>) msg.obj;
                        list.addAll(0,newlist);
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);//设置不刷新
                    }
                    break;
                case 0x102:
                    List<ListModel> newlist=(List<ListModel>) msg.obj;
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
                List<ListModel> newlist=new ArrayList<ListModel>();
                for (int i=0;i<5;i++)
                {
                    newlist.add(0,new ListModel(i+""));
                }

                Thread.sleep(2000);

                Message msg = Message.obtain();
                msg.obj=newlist;
                msg.what = 0x101;
                handler.sendMessage(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    //追加尾部
    class LoadNextDataThread extends  Thread{
        @Override
        public void run() {

            try {
                //List<String> newlist=Arrays.asList("Json","XML","UDP","http");
                List<ListModel> newlist=new ArrayList<ListModel>();
                for (int i=0;i<5;i++)
                {
                    newlist.add(0,new ListModel(i+""));
                }
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
