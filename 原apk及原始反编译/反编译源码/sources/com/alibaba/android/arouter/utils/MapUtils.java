package com.alibaba.android.arouter.utils;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class MapUtils {
    public static boolean a(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean b(Map<?, ?> map) {
        return !a(map);
    }
}
