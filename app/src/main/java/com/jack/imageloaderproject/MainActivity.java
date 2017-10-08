package com.jack.imageloaderproject;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.jack.imageloader.ImageLoader;
import com.jack.imageloader.util.ImageLoaderUtils;





public class MainActivity extends Activity {

    //显示图片的地址
    private final String mImageUrl = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png";
    private ImageView mImageView, mRoundImageView;
    private ImageLoader mImageLoader = new ImageLoader.Builder().placeHolder(0).disableCache(true).build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView)findViewById(R.id.imageView);
        mRoundImageView = (ImageView)findViewById(R.id.roundImageView);

        //直角的图片展示
        ImageLoaderUtils.getInstance().loadImage(this, mImageLoader, mImageView, mImageUrl, 0);
        //圆角的图片展示
        ImageLoaderUtils.getInstance().loadImage(this, mImageLoader, mRoundImageView, mImageUrl, 200);

    }
}
