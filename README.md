# ImageLoader
Custom interface picture loading module, which supports Glide and so on（自定义接口的图片加载工具module，支持 Glide等）  

效果图：  
![Alt text](https://github.com/xuningjack/ImageLoader/raw/master/image/image.jpg)    


使用，详见demo：  
1、在项目中的build.gradle中增加 compile project(path: ':imageloader')  
2、初始化：
ImageLoader mImageLoader = new ImageLoader.Builder().placeHolder(0).disableCache(true).build();  
3、加载图片：  
//直角的图片展示   
ImageLoaderUtils.getInstance().loadImage(this, mImageLoader, mImageView, mImageUrl, 0);  
//圆角的图片展示，半径200  
ImageLoaderUtils.getInstance().loadImage(this, mImageLoader, mRoundImageView, mImageUrl, 200);   

最全的调用：  
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
ImageLoaderUtils.getInstance() loadImage(Activity activity, ImageLoader imgloader, final ImageView imageView,
                          final String imgUrl, int radius, final Map<String, String> map, final Callback callback)


