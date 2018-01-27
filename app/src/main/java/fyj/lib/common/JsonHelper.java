package fyj.lib.common;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
public class JsonHelper {

/*
	public static Map<String,String> mapObjectToMapString(Map<String, Object> map)
	{
		Map<String,String> newMap=new HashMap<String,String>();
		for (Object o : map.keySet()) {
			Object value=map.get(o);
		    if(value==null)
		    {
		    	newMap.put(o.toString(), null);
		    }
		    else
		    {
		    	newMap.put(o.toString(), value.toString());
		    }
		}
		
		return newMap;
	}
	*/
/**
 * 将Map<String, Object> map  转换成json格式
 * @param map
 * @return
 */
	public static String MapToJson(Map<String, Object> map) {
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		list.add(map);
		String json=ListMapToJson(list);
		
		return json;
	}

	public static Map<String, Object> JsonToMap(String json) {
		List<Map<String, Object>> list=JsonToListMap(json);
	    if(list.size()>0)
	    {
	    	return list.get(0);
	    }
	    
	    return null;
	}
	
	public static List<Map<String, Object>> JsonToListMap(String json) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Pattern p = Pattern.compile("\\{(\".*?\")\\}");
		Pattern p2 = Pattern.compile("\"(.*?)\":\"(.*?)\"");
		Matcher m = p.matcher(json);
		while (m.find()) {
			Matcher m2 = p2.matcher(m.group());
			Map<String, Object> map = new HashMap<String, Object>();
			while (m2.find()) {
				String key=m2.group(1);
				String value=m2.group(2);
				if(value!=null)
				{
					value=value.replace("&#39;", "\"");
				}
			    map.put(key, value);
			}
			list.add(map);
		}

		return list;
	}
	

	/**
	 * 
	 * @param list
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String ListMapToJson(List<Map<String, Object>> list) 
	{
		StringBuilder sb=new StringBuilder();
	     sb.append("[");

		 for(Map<String, Object> map :list)
		 {
			 sb.append("{");
			 String json="";
				for (Object o : map.keySet()) {
					Object value=map.get(o);
				     json += "\"" + o + "\":\"" +( value==null?"":value.toString().replace("\"", "&#39;")) + "\",";
				}
				
				json = json.substring(0, json.lastIndexOf(","));
				sb.append(json);
				sb.append("},");
		 }
		 
		  String result=sb.toString();
		  result=StringEx.trimEnd(result, ",")+"]";

		 return result;
	}
}
