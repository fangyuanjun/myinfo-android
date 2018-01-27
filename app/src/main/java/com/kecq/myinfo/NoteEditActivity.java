package com.kecq.myinfo;

import android.location.Location;
import android.location.LocationListener;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class NoteEditActivity extends AppCompatActivity {


    private double longitude; // 经度
    private double latitude; // 纬度
    private Location location;
    private String display; // 根据经纬度api查询地点http://maps.google.com/maps/api/geocode/xml?latlng=39.910093,116.403945&language=zh-CN&sensor=false

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);

       /*
        mLocationClient = new LocationClient(getApplicationContext()); //声明LocationClient类
        mLocationClient.registerLocationListener( myListener ); //注册监听函数
        mLocationClient.setAK( "A9e4f37d0e4bdc5b0859fa059976ccf2" );
        LocationClientOption option = new LocvationClientOption();
        option.setOpenGps(true);
        option.setAddrType("all");//返回的定位结果包含地址信息
        option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
        option.setScanSpan(5000);//设置发起定位请求的间隔时间为5000ms
        option.disableCache(true);//禁止启用缓存定位
        option.setPoiNumber(5); //最多返回POI个数
        option.setPoiDistance(1000); //poi查询距离
        option.setPoiExtraInfo(true); //是否需要POI的电话和地址等详细信息
        mLocationClient.setLocOption(option);
        */
    }

    public void post_backClick(View arg0) {
        // TODO Auto-generated method stub
        this.finish();
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {

        }
    };

    Runnable task = new Runnable() {
        public void run() {
            // TODO Auto-generated method stub
            try {
                Message msg = Message.obtain();
                msg.what = 0;
                mHandler.sendMessage(msg);
                msg = Message.obtain(); // 重新获取 很重要
                msg.what = 1;
                //msg.obj = getPostResult();
                mHandler.sendMessage(msg);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            }// TODO 1
        }
    };

    Runnable locationTask = new Runnable() {
        public void run() {
            // TODO Auto-generated method stub
            if (location != null) {
                double lat = location.getLatitude();
                double lng = location.getLongitude();
                latitude = lat;
                longitude = lng;

                //display = lo.GetLocation(lng+"", lat+"");  //通过地图api获取位置
                Message msg = Message.obtain();
                msg.what = 2;
                mHandler.sendMessage(msg);
            }
            else
            {
                Message msg = Message.obtain();
                msg.what = 3;
                mHandler.sendMessage(msg);
            }
        }
    };



    public class MyLocationListener  {

    /*
	double lat = location.getLatitude();
	double lng = location.getLongitude();
	latitude = lat;
	longitude = lng;
	ILocation lo=new BaiduLocation();
	display = lo.GetLocation(lng+"", lat+"");  //通过地图api获取位置
	Message msg = Message.obtain();
	msg.what = 2;
	mHandler.sendMessage(msg);
	*/
            //logMsg(sb.toString());
    }

    private final LocationListener locationListener = new LocationListener() {

        public void onLocationChanged(Location location) {
            updateWithNewLocation(location);
        }

        public void onProviderDisabled(String provider) {
            updateWithNewLocation(null);
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    private void updateWithNewLocation(Location location) {

    }

    public void locationClick(View v) {
        if (v instanceof ImageView) {
            ImageView imageView = (ImageView) v;
            TextView textView = (TextView) findViewById(R.id.txt_location);
            if (imageView.getTag() == null
                    || imageView.getTag().toString() == "0") {
                //imageView.setImageResource(R.drawable.lbs_icon_enable);
                imageView.setTag("1");
                textView.setText("正在获取位置......");

                /*
                if (mLocationClient != null)
                {
                    if(!mLocationClient.isStarted())
                    {
                        mLocationClient.start();
                    }
                    mLocationClient.requestLocation();
                }
                else
                {
                    //Log.d("LocSDK4", "locClient is null or not started");
                }
                */
				/*
				LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
				Criteria criteria = new Criteria();
				criteria.setAccuracy(Criteria.ACCURACY_FINE);
				criteria.setAltitudeRequired(false);
				criteria.setBearingRequired(false);
				criteria.setCostAllowed(true);
				criteria.setPowerRequirement(Criteria.POWER_LOW);
				String provider = locationManager.getBestProvider(criteria,
						true);
			  location = locationManager
						.getLastKnownLocation(provider);
				locationManager.requestLocationUpdates(provider, 2000, 10,
						locationListener);

				final boolean gpsEnabled = locationManager
						.isProviderEnabled(LocationManager.GPS_PROVIDER);
				if (!gpsEnabled) {
					// Build an alert dialog here that requests that the user
					// enable
					// the location services, then when the user clicks the "OK"
					// button,
					// call enableLocationSettings()
				}
				new Thread(locationTask).start();
				*/
            } else {
                //imageView.setImageResource(R.drawable.lbs_icon_disable);
                imageView.setTag("0");
                textView.setText("显示所在位置");
                this.latitude = 0;
                this.longitude = 0;
            }
        }
    }

    private void enableLocationSettings() {
        //Intent settingsIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        //startActivity(settingsIntent);
    }

    public void post_Click(View arg0) {
        // TODO Auto-generated method stub
        EditText editText=(EditText)findViewById(R.id.post_editText);
        if(editText.getText().toString().equals(""))
        {
            //diag.Alert("提示", "发表内容不能为空");
            return;
        }
        new Thread(task).start();
    }
}
