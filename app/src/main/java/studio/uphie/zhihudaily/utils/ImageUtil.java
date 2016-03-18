package studio.uphie.zhihudaily.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class ImageUtil {

    public static final String TEMP_IMG_CACHE_FOLDER = "img";//缓存路径
    private static final long MAX_SIZE_CACHE = 1024 * 1024 * 200;//最大缓存

    public static void init(Context context, String cachePath) {
        File cacheDir = new File(cachePath);
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(context)
                .setBaseDirectoryPath(cacheDir)
                .setBaseDirectoryName(TEMP_IMG_CACHE_FOLDER)
                .setMaxCacheSize(MAX_SIZE_CACHE)
                .build();
        ImagePipelineConfig imagePipelineConfig = ImagePipelineConfig.newBuilder(context).setMainDiskCacheConfig(diskCacheConfig).build();
        Fresco.initialize(context, imagePipelineConfig);
    }

    public static void showLocalImage(String path, SimpleDraweeView view) {
        view.setImageURI(Uri.parse("file://" + path));
    }

    public static void showDrawableImage(int resId, SimpleDraweeView view) {
        view.setImageURI(Uri.parse("res://cn.tianyilm.client/" + resId));
    }

    public static void showAssetsImage(String path, SimpleDraweeView view) {
        view.setImageURI(Uri.parse("asset://" + path));
    }

    public static void displayImage(String url, SimpleDraweeView view) {
        view.setImageURI(Uri.parse(url));
    }

    public static void clearFrescoDiscCache() {
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
//      imagePipeline.clearMemoryCaches();
        imagePipeline.clearDiskCaches();
//      combines above two lines
//      imagePipeline.clearCaches();
    }

}
