package com.jack.imageloader;

import com.jack.imageloader.util.ImageLoaderUtils;



/**
 * 图片加载的设置类
 * @Author Jack
 */
public class ImageLoader {

    /**类型，大图中图小图 */
    private int mType;
    /**加载本地图片*/
    private int mImgRes;
    /**当没有成功加载的时候显示的占位图片*/
    private int mPlaceHolder;
    /**加载失败的时候显示的图片*/
    private int mErrorImg;
    /**是否只在WiFi下加载*/
    private int mStrategy;
    /**是否需要缓存*/
    private boolean mDisAbleCache;


    public int getType() {
        return mType;
    }

    public int getImgRes() {
        return mImgRes;
    }

    public int getPlaceHolder() {
        return mPlaceHolder;
    }

    public int getErrorImg() {
        return mErrorImg;
    }

    public int getStrategy() {
        return mStrategy;
    }

    public boolean isDisAbleCache() {
        return mDisAbleCache;
    }

    private ImageLoader(Builder builder){
        this.mType = builder.mType;
        this.mImgRes = builder.mImgRes;
        this.mPlaceHolder = builder.mPlaceHolder;
        this.mErrorImg = builder.mErrorImg;
        this.mStrategy = builder.mStrategy;
        this.mDisAbleCache = builder.mDisAbleCache;
    }

    /**
     * 静态内部类，构造器
     */
    public static class Builder{

        /**类型 (大图，中图，小图)*/
        private int mType;
        /**加载本地的图片*/
        private int mImgRes;
        /**图片加载失败时的显示图片*/
        private int mErrorImg;
        /**当没有成功加载的时候显示的图片*/
        private int mPlaceHolder;
        /**加载策略，是否在wifi下才加载*/
        private int mStrategy;
        /**不用缓存*/
        private boolean mDisAbleCache;
        /**图片加载的公共参数*/
        private ImageLoader mImageLoader = new ImageLoader(this);


        public Builder(){
            this.mType = ImageLoaderUtils.PIC_MEDIUM;
            this.mImgRes = 0;
            this.mErrorImg = 0;
            //this.mPlaceHolder = R.drawable.gray_tiger_holder;
            this.mStrategy = ImageLoaderUtils.LOAD_STRATEGY_NORMAL;
            this.mDisAbleCache = false;
        }

        public Builder type(int type){
            this.mType = type;
            return this;
        }

        public Builder placeHolder(int placeHolder){
            this.mPlaceHolder = placeHolder;
            return this;
        }


        public Builder strategy(int strategy){
            this.mStrategy = strategy;
            return this;
        }

        public Builder imgRes(int mImgRes){
            this.mImgRes = mImgRes;
            return this;
        }

        public Builder disableCache(boolean mDisAbleCache){
            this.mDisAbleCache = mDisAbleCache;
            return this;
        }

        public Builder errorImg(int errorId){
            this.mErrorImg = errorId;
            return this;
        }

        public ImageLoader build(){
            return mImageLoader;
        }
    }

}