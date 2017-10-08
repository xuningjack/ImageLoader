# ImageLoader
Custom interface picture loading module, which supports Glide and so on（自定义接口的图片加载工具module，支持 Glide等）  


效果图：  
![Alt text](https://github.com/xuningjack/ImageLoader/raw/master/image/image.jpg)    

使用，详见demo：  

初始化：
ImageLoader mImageLoader = new ImageLoader.Builder().placeHolder(0).disableCache(true).build();

//直角的图片展示  
ImageLoaderUtils.getInstance().loadImage(this, mImageLoader, mImageView, mImageUrl, 0);  
//圆角的图片展示，半径200  
ImageLoaderUtils.getInstance().loadImage(this, mImageLoader, mRoundImageView, mImageUrl, 200);  

