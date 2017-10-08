package com.jack.imageloader.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Looper;


/**
 * 公共工具类
 * @author Jack
 */
public class Utils {

	/**
	 * 判断是否在主线程
	 * @return true是在UI主线程；false不在
     */
	public static boolean isOnMainThread(){
		return Looper.myLooper() == Looper.getMainLooper();
	}

	/**
	 * 判断是否有网络连接
	 * @param context
	 * @return
	 */
	public static boolean isNetworkConnected(Context context){
		if(context == null){
			return false;
		}
		ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();
		return (networkInfo != null && networkInfo.isConnectedOrConnecting());
	}

	/**
	 * 判断网络是否wifi连接
	 * @param context
	 * @return
	 */
	public static boolean isWifiConnected(Context context){
		if(context == null){
			return false;
		}
		ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();
		return (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI);
	}
}
