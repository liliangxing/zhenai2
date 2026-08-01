package com.alibaba.android.arouter.utils;

import android.net.Uri;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class TextUtils {
    public static String a(StackTraceElement[] stackTraceElementArr) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement stackTraceElement : stackTraceElementArr) {
            sb.append("    at ");
            sb.append(stackTraceElement.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    public static String b(String str) {
        return (!str.contains("|") || str.endsWith("|")) ? str : str.substring(0, str.indexOf("|"));
    }

    public static boolean c(CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }

    public static Map<String, String> d(Uri uri) {
        String encodedQuery = uri.getEncodedQuery();
        if (encodedQuery == null) {
            return Collections.emptyMap();
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        int i = 0;
        do {
            int iIndexOf = encodedQuery.indexOf(38, i);
            if (iIndexOf == -1) {
                iIndexOf = encodedQuery.length();
            }
            int iIndexOf2 = encodedQuery.indexOf(61, i);
            if (iIndexOf2 > iIndexOf || iIndexOf2 == -1) {
                iIndexOf2 = iIndexOf;
            }
            String strSubstring = encodedQuery.substring(i, iIndexOf2);
            if (!android.text.TextUtils.isEmpty(strSubstring)) {
                linkedHashMap.put(Uri.decode(strSubstring), Uri.decode(iIndexOf2 == iIndexOf ? "" : encodedQuery.substring(iIndexOf2 + 1, iIndexOf)));
            }
            i = iIndexOf + 1;
        } while (i < encodedQuery.length());
        return Collections.unmodifiableMap(linkedHashMap);
    }
}
