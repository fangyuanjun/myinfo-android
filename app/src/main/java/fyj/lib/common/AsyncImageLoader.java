package fyj.lib.common;

import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class AsyncImageLoader {
	
	private  static AsyncImageLoader instance;
	public static AsyncImageLoader GetInstance()
	{
		if(instance==null){
			instance=new AsyncImageLoader();
		}
		
		return instance;
	}
	
	//存放缓存的图片对象
	private HashMap<String, SoftReference<Drawable>> imageCache;
    //私有的构造方法
	private AsyncImageLoader() {
		imageCache = new HashMap<String, SoftReference<Drawable>>();
	}
	//判断是否有缓存的图片，有的话返回缓存，没得话开启线程下载
	public Drawable loadDrawable(final String imageUrl,
			final ImageCallback imageCallback) {
		//有的情况
		if (imageCache.containsKey(imageUrl)) {
			SoftReference<Drawable> softReference = imageCache.get(imageUrl);
			Drawable drawable = softReference.get();
			//用的缓存图片
			if (drawable != null) {
				return drawable;
			}
		}
		//没的情况
		final  Handler handler = new Handler() {
			public void handleMessage(Message message) {
				if (imageCallback != null) {
					imageCallback.imageLoaded((Drawable) message.obj);
				}
			}
		};
		new Thread() {
			@Override
			public void run() {
				//新下载图片
				Drawable drawable = loadImageFromUrl(imageUrl);
				imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));
				Message message = handler.obtainMessage(0, drawable);
				handler.sendMessage(message);
			}
		}.start();
		return null;
	}
	//根据图片网络地址返回一个Drawable
	public static Drawable loadImageFromUrl(String url) {
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

	/* 设置ImageView控件的图片 */
	public static void SetImageViewDrawable(final ImageView control, String url) {

		Drawable dr = AsyncImageLoader.GetInstance().loadDrawable(url,
				new ImageCallback() {
					public void imageLoaded(Drawable imageDrawable) {
						control.setImageDrawable(imageDrawable);
					}
				});
	}

	public interface ImageCallback {
		public void imageLoaded(Drawable imageDrawable);
	}
}
