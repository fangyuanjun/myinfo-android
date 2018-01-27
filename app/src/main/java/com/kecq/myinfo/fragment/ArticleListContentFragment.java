package com.kecq.myinfo.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kecq.myinfo.ArticleShowActivity;
import com.kecq.myinfo.Loading;
import com.kecq.myinfo.MyWebViewActivity;
import com.kecq.myinfo.R;
import com.kecq.myinfo.Temp;
import com.kecq.myinfo.adapter.ListContentAdapter;
import com.kecq.myinfo.model.ArticleModel;
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
import fyj.lib.LogHelper;
import fyj.lib.SignHelper;
import fyj.lib.android.DialogHelper;
import fyj.lib.common.DateHelper;


/**
 * Created by fyj on 2017/12/16.
 */

public class ArticleListContentFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private ListContentAdapter adapter;
    private List<ListModel> list;
    private int totalItemsCount;   //数据总条数
    private boolean isLoadingNext;   //是否正在加载更多
    private ListView listView;
    private View footerView;   //底部加载更多
    private View thisView;

    private  String dbConfig="fyj";

    public void DoSearch(String search){
        LoadData(0,10,false,false,search);
    }
    private void LoadData(int start, int size, final boolean isNext, final boolean isInit)
    {
        LoadData(start,size,isNext,isInit,null);
    }
    private void LoadData(int start, int size, final boolean isNext, final boolean isInit,String search) {

        Map<String, String> map = new HashMap<>();
        map.put("db", dbConfig);
        map.put("start", start + "");
        map.put("size", size + "");
        if(search!=null&&!search.trim().equals(""))
        {
            map.put("search", search);
        }
        if(isInit){
            Loading.showWaiting(getActivity(), "获取数据...", true);
        }

        HttpAsync.DoPost(Temp.BaseServerUrl + SignHelper.SignUserUrl("/api/Article/QueryList"), map, new HttpResult() {
            @Override
            public void Success(String result) {

                if(isInit) {
                    Loading.hideWaiting(getActivity());
                }

                try {
                    JSONObject json = new JSONObject(result);
                    int code = json.optInt("code");
                    if (code > 0) {
                        totalItemsCount = json.getInt("total");
                        JSONArray arry = json.getJSONArray("data");
                        List<ListModel> newlist = new ArrayList<ListModel>();
                        for (int i = 0; i < arry.length(); i++) {
                            JSONObject itemobj = arry.getJSONObject(i);
                            ListModel model = new ListModel();
                            ArticleModel article = new ArticleModel();
                            article.setArticleID(itemobj.getInt("articleID"));
                            article.setArticleTitle(itemobj.getString("articleTitle"));
                            article.setArticleAuthor(itemobj.getString("articleAuthor"));
                            article.setArticleDatetime(DateHelper.strToDateLong(itemobj.getString("articleDatetime")));
                            article.setArticleThumbPic(itemobj.getString("articleThumbPic"));
                            article.setArticleUrl(itemobj.getString("articleUrl"));
                            article.setBlogID(itemobj.optString("blogID"));
                            model.setTitle(article.getArticleTitle());
                            model.setId(article.getArticleID());
                            model.setPic(article.getArticleThumbPic());
                            model.setUrl(article.getArticleUrl());
                            model.setShowtime(article.getArticleDatetime());
                            model.setShowtimeString(itemobj.getString("articleDatetime"));
                            model.setTag(article);
                            newlist.add(model);
                        }

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

                    } else {
                        DialogHelper.showToast(json.optString("message"));
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
        swipeRefreshLayout = (SwipeRefreshLayout) thisView.findViewById(R.id.article_list_content_refreshLayout);
        listView = (ListView) thisView.findViewById(R.id.article_list_content_listview);

        footerView = getLayoutInflater().inflate(R.layout.listview_footer, null);

        this.list = list;

        adapter = new ListContentAdapter(getLayoutInflater(), list);

        //List<String> list2= new ArrayList<>();
        //list2.addAll(Arrays.asList("Java","php","C++","C#","IOS","html","C","J2ee","j2se","VB",".net","Http","tcp","udp","www"));

        //ArrayAdapter adapter2 = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,
        //       android.R.id.text1,list);

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
                            //new ArticleListContentFragment.LoadNextDataThread().start();
                            int index = adapter.getCount();
                            //Log.i("listView",index+"");
                            LoadData(adapter.getCount(), 10, true, false);

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
                //new ArticleListContentFragment.LoadDataThread().start();
                LoadData(0, 10, false, false);
            }
        });

        initListViewEvent();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        thisView = inflater.inflate(R.layout.article_list_content, container, false);

        LoadData(0, 10, false, true);
        return thisView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    private void initListViewEvent() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //position id
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ListModel model=(ListModel)adapter.getItem(position);
                ArticleModel article= (ArticleModel) (model.getTag());
                String url= model.getUrl();
                SharedPreferences settings = getActivity().getSharedPreferences(Temp.SP_NAME, 0);
                String userID= settings.getString(Temp.USER_ID,null);
                if(userID!=null&&userID.equals("8dc2885cd7bb44aa9344557dbc0c1630")){    //if(article.getBlogID().equals("10000002"))
                    url=Temp.BaseServerUrl+SignHelper.SignUserUrl("/ArticleShow/Show/"+id);
                }
                Intent intent = new Intent(getActivity(), ArticleShowActivity.class);
                //Bundle extras = new Bundle();
                //intent.putExtras(extras);
                intent.putExtra("articleID",id);
                intent.putExtra("articleUrl",url);
                intent.putExtra("articleTitle",model.getTitle());
                intent.putExtra("url",url);
                intent.putExtra("title",model.getTitle());

                startActivity(intent);
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

    }



   public  void ChangeDb(String db)
   {
       this.dbConfig=db;
       LoadData(0, 10, false, true);
   }



}


