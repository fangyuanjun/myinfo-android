package fyj.lib.common;

import java.io.InputStream;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

public class AsyncControlHelper {
	private static AsyncControlHelper instance;
	private AsyncControlHelper(){}
	public static AsyncControlHelper GetInstance(){
		if(instance==null)
		{
		 instance=new AsyncControlHelper();
		}
		return instance;
	}
	/* 设置ImageView控件的图片 */
	public void SetImageViewDrawable(final ImageView control,final String url) {
		new AsyncTask<Void,Void,Drawable>(){

			@Override
			protected Drawable doInBackground(Void... params) {
				Drawable drawable = loadImageFromUrl(url);
				return drawable;
			}

			@Override
			protected void onPostExecute(Drawable result) {
				// TODO Auto-generated method stub
				control.setImageDrawable(result);
			}

		}.execute();
	}
	
	//根据图片网络地址返回一个Drawable
		public  Drawable loadImageFromUrl(String url) {
			InputStream i = null;
			try {
				i = HttpHelper.GetResponseStream(url,"GET",null);
			} catch (Exception ex) {
				ex.printStackTrace();
			} 
			Drawable d = null;
			if (i != null) {
				d = Drawable.createFromStream(i, "src");
			}
			return d;
		}
}
