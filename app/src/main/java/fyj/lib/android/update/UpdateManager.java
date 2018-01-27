package fyj.lib.android.update;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

import org.json.JSONObject;

import fyj.lib.android.update.IUpdateCallback;
//import sun.net.www.protocol.http.HttpURLConnection;

import fyj.lib.common.HttpHelper;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageInfo;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

public class UpdateManager {

	private Context mContext;
	private IUpdateCallback callback;
	//检查版本url
	private String versionUrl="http://192.168.1.3:8080/javafyj/service/version.action";
	// 安装包路径
	private String apkUrl;

	private Dialog noticeDialog;

	private Dialog downloadDialog;
	// 下载包安装路径
	private static final String savePath = "/mnt/sdcard/";
	// 进度条与通知ui刷新的handler 和msg常量
	private ProgressBar mProgress;
	private int progress;
	private boolean canceled = false;
	
    //正在检查版本
    private static final int CHECKING=1;
    //取消更新
    private static final int CHECK_CANCELED=2;
	//检查到新版本
	private static final int HAS_NEW_VERSION=3;
    //没有检查到新版本
    private static final int NO_NEW_VERSION=4; 
    //下载错误
    private static final int UPDATE_DOWNLOAD_ERROR=5;
    //下载被取消
    private static final int UPDATE_DOWNLOAD_CANCELED=6;
    //正在下载
    private static final int UPDATE_DOWNLOADING=7;
    //下载完毕
    private static final int UPDATE_DOWNLOAD_COMPLETED=8;
    
    
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			try {
				switch (msg.what) {
				case UPDATE_DOWNLOADING:
					mProgress.setProgress(progress);
					break;
				case UPDATE_DOWNLOAD_COMPLETED:
					downloadDialog.dismiss();
					callback.downloadCompleted(true, (String)msg.obj);
					//installApk();
					break;
				case HAS_NEW_VERSION:
					showNoticeDialog();
					break;
				case NO_NEW_VERSION:
					callback.checkUpdateCompleted(false);
					break;
				case UPDATE_DOWNLOAD_ERROR:
					downloadDialog.dismiss();
					callback.downloadCompleted(false, null);
					break;
				case UPDATE_DOWNLOAD_CANCELED:
					downloadDialog.dismiss();
					callback.downloadCanceled();
					break;
				default:
					break;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	};

	//构造函数
	public UpdateManager(Context context,IUpdateCallback callback){
		this.mContext=context;
		this.callback=callback;
	}
	
	Runnable checkUpdateTask = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				String json = HttpHelper.DoGet(versionUrl);
				JSONObject obj = new JSONObject(json);  
					int newVersionCode = obj.getInt("versionCode");
					apkUrl = obj.getString("downloadUrl");
					PackageInfo pInfo = mContext.getPackageManager()
							.getPackageInfo(mContext.getPackageName(), 0);
					// String curVersion=pInfo.versionName;
					int curVersionCode = pInfo.versionCode;
					// 如果发现新版本
					if (newVersionCode > curVersionCode) {
						mHandler.sendEmptyMessage(HAS_NEW_VERSION);
					}
					else
					{
						mHandler.sendEmptyMessage(NO_NEW_VERSION);
					}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	//外部接口 让主Activity调用
	public void checkUpdateInfo()
	{
		new Thread(checkUpdateTask).start();
	}
	
	public void showNoticeDialog(){
		//PopupWindow p=new PopupWindow(mContext);
		Builder builder=new Builder(mContext);
		builder.setCancelable(false);  
		builder.setTitle("软件版本更新");
		builder.setMessage("有新的软件包，推荐下载");
		builder.setPositiveButton("下载", new OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				showDownloadDialog();
			}
		});
		
		builder.setNegativeButton("以后再说", new OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				callback.checkCanceled();
			}
		});
		
		noticeDialog = builder.create(); 
		noticeDialog.show();
	}
	
	private void showDownloadDialog(){

		/*
		Builder builder=new Builder(mContext);
		builder.setTitle("软件版本更新");
		builder.setCancelable(false);  
		final LayoutInflater inflater=LayoutInflater.from(mContext);
		View v=inflater.inflate(R.layout.activity_progress, null);
		mProgress=(ProgressBar)v.findViewById(R.id.progress);
		builder.setView(v);
		builder.setNegativeButton("取消", new OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				canceled = true;  
			}
		});
		 downloadDialog = builder.create();  
		 downloadDialog.show();
		 
		 //下载
		 new Thread(mdownApkRunnable).start();
		 */
	}
	
	private Runnable mdownApkRunnable=new Runnable(){
		@Override
		public void run(){
			try
			{
			      URL uri = new URL(apkUrl);
			      java.net.HttpURLConnection conn = ( java.net.HttpURLConnection) uri.openConnection();
			      conn.setRequestMethod("GET");
			      conn.setConnectTimeout(6 * 1000);
			      // 连接成功
			      if (conn.getResponseCode() == 200)
			      {
			    	  int length=conn.getContentLength();
			          // 得到服务器传回来的数据，相对我们来说输入流
			          InputStream is = conn.getInputStream();
			          
			          File file = new File(savePath);  
		                if(!file.exists()){  
		                    file.mkdir();  
		                }  
		                
			          File apkFile=new File(savePath,"note.apk");		
						 if(apkFile.exists())
						 {
							 apkFile.delete();
						 }
						 
						 FileOutputStream fos=new FileOutputStream(apkFile);
						 int count=0;
						 byte buffer[]=new byte[4096];
						 do{
							 int numread=is.read(buffer);
							 count+=numread;
							 progress =(int)(((float)count / length) * 100);
							 //更新进度
							 mHandler.sendEmptyMessage(UPDATE_DOWNLOADING);  
							 if(numread<=0)
							 {
								 //下载完成通知安装
								 mHandler.sendMessage(mHandler.obtainMessage(UPDATE_DOWNLOAD_COMPLETED,savePath+"note.apk"));
								 break;
							 }
							 fos.write(buffer,0,numread);
						 }
						 while(!canceled);//点击取消就停止下载. 
						 
						 if(canceled)
		                 {
							 mHandler.sendEmptyMessage(UPDATE_DOWNLOAD_CANCELED);
		                 }
						 
						 fos.close();
						 is.close();
			      }
			}
			 catch (Exception e) { 
				 //e.printStackTrace();  
				 System.out.print(e.getMessage());
				 mHandler.sendMessage(mHandler.obtainMessage(UPDATE_DOWNLOAD_ERROR,e.getMessage()));
			 }
		}
	};
	
	//安装apk
	/*
	private void installApk(){
		File apkFile=new File(savePath,"note.apk");
		if(apkFile.exists())
		{
			//不能使用Intent.FLAG_ACTIVITY_NEW_TASK是因为Intent.FLAG_ACTIVITY_NEW_TASK无法获得返回的结果；不能使用Intent.FLAG_ACTIVITY_CLEAR_TOP是因为可能会有多个apk同时安装  
			 Intent i = new Intent(Intent.ACTION_VIEW);  
			 i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);//或FLAG_ACTIVITY_PREVIOUS_IS_TOP  
			 i.setDataAndType(Uri.parse("file://"+apkFile.toString()), "application/vnd.android.package-archive");
			 mContext.startActivity(i);
			// mContext.start
			 //mContext.startActivityForResult(i, 0);
		}
	}*/
}
