package com.jack.imageloader.instance;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.Headers;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jack.imageloader.BaseImageLoaderProvider;
import com.jack.imageloader.ImageLoader;
import com.jack.imageloader.util.ImageLoaderUtils;
import com.jack.imageloader.util.Utils;

import java.util.Map;


/**
 * 图片加载的Glide实现
 * @Author Jack
 */
public class GlideImageLoaderProvider extends BaseImageLoaderProvider {

    /**
     * 正常加载图片，没有headers和回调
     * @param activity
     * @param imgLoader
     * @param imageView
     * @param imgUrl
     */
    @Override
    public void loadImage(Activity activity, ImageLoader imgLoader,
                          final ImageView imageView, final String imgUrl) {
        loadImage(activity, imgLoader, imageView, imgUrl, 0, null, null);
    }

    /**
     * 带有请求参数列表的加载图片
     * @param activity
     * @param imgLoader
     * @param imageView
     * @param imgUrl
     * @param map
     */
    @Override
    public void loadImage(Activity activity, ImageLoader imgLoader, final ImageView imageView,
                          final String imgUrl, final Map<String, String> map) {
        loadImage(activity, imgLoader, imageView, imgUrl, 0, map, null);
    }

    /**
     * 带有请求参数列表和成功后回调的加载图片
     * @param activity 对应的Activity实例
     * @param imgloader
     * @param imageView 加载的图片
     * @param imgUrl 图片的url
     * @param radius 圆角的弧度
     * @param map 发送请求时需要的头部参数列表
     * @param callback 请求成功后的回调接口
     */
    @Override
    public void loadImage(Activity activity, ImageLoader imgloader, final ImageView imageView,
                          final String imgUrl, int radius, final Map<String, String> map, final Callback callback) {

        RequestListener<String, GlideDrawable> listener = new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model,
                                       Target<GlideDrawable> target,
                                       boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target,
                                           boolean isFromMemoryCache, boolean isFirstResource) {
                if(resource != null && resource.getCurrent() instanceof GlideBitmapDrawable){
                    if(callback == null){
                        imageView.setImageBitmap(((GlideBitmapDrawable)resource.getCurrent()).getBitmap());
                    }else{
                        callback.callBack(imgUrl, imageView,
                                ((GlideBitmapDrawable)resource.getCurrent()).getBitmap());
                    }
                }
                return false;
            }
        };


        //加载图片的时候判断一下activity是否已经finish或者onPause了
        if (activity != null && !activity.isFinishing()) {
            boolean isOnMainThread = Utils.isOnMainThread();
            if (!isOnMainThread) {
                return;
            }

            if(map == null){   //图片请求无headers中有参数
                if (imgloader.isDisAbleCache()) {   //不用缓存，每次请求都能刷新图片，即使url相同
                    Glide.with(activity)
                            .load(imgUrl)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .placeholder(imgloader.getPlaceHolder())
                            .error(imgloader.getErrorImg())
                            .listener(listener)
                            .bitmapTransform(new GlideRoundedCornersTransformation(activity, radius))
                            .into(imageView);
                } else { //正常加载
                    boolean isNetworkConnected = Utils.isNetworkConnected(activity);
                    if (!isNetworkConnected) {  //没有网络连接，加载缓存
                        loadCache(activity, imgloader, imageView, imgUrl, radius, listener);
                        return;
                    } else { //有网判断是什么网
                        int strategy = imgloader.getStrategy();
                        if (strategy == ImageLoaderUtils.LOAD_STRATEGY_ONLY_WIFI) {   //在wifi情况下
                            if (Utils.isWifiConnected(activity)) {
                                //如果是在wifi下才加载图片，并且当前网络是wifi,直接加载
                                loadNormal(activity, imgloader, imageView, imgUrl, radius, listener);
                            } else {  //如果是在wifi下才加载图片，并且当前网络不是wifi，加载缓存
                                loadCache(activity, imgloader, imageView, imgUrl, radius, listener);
                            }
                        } else {  //如果不是在wifi下才加载图片
                            loadNormal(activity, imgloader, imageView, imgUrl, radius, listener);
                        }
                    }
                }
            }else{   //图片请求需要headers中有参数，目前不需要缓存
                LazyHeaders.Builder builder = new LazyHeaders.Builder();
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    builder.addHeader(entry.getKey(), entry.getValue());
                }
                Headers headers = builder.build();
                GlideUrl glideUrl = new GlideUrl(imgUrl, headers);
                Glide.with(activity)
                        .load(glideUrl)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .placeholder(imgloader.getPlaceHolder())
                        .error(imgloader.getErrorImg())
                        .bitmapTransform(new GlideRoundedCornersTransformation(activity, radius))
                        .into(imageView);
            }
        }
    }


    /**
     * 使用Glide加载网络图片
     * @param activity
     * @param img
     */
    private void loadNormal(Activity activity, ImageLoader img, ImageView imageView,
                            String imgUrl, int radius, RequestListener<String, GlideDrawable> listener) {
        Context ctx = activity.getApplicationContext();
        if (img.getImgRes() == 0) {  //加载网络图片
            DrawableTypeRequest<String> request = Glide.with(ctx).load(imgUrl);
            request.bitmapTransform(new GlideRoundedCornersTransformation(activity, radius));
            if (img.getPlaceHolder() != 0) {   //设置背景图
                imageView.setBackgroundResource(img.getPlaceHolder());
            }
            if (img.getErrorImg() != 0) {
                request.error(img.getErrorImg());
            }
            request.listener(listener);
            request.into(imageView);
        } else {   //加载本地图片
            Glide.with(ctx).load(img.getImgRes())
                    .placeholder(img.getPlaceHolder()).error(img.getErrorImg())
                    .bitmapTransform(new GlideRoundedCornersTransformation(activity, radius))
                    .diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
        }
    }


    /**
     * 使用Glide加载缓存图片，默认打开内存缓存
     * @param activity
     * @param img
     */
    private void loadCache(Activity activity, ImageLoader img, ImageView imageView,
                           String imgUrl, int radius, RequestListener<String, GlideDrawable> listener) {
        Context ctx = activity.getApplicationContext();
        if (img.getImgRes() == 0) {  //加载网络图片
            Glide.with(ctx).load(imgUrl)
                    .placeholder(img.getPlaceHolder()).error(img.getErrorImg())
                    .diskCacheStrategy(DiskCacheStrategy.ALL).listener(listener)
                    .bitmapTransform(new GlideRoundedCornersTransformation(activity, radius))
                    .into(imageView);
        } else {  //加载本地图片
            Glide.with(ctx).load(img.getImgRes())
                    .placeholder(img.getPlaceHolder()).error(img.getErrorImg())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .bitmapTransform(new GlideRoundedCornersTransformation(activity, radius))
                    .into(imageView);
        }
    }

    @Override
    public void clearMemoryCache(Activity activity) {
        Glide.get(activity).clearMemory();
    }
}