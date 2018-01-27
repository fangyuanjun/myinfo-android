package com.kecq.myinfo;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.opengl.Visibility;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.kecq.myinfo.adapter.MainFragmentAdapter;
import com.kecq.myinfo.fragment.ArticleListContentFragment;
import com.kecq.myinfo.fragment.MyregisterListFragment;
import com.kecq.myinfo.fragment.MyProfileFragment;
import com.kecq.myinfo.model.ListModel;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import fyj.lib.SignHelper;
import fyj.lib.android.DialogHelper;

public class MainActivity extends AppCompatActivity {

    private  ViewPager viewPager;
    private  List<Fragment> fragmentList;
    private MainFragmentAdapter adapter;
    private ArticleListContentFragment fragment1;
    private MyregisterListFragment myregisterListFragment;
    private BottomNavigationBar bottomNavigationBar;
    private BottomNavigationView mBottomNavigationView;
    private  AppCompatSpinner spinner;
    private CateSelectWindow popupwindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTopBar();
        initBottomBar();

        viewPager = (ViewPager) findViewById(R.id.main_viewPager);

        /*
        List viewList = new ArrayList<View>();

        LocalActivityManager manager = new LocalActivityManager (this,true);
        manager.dispatchCreate(savedInstanceState);
        Intent intent_article=new Intent(new Intent(getApplication(),ArticleListActivity.class));
        View view_article=manager.startActivity("view_article", intent_article).getDecorView();
        viewList.add(view_article);

        MainViewAdapter adapter=new MainViewAdapter(viewList);
        viewPager.setAdapter(adapter);
        */

        fragmentList=new ArrayList<Fragment>();
        fragment1=new ArticleListContentFragment();
        //ArticleListTitleFragment fragment2=new ArticleListTitleFragment();
        MyProfileFragment fragment2=new MyProfileFragment();
        fragmentList.add(fragment1);
        fragmentList.add(fragment2);

        adapter=new MainFragmentAdapter(this.getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // position是当前选中的页面的Position
                bottomNavigationBar.selectTab(position);
                // getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
                invalidateOptionsMenu();
               /* switch (position) {
                    case 0:
                        mBottomNavigationView.setSelectedItemId(R.id.tab_home);
                        break;
                    case 1:
                        mBottomNavigationView.setSelectedItemId(R.id.tab_my);
                        break;

                    default:
                        break;
                }*/
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // position :当前页面，及你点击滑动的页面；positionOffset:当前页面偏移的百分比；positionOffsetPixels:当前页面偏移的像素位置
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //state ==1的时表示正在滑动，state==2的时表示滑动完毕了，state==0的时表示什么都没做。
            }
        });
/*


        LayoutInflater lf = LayoutInflater.from(this);
        View view1 = lf.inflate(R.layout.activity_article_list, null);

        LocalActivityManager manager = new LocalActivityManager (this,true);
        manager.dispatchCreate(savedInstanceState);
        Intent intent2=new Intent(new Intent(getApplication(),NoteListActivity.class));
        View view2=manager.startActivity("B", intent2).getDecorView();

        View view3 = new View(MainActivity.this);
*/

    }

    private void initTopBar(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle("我的东东");
        //toolbar.setSubtitle("SubTitle");
        //toolbar.setLogo(R.mipmap.ic_launcher);

        //设置导航图标要在setSupportActionBar方法之后
        setSupportActionBar(toolbar);
        //toolbar.setNavigationIcon(R.mipmap.ic_drawer_home);

        popupwindow =new CateSelectWindow(this, new CateSelectWindow.OnItemSelectListener() {
            @Override
            public void onItemSelect(String id) {
                fragment1.DoSearch(id);
                popupwindow.dismiss();
            }

            @Override
            public String getCategoryDateUrl() {
                return  Temp.BaseServerUrl+ SignHelper.SignUserUrl("/api/Article/QueryCategory");
            }

        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.main_menu_cate:

                            popupwindow.showAsDropDown(findViewById(R.id.main_menu_cate), 0, 10);

                        break;
                    case R.id.main_menu_fyj:
                        fragment1.ChangeDb("fyj");
                        popupwindow.initData("fyj");
                        //View v=	LayoutInflater.from(this).inflate(R.layout.activity_setting,null);
                        //ViewManager wm = getWindowManager();   // 获取WindowManager对象
                        //wm.addView(v, getWindow().getAttributes());
                        break;
                    case R.id.main_menu_kecq:
                        fragment1.ChangeDb("kecq");
                        popupwindow.initData("kecq");
                        break;
                    case R.id.main_menu_fangyjcom:
                        fragment1.ChangeDb("fangyjcom");
                        popupwindow.initData("fangyjcom");
                        break;
                    case R.id.main_menu_movie:
                        startActivity(new Intent(getApplication(),MovieListActivity.class));
                        break;
                    case R.id.main_menu_favorite:
                        startActivity(new Intent(getApplication(),FavoriteListActivity.class));
                        break;
                    case R.id.main_menu_note:
                        startActivity(new Intent(getApplication(),NoteListActivity.class));
                        break;
                    case R.id.main_menu_myregister:
                        /*if(myregisterListFragment==null){
                            myregisterListFragment=new MyregisterListFragment();
                            fragmentList.add(myregisterListFragment);
                            adapter.notifyDataSetChanged();
                        }
                        */
                        startActivity(new Intent(getApplication(),MyregisterListActivity.class));
                        break;
                    case R.id.main_menu_test:
                        startActivity(new Intent(getApplication(),TestActivity.class));
                        break;
                }
                return true;
            }
        });

    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // 动态设置ToolBar状态
        switch (viewPager.getCurrentItem()) {
            case 0:
                menu.findItem(R.id.menu_search).setVisible(true);
                menu.findItem(R.id.main_menu_cate).setVisible(true);
                break;
            case 1:
                menu.findItem(R.id.menu_search).setVisible(false);
                menu.findItem(R.id.main_menu_cate).setVisible(false);
                break;
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void initBottomBar(){

        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.main_bottomBar);

        BadgeItem badgeItem = new BadgeItem().setBackgroundColor(R.color.red).setText("99");  //小红点数字
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_DEFAULT);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar.setBarBackgroundColor(R.color.black);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_bottom_home, "首页").setActiveColorResource(R.color.white))

                //.addItem(new BottomNavigationItem(R.drawable.ic_tv_white_24dp, "电影").setActiveColorResource(R.color.white).setBadgeItem(badgeItem))
                .addItem(new BottomNavigationItem(R.drawable.ic_my, "我").setActiveColorResource(R.color.white))
                .setFirstSelectedPosition(0)
                .initialise(); //所有的设置需在调用该方法前完成


        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {//这里也可以使用SimpleOnTabSelectedListener
            @Override
            public void onTabSelected(int position) {//未选中 -> 选中
                //if(viewPager.getCurrentItem()!=position)
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(int position) {//选中 -> 未选中
            }

            @Override
            public void onTabReselected(int position) {//选中 -> 选中
            }
        });

       /* mBottomNavigationView =  findViewById(R.id.main_bottomView);
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                switch (itemId) {
                    case R.id.tab_home:
                        viewPager.setCurrentItem(0);
                        return true;//返回true,否则tab按钮不变色,未被选中
                    case R.id.tab_my:
                        viewPager.setCurrentItem(1);
                        return true;
                    default:
                        break;
                }

                return false;
            }
        } );*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        //通过MenuItem得到SearchView
        SearchView mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        //搜索图标按钮(打开搜索框的按钮)的点击事件
        mSearchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //spinner.setVisibility(View.VISIBLE);
            }
        });

        //搜索框文字变化监听
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                fragment1.DoSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }
        });

        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                fragment1.DoSearch(null);
                //spinner.setVisibility(View.INVISIBLE);
                return false;
            }
        });

        return  true;
    }

    //不会触发下面的事件  不知为何
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())//得到被点击的item的itemId
        {
            case R.id.menu_search://这里的Id就是布局文件中定义的Id，在用R.id.XXX的方法获取出来

                break;
        }

        return   super.onOptionsItemSelected(item);
    }
    private boolean isExit;

    //onKeyDown  无效  应该是ViewPaer的问题
    @Override
    public boolean dispatchKeyEvent(KeyEvent event)
    {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 ){
            if(event.getAction() != KeyEvent.ACTION_DOWN)  //解决监听2次的问题
            {
                return false;
            }
            exit();
            return false;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }
    public void exit(){
        if (!isExit) {
            isExit = true;
            DialogHelper.showToast("再按一次退出程序");
            existHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            System.exit(0);
        }
    }

    Handler existHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            isExit = false;
        }
    };
}
