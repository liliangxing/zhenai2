package com.alibaba.android.arouter.facade.enums;

/* JADX INFO: loaded from: classes.dex */
public enum RouteType {
    ACTIVITY(0, "android.app.Activity"),
    SERVICE(1, "android.app.Service"),
    PROVIDER(2, "com.alibaba.android.arouter.facade.template.IProvider"),
    CONTENT_PROVIDER(-1, "android.app.ContentProvider"),
    BOARDCAST(-1, ""),
    METHOD(-1, ""),
    FRAGMENT(-1, "android.app.Fragment"),
    UNKNOWN(-1, "Unknown route type");

    public String className;
    public int id;

    RouteType(int i, String str) {
        this.id = i;
        this.className = str;
    }
}
