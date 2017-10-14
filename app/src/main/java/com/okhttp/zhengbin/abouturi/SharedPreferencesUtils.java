package com.okhttp.zhengbin.abouturi;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ZHengBin on 2017/9/25.
 */

public class SharedPreferencesUtils {

    public static void putString(Context context, String key, String content) {
        SharedPreferences sp = context.getSharedPreferences("userInfo.txt", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key,content);
        editor.commit();
    }
    public static String getString(Context context,String key) {
        SharedPreferences sp = context.getSharedPreferences("userInfo.txt", Context.MODE_PRIVATE);
        return sp.getString(key,"");
    }
}
