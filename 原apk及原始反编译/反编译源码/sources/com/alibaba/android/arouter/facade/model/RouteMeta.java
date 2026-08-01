package com.alibaba.android.arouter.facade.model;

import com.alibaba.android.arouter.facade.enums.RouteType;
import java.util.Map;
import javax.lang.model.element.Element;

/* JADX INFO: loaded from: classes.dex */
public class RouteMeta {
    public RouteType a;
    public Element b;
    public Class<?> c;
    public String d;
    public String e;
    public int f;
    public int g;
    public Map<String, Integer> h;
    public String i;

    public RouteMeta() {
        this.f = -1;
    }

    public static RouteMeta a(RouteType routeType, Class<?> cls, String str, String str2, Map<String, Integer> map, int i, int i2) {
        return new RouteMeta(routeType, null, cls, null, str, str2, map, i, i2);
    }

    public Class<?> b() {
        return this.c;
    }

    public int c() {
        return this.g;
    }

    public String d() {
        return this.e;
    }

    public Map<String, Integer> e() {
        return this.h;
    }

    public String f() {
        return this.d;
    }

    public int g() {
        return this.f;
    }

    public RouteType h() {
        return this.a;
    }

    public RouteMeta i(Class<?> cls) {
        this.c = cls;
        return this;
    }

    public RouteMeta j(int i) {
        this.g = i;
        return this;
    }

    public RouteMeta k(String str) {
        this.e = str;
        return this;
    }

    public RouteMeta l(String str) {
        this.d = str;
        return this;
    }

    public RouteMeta m(int i) {
        this.f = i;
        return this;
    }

    public RouteMeta n(RouteType routeType) {
        this.a = routeType;
        return this;
    }

    public String toString() {
        return "RouteMeta{type=" + this.a + ", rawType=" + this.b + ", destination=" + this.c + ", path='" + this.d + "', group='" + this.e + "', priority=" + this.f + ", extra=" + this.g + ", paramsType=" + this.h + ", name='" + this.i + "'}";
    }

    public RouteMeta(RouteType routeType, Element element, Class<?> cls, String str, String str2, String str3, Map<String, Integer> map, int i, int i2) {
        this.f = -1;
        this.a = routeType;
        this.i = str;
        this.c = cls;
        this.b = element;
        this.d = str2;
        this.e = str3;
        this.h = map;
        this.f = i;
        this.g = i2;
    }
}
