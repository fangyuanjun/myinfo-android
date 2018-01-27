package com.kecq.myinfo;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.kecq.myinfo.fragment.ListTitleFragment;
import com.kecq.myinfo.model.ListModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fyj.lib.HttpAsync;
import fyj.lib.HttpResult;
import fyj.lib.LogHelper;
import fyj.lib.SignHelper;
import fyj.lib.android.DialogHelper;

/**
 * Created by fyj on 2017/12/25.
 */

public class CateSelectWindow extends PopupWindow {
    private float mShowAlpha = 0.8f;
    private Context mContext;
    private Drawable mBackgroundDrawable;
    private List<ListModel> list;
    private ArrayAdapter<ListModel> adapter;
    private OnItemSelectListener listener;

    public CateSelectWindow(Context context, final OnItemSelectListener listener) {
        this.mContext = context;
        this.listener=listener;
        View customView = ((Activity)context).getLayoutInflater().inflate(R.layout.popup_cate,
                null, false);
        setContentView(customView);
        //LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View contentview = inflater.inflate(R.layout.popup_process, null);
        setAnimationStyle(android.R.style.Animation_Dialog);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setWidth(600);
        setOutsideTouchable(true);  //  设置可以触摸弹出框以外的区域,默认设置outside点击无响应
        setFocusable(true);  //  设置可以获取焦点
        //update(); //更新popupwindow的状态
        // TODO: 2016/5/17 设置背景颜色
        setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.white)));
        //setBackgroundDrawable(new ColorDrawable(Color.parseColor("#66000000")));
        list=new ArrayList<>();
        adapter=new ArrayAdapter<ListModel>(mContext,android.R.layout.simple_list_item_1, list);
        ListView listView =this.getContentView().findViewById(R.id.list_cate);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ListModel model=(ListModel) adapter.getItem(i);
                listener.onItemSelect(model.getGuid());
            }
        });
        initData("fyj");
    }

    public  void initData(String db){
        Loading.showWaiting((Activity) mContext);
        HttpAsync.DoGet(listener.getCategoryDateUrl()+"&db="+db, new HttpResult() {
            @Override
            public void Success(String result) {
                try {
                    Loading.hideWaiting((Activity) mContext);
                    JSONObject json = new JSONObject(result);
                    int code=json.optInt("code");
                    if(code>0){
                        JSONArray array=json.getJSONArray("data");
                        List<ListModel> list=new ArrayList<ListModel>();
                        CateSelectWindow.this.list.clear();
                        ListModel all = new ListModel();
                        all.setTitle("--全部--");
                        CateSelectWindow.this.list.add(all);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject itemobj = array.getJSONObject(i);
                            ListModel model = new ListModel();

                            model.setTitle(itemobj.optString("text"));
                            model.setGuid(itemobj.optString("id"));

                            CateSelectWindow.this.list.add(model);
                        }

                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    DialogHelper.showToast("解析数据失败");
                    LogHelper.DoException(e);
                }
            }

            @Override
            public void Error(Throwable tr) {
                Loading.hideWaiting((Activity) mContext);
                DialogHelper.showToast("获取数据失败");
                LogHelper.DoException(tr);
            }
        });


    }

   public interface  OnItemSelectListener
    {
        void onItemSelect(String id);

        String getCategoryDateUrl();

        //List<ListModel> resultToListModel(String result) throws  JSONException;
    }






     /* getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.week,
                android.R.layout.simple_spinner_dropdown_item);

        getSupportActionBar().setListNavigationCallbacks(mSpinnerAdapter, new ActionBar.OnNavigationListener() {
            @Override
            public boolean onNavigationItemSelected(int itemPosition, long itemId) {
                return true;
            }
        });*/

    /*   spinner=findViewById(R.id.cate_spinner);

        List<ListModel> list=new ArrayList<ListModel>();
        ListModel m1=new ListModel();
        m1.setTitle("1111");
        list.add(m1);
        ListModel m2=new ListModel();
        m2.setTitle("2222");
        list.add(m2);

        final ArrayAdapter<ListModel> adapter=new ArrayAdapter<ListModel>(this,android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setVisibility(View.INVISIBLE);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //选择列表项的操作
                ListModel model=adapter.getItem(position);
                DialogHelper.showToast("你点击了"+model.getTitle());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //未选中时候的操作
            }
        });*/
}
