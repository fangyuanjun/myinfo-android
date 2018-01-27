package com.kecq.myinfo.data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fyj.lib.common.HttpHelper;
import fyj.lib.common.JsonHelper;

public class GoogleLocation implements ILocation {

	public String GetLocation(String longitude, String latitude) {
		// TODO Auto-generated method stub
		String result = null;
		try {
			String json = HttpHelper.DoGet(
					"http://maps.google.com/maps/api/geocode/json?latlng="
							+ latitude + "," + longitude
							+ "&language=zh-CN&sensor=false");
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

}
