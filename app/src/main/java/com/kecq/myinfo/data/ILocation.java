package com.kecq.myinfo.data;

public interface ILocation {

	/**
	 * 获取地理位置
	 * @param longitude  经度
	 * @param latitude   纬度
	 * @return
	 */
 public String GetLocation(String longitude, String latitude);
}
