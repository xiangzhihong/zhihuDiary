package studio.uphie.zhihudaily.utils;


import com.google.gson.Gson;

/**
 * Created by Uphie on 2015/9/6.
 * Email: uphie7@gmail.com
 */
public class JsonUtil {
    public static String getJson(Object object) {
        return new Gson().toJson(object);
    }

    public static <T> T getEntity(String json, Class<T> clazz) {
        return new Gson().fromJson(json, clazz);
    }

}
