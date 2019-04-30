package com.jim.common.utils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * OkGo二次封装网络工具
 * Created by jim on 2017/9/16.
 */

public class OkGoUtils {


    //GET方法，不需要TAG
    public static void get(String url, Map<String, String> paramMap, StringCallback callback) {
        OkGo.<String>get(url).params(paramMap).execute(callback);
    }

    //GET方法，需要传递TAG
    public static void get(String url, Map<String, String> paramMap, Object tag, StringCallback callback) {
        OkGo.<String>get(url).params(paramMap).tag(tag).execute(callback);
    }

    //GET方法
    public static void get(String url, StringCallback callback) {
        OkGo.<String>get(url).execute(callback);
    }

    //post，需要传递TAG
    public static void post(String url, Map<String, String> paramMap, Object tag, StringCallback callback) {
        OkGo.<String>post(url).params(paramMap).tag(tag).execute(callback);
    }

    //post，不需要传递TAG
    public static void post(String url, Map<String, String> paramMap, StringCallback callback) {
        OkGo.<String>post(url).params(paramMap).execute(callback);
    }

    //post,不需要传参
    public static void post(String url, StringCallback callback) {
        OkGo.<String>post(url).execute(callback);
    }


    //post,需要传递表头(token)
    public static void post(String url, Map<String, String> paramMap, String token, StringCallback callback) {
        OkGo.<String>post(url).headers("token", token).params(paramMap).execute(callback);
    }

    //post,需要传递表头(token)以及对象
    public static void post(String url, String json, String token, StringCallback callback) {
        OkGo.<String>post(url).headers("token", token).upJson(json).execute(callback);
    }
//post，文件下载
    public static void download(@NotNull String url, @NotNull HashMap<String, String> paramMap, @NotNull FileCallback callback) {
        OkGo.<File>post(url)
                .params(paramMap)
                .execute(callback);
    }
}
