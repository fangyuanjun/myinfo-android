package fyj.lib.common;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class StringEx {

	/** 移除末尾的字符 */
	public static String trimEnd(String str,String trim)
	{
	String st=str;
	if(st.lastIndexOf(trim)!=-1&&(st.lastIndexOf(trim)==(st.length()-1)))
	{
		st=str.substring(0,str.length()-trim.length());
	 while(st.lastIndexOf(trim)==(st.length()-1))
	 {
		st=st.substring(0,st.length()-trim.length());
	 }
	}
	return st;
	}
	/** 移除开始的字符 */
	public static String trimStart(String str,String trim)
	{

	String st=str;
	if(st.lastIndexOf(trim)!=-1&&(st.indexOf(trim)==0))
	{
		st=str.substring(trim.length(),str.length());
	while(st.indexOf(trim)==0)
	 {
		st=st.substring(trim.length(),st.length());
	 }
	}
	return st;
	}
	/** 移除开始和末尾的字符 */
	public static String trim(String str,String trim)
	{
		return trimStart(trimEnd(str,trim),trim);
	}

	public static String byte2hex(byte[] b) {
		  StringBuffer hs = new StringBuffer(b.length);
		  String stmp = "";
		  int len = b.length;
		  for (int n = 0; n < len; n++) {
		   stmp = Integer.toHexString(b[n] & 0xFF);
		   if (stmp.length() == 1)
		    hs = hs.append("0").append(stmp);
		   else {
		    hs = hs.append(stmp);
		   }
		  }
		  return String.valueOf(hs);
		 }
	
	/**
	 * 计算百分比
	 * 
	 * @param dividend
	 *            -被除数
	 * @param divisor
	 *            -除数
	 * @return
	 */
	public static String getPercent(double dividend, double divisor) {

		double source = dividend / divisor;
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMinimumFractionDigits(2);
		return nf.format(source);
	}

	/**
	 * 产生指定长度的随机数字字符串
	 * 
	 * @param patStr
	 *            -从该字符串的每个字母产生
	 * @param length
	 *            -产生字符串的长度
	 * @return
	 */
	public static String randomString(String patStr, int length) {
		Random randGen = null;
		char[] numbersAndLetters = null;
		Object initLock = new Object();
		if (length < 1) {
			return null;
		}
		if (randGen == null) {
			synchronized (initLock) {
				if (randGen == null) {
					randGen = new Random();
					numbersAndLetters = (patStr).toCharArray();
				}
			}
		}
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(10)];
		}
		return new String(randBuffer);
	}

	
	/**
	 * 获取系统时间
	 * 
	 * @return
	 */
	public static String getDataTime() {
		SimpleDateFormat si = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return si.format(new Date());
	}



	/**
	 * 将字符串数组生成一个网页形式的下拉选项，选项的值就是显示的值
	 * 
	 * @param name
	 * @return
	 */
	public static String getSelect(String name[]) {
		String str = "";
		for (int i = 0; i < name.length; i++) {
			str += "<option value=" + name[i] + ">" + name[i] + "</option>";
		}
		return str;
	}

	/**
	 * 将字符串数组生成一个网页形式的下拉选项,并选择一项,选项的值就是显示的值
	 * 
	 * @param names
	 * @param selectValue
	 *            被选择的项
	 * @return
	 */
	public static String getSelect(List<String> names, String selectValue) {
		String str = "";
		if(selectValue==null)
			selectValue="";
		for (int i = 0; i < names.size(); i++) {
			if (names.get(i).equals(selectValue))
				str += "<option value=" + names.get(i) + "  selected='selected'>"
						+ names.get(i) + "</option>";
			else
				str += "<option value=" +names.get(i) + ">" +names.get(i) + "</option>";
		}
		return str;
	}

	/**
	 * 将字符串数组生成一个网页形式的下拉选项，并有为参数选项值
	 * 
	 * @param names
	 *            显示的选项
	 * @param values
	 *            选项值
	 * @return
	 */
	public static String getSelect(List<String> names, List<String> values) {
		String str = "";
		for (int i = 0; i < names.size(); i++) {
			str += "<option value=" + values.get(i) + ">" + names.get(i) + "</option>";
		}
		return str;
	}

	/**
	 * 将字符串数组生成一个网页形式的下拉选项，并有为参数选项值,选择一项
	 * 
	 * @param names
	 *            显示的选项
	 * @param values
	 *            选项值
	 * @param selectValue
	 *            选择的项
	 * @return
	 */
	public static String getSelect(List<String> names, List<String> values,
			String selectValue) {
		if(selectValue==null)
			selectValue="";
		String str = "";
		for (int i = 0; i < names.size(); i++) {
			if (values.get(i).equals(selectValue))
				str += "<option value=" + values.get(i) + "  selected='selected'>"
						+ names.get(i) + "</option>";
			else
				str += "<option value=" + values.get(i) + ">" + names.get(i)
						+ "</option>";
		}
		return str;
	}

	public static String getChaxunSelect(String selectedValue) {
		String str = "";
		if(selectedValue==null)
			selectedValue="";
		
		if(selectedValue.equals("&lt")||selectedValue.equals("<"))
		str+="<option value=\"&lt\" selected='selected'>小于</option>";
		else
			str+="<option value=\"&lt\" >小于</option>";
		
		if(selectedValue.equals("&gt=")||selectedValue.equals(">"))
			str+="<option value=\"&gt\" selected='selected'>大于</option>";
			else
				str+="<option value=\"&gt\" >大于</option>";
		
		if(selectedValue.equals("&lt=")||selectedValue.equals("<="))
			str+="<option value=\"&lt=\" selected='selected'>小于等于</option>";
			else
				str+="<option value=\"&lt=\" >小于等于</option>";
		
		if(selectedValue.equals("&gt=")||selectedValue.equals(">="))
			str+="<option value=\"&gt=\" selected='selected'>大于等于</option>";
			else
				str+="<option value=\"&gt=\" >大于等于</option>";
		
		if(selectedValue.equals("="))
			str+="<option value=\"=\" selected='selected'>等于</option>";
			else
				str+="<option value=\"=\" >等于</option>";
		
		if(selectedValue.equals("!="))
			str+="<option value=\"!=\" selected='selected'>不等于</option>";
			else
				str+="<option value=\"!=\" >不等于</option>";
		
		if(selectedValue.equals("like"))
			str+="<option value=\"like\" selected='selected'>像</option>";
			else
				str+="<option value=\"like\" >像</option>";
		return str;
	}

	/** 取得A时间减去B时间后的毫秒数  */
	public static long getPoorSeconds(Date a,Date b){
	    Calendar timea = Calendar.getInstance();
	    Calendar timeb = Calendar.getInstance();
	    timea.setTime(a);
	    timeb.setTime(b);
	    return timea.getTimeInMillis() - timeb.getTimeInMillis();
	}
	
	public static String FilterSql(String sql)
	{
		sql=sql.replaceAll("(?i)(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)", "");
		sql=sql.replaceAll("'", "");
		sql=sql.replaceAll(",", "");

       return sql;
	}
	
	public static String Html2Text(String inputString) { 
        String htmlStr = inputString; //含html标签的字符串 
            String textStr =""; 
      Pattern p_script;
      java.util.regex.Matcher m_script; 
      Pattern p_style;
      java.util.regex.Matcher m_style; 
      Pattern p_html;
      java.util.regex.Matcher m_html; 
   
      try { 
       String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script> }
        String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style> }
           String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式 
      
          p_script = Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
           m_script = p_script.matcher(htmlStr); 
          htmlStr = m_script.replaceAll(""); //过滤script标签 

          p_style = Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE); 
          m_style = p_style.matcher(htmlStr); 
          htmlStr = m_style.replaceAll(""); //过滤style标签 
      
          p_html = Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
          m_html = p_html.matcher(htmlStr); 
          htmlStr = m_html.replaceAll(""); //过滤html标签 
      
       textStr = htmlStr; 
      
      }catch(Exception e) { 
               System.err.println("Html2Text: " + e.getMessage()); 
      } 
   
      return textStr;//返回文本字符串 
       }   

	public static String ToHtml(String str)
	{
		   String[] tmp = str.split(";&#|&#|;");  
		     StringBuffer sb = new StringBuffer("");  
		      for (int i=0; i<tmp.length; i++ ){  
		         if (tmp[i].matches("\\d{5}")){  
		         sb.append((char)Integer.parseInt(tmp[i]));  
		        } else {  
		              sb.append(tmp[i]);  
		         }  
		     }  
		  
		      String result=sb.toString();
		      
		  return result;  
	}
	
	/**
	 * 获取网址根   返回诸如http://www.baidu.com/
	 * @param url
	 * @return
	 */
	public static String getHttpRootPath(String url)
	{
		String temp=url.substring("https://".length());
		temp=temp.substring(temp.indexOf("/")+1);  //提取后面的
	    temp=url.replace(temp, "");	
	    
	    return temp;
	}
}
