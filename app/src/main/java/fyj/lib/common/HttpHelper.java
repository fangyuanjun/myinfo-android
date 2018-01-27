package fyj.lib.common;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpHelper {
	
	 public static InputStream GetResponseStream(String url, String method, String data) throws IOException
	 {
		 // 建立URL对象，抛出异常
	      URL uri = new URL(url);
	      // 得到HttpURLConnection对象
	      HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
	      // 声明请求方式
	      conn.setRequestMethod(method);
	      // 设置连接超时
	      conn.setConnectTimeout(6 * 1000);
	      
	      if(method.equalsIgnoreCase("POST")&&data!=null&&!data.equals(""))
	      {
	      conn.setDoOutput(true);// 是否输入参数
	      byte[] bypes = data.getBytes();
	      OutputStream os=conn.getOutputStream();
	      os.write(bypes);// 输入参数
	      os.close();
	      }
	      
	      // 连接成功
	      if (conn.getResponseCode() == 200)
	      {
	          // 得到服务器传回来的数据，相对我们来说输入流
	          InputStream inputStream = conn.getInputStream();
	          
	          return inputStream;
	      }
	      
	     return null;
	 }
	 
	  public static String DoGet(String url) throws IOException
	  {
	      InputStream inStream=GetResponseStream(url,"GET",null);
	      String result=convertStreamToString(inStream);
	      
	      return result;
	  }
	
  public static String DoPost(String url,String data) throws IOException
  {
      InputStream inStream=GetResponseStream(url,"POST",data);
      String result=convertStreamToString(inStream);
      
      return result;
  }

  public static String DoPost(String url,Map<String,String> map) throws IOException
  {
	 
	  // 建立URL对象，抛出异常
      URL uri = new URL(url);
      // 得到HttpURLConnection对象
      HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
      // 声明请求方式
      conn.setRequestMethod("POST");
     // conn.setRequestProperty("Accept-Charset", "utf-8");
      //conn.setRequestProperty("contentType", "utf-8");
      conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
      //conn.setRequestProperty("Charset", "utf-8");

      // 设置连接超时
      conn.setConnectTimeout(6 * 1000);
      // 是否输入参数
      conn.setDoOutput(true);
      OutputStream os=conn.getOutputStream();
     // OutputStreamWriter w=new OutputStreamWriter(os,"utf-8");
  

		for (String key : map.keySet()) {
			String value=map.get(key)==null?"":map.get(key);
			// byte[] bypes =(key+"="+java.net.URLEncoder.encode(value,"utf-8")+"&").getBytes();
			 byte[] bypes =(key+"="+value+"&").getBytes();
			  os.write(bypes);// 输入参数
	
		}
      os.close();
    
      // 连接成功
      if (conn.getResponseCode() == 200)
      {
          // 得到服务器传回来的数据，相对我们来说输入流
          InputStream inputStream = conn.getInputStream();
          String result=convertStreamToString(inputStream);
          
          return result;
      }
      
      return null;
  }
  
	public static String convertStreamToString(InputStream is)
			throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		is.close();

		return sb.toString();
	}
	
	// 读取流文件的内容
		  public static byte[] readInStream(InputStream inputStream) throws Exception
		  {
		      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		      BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "gbk"));
		     
		      // 声明缓冲区
		      byte[] buffer = new byte[1024];
		      // 定义读取默认长度
		      int length = -1;
		      while ((length = inputStream.read(buffer)) != -1)
		      {
		          // 把缓冲区中输出到内存中
		          byteArrayOutputStream.write(buffer, 0, length);
		      }
		      // 关闭输出流
		      byteArrayOutputStream.close();
		      // 关闭输入流
		      inputStream.close();
		      // 返回这个输出流的字节数组
		      return byteArrayOutputStream.toByteArray();
		  }
}
