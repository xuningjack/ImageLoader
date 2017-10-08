package com.jack.imageloader;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.ImageView;

import java.util.Map;


/**
 * 图片加载的基类
 * @Author Jack
 */
public abstract class BaseImageLoaderProvider{


    /**
     * 正常加载图片，没有headers和回调
     * @param activity 对应的Activity实例
     * @param imageLoader 图片加载过程中的公共参数
     * @param imageView 加载的图片
     * @param imgUrl 图片的url
     */
    public abstract void loadImage(Activity activity, ImageLoader imageLoader, ImageView imageView,
                                   String imgUrl);


    /**
     * 带有请求参数列表的加载图片
     * @param activity 对应的Activity实例
     * @param imageLoader 图片加载过程中的公共参数
     * @param imageView 加载的图片
     * @param imgUrl 图片的url
     * @param map 发送请求时需要的头部参数列表
     */
    public abstract void loadImage(Activity activity, ImageLoader imageLoader, ImageView imageView,
                                   String imgUrl, final Map<String, String> map);


    /**
     * 带有请求参数列表和成功后回调的加载图片
     * @param activity 对应的Activity实例
     * @param imageLoader 图片加载过程中的公共参数
     * @param imageView 加载的图片
     * @param imgUrl 图片的url
     * @param map 发送请求时需要的头部参数列表
     * @param callback 请求成功后的回调接口
     */
    public abstract void loadImage(Activity activity, ImageLoader imageLoader, ImageView imageView,
                                   String imgUrl, int radius, final Map<String, String> map, Callback callback);

    /**
     * 清除内存缓存
     * @param activity
     */
    public abstract void clearMemoryCache(Activity activity);


    /**
     * 图片加载的回调
     */
    public interface Callback{
        void callBack(String url, ImageView img, Bitmap bitmap);
    }

}