package studio.uphie.zhihudaily.http;

import android.app.Activity;
import android.content.Context;
import android.os.Build;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import studio.uphie.zhihudaily.utils.SysUtil;

/**
 * Created by Uphie on 2016/2/26 0026.
 * Email:uphie7@gmail.com
 */
public class HttpClient {
    private static Context context;
    private static AsyncHttpClient asyncHttpClient;
    private static OnInteractionListener onInteractionListener = null;

    public static void init(Context ctx) {
        context = ctx;
        asyncHttpClient = new AsyncHttpClient();
        //below are not required
        asyncHttpClient.addHeader("x-api-version", "3");
        asyncHttpClient.addHeader("x-app-version", "2.5.4");
        asyncHttpClient.addHeader("x-device", SysUtil.getPhoneModel());
        asyncHttpClient.addHeader("x-os", "Android " + Build.VERSION.RELEASE);
        asyncHttpClient.addHeader("za", "OS=Android " + Build.VERSION.RELEASE + "&Platform=" + SysUtil.getPhoneModel());
    }

    public static void get(String url, TextHttpResponseHandler textHttpResponseHandler) {
        //        asyncHttpClient.addHeader("Authorization","");
        asyncHttpClient.get(context, url, textHttpResponseHandler);
    }

    public static void postJson(String url, String param, TextHttpResponseHandler textHttpResponseHandler) {
        try {
            asyncHttpClient.post(context, url, new StringEntity(param), "application/json; charset=UTF-8", textHttpResponseHandler);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void downloadFile(String url, BinaryHttpResponseHandler handler) {
        asyncHttpClient.get(context, url, handler);
    }


    public static void uploadFile(String path, String url) throws Exception{
        File file = new File(path);
        if (file.exists()&&file.length()>0){
            RequestParams params = new RequestParams();
            params.put("upload",file);
            asyncHttpClient.post(context, url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                   //Todo
                   if (statusCode== Activity.RESULT_OK){
                       if (onInteractionListener!=null){
                           onInteractionListener.onInteraction(responseBody);
                       }
                   }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    onInteractionListener.onInteraction(responseBody);
                }
            });
        }
    }

    //调用接口前，此方法必须先调用
    public void initOnInteractionListener(OnInteractionListener onInteractionListener) {
        this.onInteractionListener = onInteractionListener;
    }

}
