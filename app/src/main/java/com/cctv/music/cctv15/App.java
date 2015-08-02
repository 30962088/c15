package com.cctv.music.cctv15;

import android.app.Application;

import com.cctv.music.cctv15.utils.AppConfig;
import com.cctv.music.cctv15.utils.Preferences;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class App extends Application{

    public static ImageLoader mImageLoader = ImageLoader.getInstance();



    @Override
    public void onCreate() {
        super.onCreate();


        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext()).denyCacheImageMultipleSizesInMemory()
                .memoryCacheExtraOptions(768, 1280)
                .memoryCache(new UsingFreqLimitedMemoryCache(8 * 1024 * 1024))
                .memoryCacheSize(8 * 1024 * 1024)
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .threadPoolSize(3).threadPriority(Thread.NORM_PRIORITY - 1)
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        // 初始化ImageLoader的与配置。
        mImageLoader.init(config);
        AppConfig.init(this);
        Preferences.init(this);
    }
}
