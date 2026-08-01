package com.alibaba.android.arouter.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import com.alibaba.android.arouter.launcher.ARouter;

/* JADX INFO: loaded from: classes.dex */
public class PackageUtils {
    public static String a;
    public static int b;

    public static PackageInfo a(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 16384);
        } catch (Exception unused) {
            ARouter.c.r0("ARouter::", "Get package info error.");
            return null;
        }
    }

    public static boolean b(Context context) {
        PackageInfo packageInfoA = a(context);
        if (packageInfoA != null) {
            String str = packageInfoA.versionName;
            int i = packageInfoA.versionCode;
            SharedPreferences sharedPreferences = context.getSharedPreferences("SP_AROUTER_CACHE", 0);
            if (str.equals(sharedPreferences.getString("LAST_VERSION_NAME", null)) && i == sharedPreferences.getInt("LAST_VERSION_CODE", -1)) {
                return false;
            }
            a = str;
            b = i;
        }
        return true;
    }

    public static void c(Context context) {
        if (android.text.TextUtils.isEmpty(a) || b == 0) {
            return;
        }
        context.getSharedPreferences("SP_AROUTER_CACHE", 0).edit().putString("LAST_VERSION_NAME", a).putInt("LAST_VERSION_CODE", b).apply();
    }
}
