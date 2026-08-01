package com.alibaba.android.arouter.launcher;

import android.app.Application;
import android.content.Context;
import com.alibaba.android.arouter.exception.InitException;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.facade.template.ILogger;

/* JADX INFO: loaded from: classes.dex */
public final class ARouter {
    public static volatile ARouter a;
    public static volatile boolean b;
    public static ILogger c;

    @Deprecated
    public static boolean b() {
        return _ARouter.h();
    }

    public static boolean c() {
        return _ARouter.i();
    }

    public static ARouter d() {
        if (!b) {
            throw new InitException("ARouter::Init::Invoke init(context) first!");
        }
        if (a == null) {
            synchronized (ARouter.class) {
                if (a == null) {
                    a = new ARouter();
                }
            }
        }
        return a;
    }

    public static void e(Application application) {
        if (b) {
            return;
        }
        ILogger iLogger = _ARouter.a;
        c = iLogger;
        iLogger.info("ARouter::", "ARouter init start.");
        b = _ARouter.l(application);
        if (b) {
            _ARouter.e();
        }
        _ARouter.a.info("ARouter::", "ARouter init over.");
    }

    public static synchronized void i() {
        _ARouter.p();
    }

    public static synchronized void j() {
        _ARouter.q();
    }

    public static void k(ILogger iLogger) {
        _ARouter.s(iLogger);
    }

    public Postcard a(String str) {
        return _ARouter.k().f(str);
    }

    public void f(Object obj) {
        _ARouter.m(obj);
    }

    public Object g(Context context, Postcard postcard, int i, NavigationCallback navigationCallback) {
        return _ARouter.k().n(context, postcard, i, navigationCallback);
    }

    public <T> T h(Class<? extends T> cls) {
        return (T) _ARouter.k().o(cls);
    }
}
