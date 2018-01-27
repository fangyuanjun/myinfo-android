package com.kecq.myinfo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kecq.myinfo.Loading;
import com.kecq.myinfo.R;
import com.kecq.myinfo.model.ListModel;
import fyj.lib.HttpAsync;
import fyj.lib.HttpResult;
import fyj.lib.LogHelper;
import fyj.lib.android.DialogHelper;


/**
 * Created by fyj on 2017/12/16.
 */

public abstract class ListTitleFragment extends Fragment{
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayAdapter adapter;
    private List<ListModel> list=new ArrayList<>();
    private int totalItemsCount;   //数据总条数
    private boolean isLoadingNext;   //是否正在加载更多
    private ListView listView;
    private View footerView;   //底部加载更多
    private View thisView;

    /**
     * 请求URL参数
     * @param start
     * @param size
     * @param search
     * @return
     */
    protected  abstract  Map<String, String> getQueryParameters(int start,int size,String search);

    /**
     * 请求URL
     * @return
     */
    protected  abstract  String getQueryDataUrl();

    /**
     * 请求结果转成adapter所需的数据
     * @param result
     * @return
     * @throws JSONException
     */
    protected abstract  List<ListModel> resultToListModel(String result) throws JSONException;

    protected  abstract  boolean isResultSuccess(String result) throws JSONException;

    protected  abstract  int getResultTotal(String result) throws JSONException;

    protected  abstract  String getResultMessage(String result) throws JSONException;

    /**
     * 向服务器请求删除数据的 URL
     * @param id
     * @return
     */
    protected abstract String getDeleteUrl(String id);

    //获取删除操作请求的参数
    protected  abstract  Map<String, String> getDeleteParameters(String id);
    //是否删除成功
    protected abstract  boolean isDeleteSuccess(String result) throws JSONException;

    //删除操作服务器的提示
    protected abstract String getDeleteMessage(String result) throws JSONException;

    //跳转到详情页面
    protected abstract void jumpDetail(ListModel model);

    //跳转到编辑（修改）页面
    protected abstract void jumpEdit(ListModel model);

    //还可以传position 就避免查找了
    protected void setListItem(ListModel model)
    {
        if(model.getGuid()==null|| model.getGuid().equals("")){
            return;
        }

        for(int i=0;i<list.size();i++){
            ListModel m=list.get(i);
            if(m.getGuid().equals(model.getGuid())){
                list.set(i,model);//不能用m=model
                adapter.notifyDataSetChanged();
                return;
            }
        }
    }

    private String currentSearch;   //当前搜索的文本

    public   void doSearch(String search){

        this.currentSearch=search;
        LoadData(0,15,false,false,search);
    }

    private void LoadData(int start, int size, final boolean isNext, final boolean isInit){
        LoadData(start,size,isNext,isInit,this.currentSearch);
    }
    private void LoadData(int start, int size, final boolean isNext, final boolean isInit,String search) {

        if(isInit){
            Loading.showWaiting(getActivity(), "获取数据...", true);
        }

        HttpAsync.DoPost(getQueryDataUrl(), getQueryParameters(start,size,search), new HttpResult() {
            @Override
            public void Success(String result) {
                if(isInit){
                    Loading.hideWaiting(getActivity());
                }

                try {

                    if (isResultSuccess(result)) {
                        totalItemsCount = getResultTotal(result);
                        List<ListModel> newlist=resultToListModel(result);

//                        if (swipeRefreshLayout.isRefreshing()){
//                            swipeRefreshLayout.setRefreshing(false);//设置不刷新
//                        }

                        if (isInit) {
                            Init(newlist);
                        } else {
                            if (isNext) {
                                list.addAll(newlist);
                                adapter.notifyDataSetChanged();
                                isLoadingNext = false;
                                listView.removeFooterView(footerView);
                            } else {

                                for (int i = 0; i < newlist.size(); i++) {
                                    if(i<list.size()){
                                        list.set(i, newlist.get(i));
                                    }
                                   else{
                                        list.add(newlist.get(i));
                                    }
                                }
                                for (int i = list.size()-1; i >=newlist.size(); i--) {
                                    list.remove(i);
                                }

                                adapter.notifyDataSetChanged();
                                swipeRefreshLayout.setRefreshing(false);//设置不刷新
                            }

                        }

                        if(adapter.getCount()==0){
                           DialogHelper.showToast("无数据");
                        }

                    } else {
                        DialogHelper.showToast(getResultMessage(result));
                    }
                } catch (JSONException e) {
                    DialogHelper.showToast("解析数据失败");
                    LogHelper.DoException(e);
                }
            }

            @Override
            public void Error(Throwable tr) {
                Loading.hideWaiting(getActivity());
                DialogHelper.showToast("服务器异常");
                LogHelper.DoException(tr);
            }
        });
    }

    private void Init(List<ListModel> list) {
        swipeRefreshLayout = (SwipeRefreshLayout) thisView.findViewById(R.id.fragment_list_title_refreshLayout);
        listView = (ListView) thisView.findViewById(R.id.fragment_list_title_listview);
        footerView = getLayoutInflater().inflate(R.layout.listview_footer, null);

        this.list = list;

        //List<String> list2= new ArrayList<>();
        //list2.addAll(Arrays.asList("Java","php","C++","C#","IOS","html","C","J2ee","j2se","VB",".net","Http","tcp","udp","www"));

         adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,
               android.R.id.text1,list);

        listView.setAdapter(adapter);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if (i == SCROLL_STATE_IDLE) {
                    if (isLoadingNext) {
                        return;
                    }
                    //如果滚动到了最底部
                    if (listView.getLastVisiblePosition() == (adapter.getCount() - 1)) {

                        //如果没有加载完
                        if (adapter.getCount() < totalItemsCount) {
                            isLoadingNext = true;
                            listView.addFooterView(footerView);

                            LoadData(adapter.getCount(), 15, true, false);

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

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadData(0, 15, false, false);
            }
        });

        initListViewEvent();

    }
    //private View emptyView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        thisView = inflater.inflate(R.layout.fragment_list_title, container, false);
        //emptyView=thisView.findViewById(R.id.emptyView);
        LoadData(0, 15, false, true);
        return thisView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    private void initListViewEvent() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ListModel model=(ListModel) adapter.getItem(i);
                ListTitleFragment.this.jumpDetail(model);
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

        listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {

            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                //contextMenu.setHeaderTitle("选择操作");
                //group   id   categoryOrder  title
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

    private  void DeleteItem(final int position, String id) {
        Loading.showWaiting(getActivity(),"正在删除...",true);
        HttpAsync.DoPost(this.getDeleteUrl(id),this.getDeleteParameters(id), new HttpResult() {
            @Override
            public void Success(String result) {
                Loading.hideWaiting(getActivity());
                try
                {
                    if(isDeleteSuccess(result)){
                        DialogHelper.showToast("删除成功");
                        list.remove(position);
                        adapter.notifyDataSetChanged();
                    }else{
                        DialogHelper.showToast(getDeleteMessage(result));
                    }
                }
                catch (Exception e){
                    DialogHelper.showToast("error");
                    LogHelper.DoException(e);
                }
            }

            @Override
            public void Error(Throwable tr) {
                Loading.hideWaiting(getActivity());
                DialogHelper.showToast(tr.getMessage());
            }
        });
    }

    //给菜单项添加事件
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        //info.id得到listview中选择的条目绑定的id
        //long id=info.id;
        int position=info.position;
        ListModel model=(ListModel) adapter.getItem(position);
        //Log.i("右键菜单","id:"+id+",position"+position);
        switch (item.getItemId()) {
            case 0:
                ListTitleFragment.this.jumpEdit(model);
                return true;
            case 1:
                DeleteItem(position,model.getGuid());
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


}

