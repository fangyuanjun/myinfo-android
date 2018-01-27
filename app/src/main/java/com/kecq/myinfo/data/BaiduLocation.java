package com.kecq.myinfo.data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fyj.lib.common.HttpHelper;

public class BaiduLocation implements ILocation {

	//longitude 经度
	public String GetLocation(String longitude, String latitude) {
		// TODO Auto-generated method stub

		String result = null;
		try {
			String json = HttpHelper
					.DoGet("http://api.map.baidu.com/geocoder/v2/?ak=D200252c02041aac02015b01656582bf&callback=renderReverse&location="
							+ latitude
							+ ","
							+ longitude
							+ "&output=json&pois=1");
			Pattern p = Pattern
					.compile("\"formatted_address\"\\s*:\\s*\"(.*?)\"");
			Matcher m = p.matcher(json);
			while (m.find()) {
				result = m.group(1);
				break;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	public static void main(String [] args)
	{
		BaiduLocation lo=new BaiduLocation();
		String str=lo.GetLocation("116.322987", "39.983424071404");
		System.out.print(str);
	}
}
