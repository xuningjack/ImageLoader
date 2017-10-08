package com.jack.imageloader.util;

import android.app.Activity;
import android.widget.ImageView;

import com.jack.imageloader.BaseImageLoaderProvider;
import com.jack.imageloader.instance.GlideImageLoaderProvider;
import com.jack.imageloader.ImageLoader;

import java.util.Map;


/**
 * 图片加载的工具类
 * @Author Jack
 */
public class ImageLoaderUtils {

    public static final int PIC_LARGE = 0;
    public static final int PIC_MEDIUM = 1;
    public static final int PIC_SMALL = 2;

    /**正常加载*/
    public static final int LOAD_STRATEGY_NORMAL = 0;
    /**只有在wifi下加载*/
    public static final int LOAD_STRATEGY_ONLY_WIFI = 1;

    private static ImageLoaderUtils mInstance;
    private BaseImageLoaderProvider mProvider;


    /**
     * 现在使用Glide的实现
     */
    private ImageLoaderUtils(){
        mProvider = new GlideImageLoaderProvider();
    }

    /**
     * 获得实例
     * @return
     */
    public static ImageLoaderUtils getInstance(){
        if(mInstance == null){
            mInstance = new ImageLoaderUtils();
        }
        return mInstance;
    }

    /**
     * 加载图片
     * @param activity 加载的activity
     * @param imageLoader 图片加载的设置信息
     * @param imageView 加载图片的ImageView
     * @param imgUrl 访问图片的地址
     */
    public void loadImage(Activity activity, ImageLoader imageLoader, ImageView imageView, String imgUrl){
        mProvider.loadImage(activity, imageLoader, imageView, imgUrl, 0, null, null);
    }

    /**
     * 加载图片
     * @param activity 加载的activity
     * @param imageLoader 图片加载的设置信息
     * @param imageView 加载图片的ImageView
     * @param imgUrl 访问图片的地址
     * @param radius 圆角dp
     */
    public void loadImage(Activity activity, ImageLoader imageLoader, ImageView imageView, String imgUrl, int radius){
        mProvider.loadImage(activity, imageLoader, imageView, imgUrl, radius, null, null);
    }

    /**
     * 加载图片
     * @param activity 加载的activity
     * @param imageLoader 图片加载的设置信息
     * @param imageView 加载图片的ImageView
     * @param imgUrl 访问图片的地址
     * @param headers 访问url时可能需要的headers参数
     */
    public void loadImage(Activity activity, ImageLoader imageLoader,
                          ImageView imageView, String imgUrl, Map<String, String> headers) {
        mProvider.loadImage(activity, imageLoader, imageView, imgUrl, headers);
    }

    /**
     * 加载图片
     * @param activity 加载的activity
     * @param imageLoader 图片加载的设置信息
     * @param imageView 加载图片的ImageView
     * @param imgUrl 访问图片的地址
     * @param headers 访问url时可能需要的headers参数
     * @param callback 加载成功后的回调，如可以设置图片的宽高
     */
    public void loadImage(Activity activity, ImageLoader imageLoader,
                          ImageView imageView, String imgUrl, Map<String , String> headers,
                          BaseImageLoaderProvider.Callback callback) {
        mProvider.loadImage(activity, imageLoader, imageView, imgUrl, 0, headers, callback);
    }

    /**
     * 清除内存缓存，防止oom
     * @param activity
     */
    public void clearMemoryCache(Activity activity){
        mProvider.clearMemoryCache(activity);
    }

}