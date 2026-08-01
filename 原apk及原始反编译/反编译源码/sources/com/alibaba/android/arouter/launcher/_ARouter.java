package com.alibaba.android.arouter.launcher;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.alibaba.android.arouter.core.LogisticsCenter;
import com.alibaba.android.arouter.exception.HandlerException;
import com.alibaba.android.arouter.exception.InitException;
import com.alibaba.android.arouter.exception.NoRouteFoundException;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.service.AutowiredService;
import com.alibaba.android.arouter.facade.service.DegradeService;
import com.alibaba.android.arouter.facade.service.InterceptorService;
import com.alibaba.android.arouter.facade.service.PathReplaceService;
import com.alibaba.android.arouter.facade.service.PretreatmentService;
import com.alibaba.android.arouter.facade.template.ILogger;
import com.alibaba.android.arouter.thread.DefaultPoolExecutor;
import com.alibaba.android.arouter.utils.DefaultLogger;
import com.alibaba.android.arouter.utils.TextUtils;
import java.util.concurrent.ThreadPoolExecutor;

/* JADX INFO: loaded from: classes.dex */
public final class _ARouter {
    public static ILogger a = new DefaultLogger("ARouter::");
    public static volatile boolean b = false;
    public static volatile boolean c = false;
    public static volatile _ARouter d = null;
    public static volatile boolean e = false;
    public static volatile ThreadPoolExecutor f = DefaultPoolExecutor.c();
    public static Handler g;
    public static Context h;
    public static InterceptorService i;

    /* JADX INFO: renamed from: com.alibaba.android.arouter.launcher._ARouter$4, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass4 {
        public static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[RouteType.values().length];
            a = iArr;
            try {
                iArr[RouteType.ACTIVITY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[RouteType.PROVIDER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[RouteType.BOARDCAST.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[RouteType.CONTENT_PROVIDER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                a[RouteType.FRAGMENT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                a[RouteType.METHOD.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                a[RouteType.SERVICE.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public static void e() {
        i = (InterceptorService) ARouter.d().a("/arouter/service/interceptor").B();
    }

    @Deprecated
    public static boolean h() {
        return c;
    }

    public static boolean i() {
        return b;
    }

    public static _ARouter k() {
        if (!e) {
            throw new InitException("ARouterCore::Init::Invoke init(context) first!");
        }
        if (d == null) {
            synchronized (_ARouter.class) {
                if (d == null) {
                    d = new _ARouter();
                }
            }
        }
        return d;
    }

    public static synchronized boolean l(Application application) {
        h = application;
        LogisticsCenter.d(application, f);
        a.info("ARouter::", "ARouter init success!");
        e = true;
        g = new Handler(Looper.getMainLooper());
        return true;
    }

    public static void m(Object obj) {
        AutowiredService autowiredService = (AutowiredService) ARouter.d().a("/arouter/service/autowired").B();
        if (autowiredService != null) {
            autowiredService.T(obj);
        }
    }

    public static synchronized void p() {
        b = true;
        a.info("ARouter::", "ARouter openDebug");
    }

    public static synchronized void q() {
        a.t0(true);
        a.info("ARouter::", "ARouter openLog");
    }

    public static void s(ILogger iLogger) {
        if (iLogger != null) {
            a = iLogger;
        }
    }

    public final Object a(final Postcard postcard, final int i2, final NavigationCallback navigationCallback) {
        final Context contextP = postcard.p();
        int i3 = AnonymousClass4.a[postcard.h().ordinal()];
        if (i3 == 1) {
            final Intent intent = new Intent(contextP, postcard.b());
            intent.putExtras(postcard.s());
            int iT = postcard.t();
            if (iT != 0) {
                intent.setFlags(iT);
            }
            if (!(contextP instanceof Activity)) {
                intent.addFlags(268435456);
            }
            String strO = postcard.o();
            if (!TextUtils.c(strO)) {
                intent.setAction(strO);
            }
            r(new Runnable() { // from class: com.alibaba.android.arouter.launcher._ARouter.3
                @Override // java.lang.Runnable
                public void run() {
                    _ARouter.this.t(i2, contextP, intent, postcard, navigationCallback);
                }
            });
            return null;
        }
        if (i3 == 2) {
            return postcard.v();
        }
        if (i3 == 3 || i3 == 4 || i3 == 5) {
            try {
                Object objNewInstance = postcard.b().getConstructor(new Class[0]).newInstance(new Object[0]);
                if (objNewInstance instanceof Fragment) {
                    ((Fragment) objNewInstance).setArguments(postcard.s());
                } else if (objNewInstance instanceof androidx.fragment.app.Fragment) {
                    ((androidx.fragment.app.Fragment) objNewInstance).setArguments(postcard.s());
                }
                return objNewInstance;
            } catch (Exception e2) {
                a.r0("ARouter::", "Fetch fragment instance error, " + TextUtils.a(e2.getStackTrace()));
            }
        }
        return null;
    }

    public Postcard f(String str) {
        if (TextUtils.c(str)) {
            throw new HandlerException("ARouter::Parameter is invalid!");
        }
        PathReplaceService pathReplaceService = (PathReplaceService) ARouter.d().h(PathReplaceService.class);
        if (pathReplaceService != null) {
            str = pathReplaceService.i2(str);
        }
        return g(str, j(str), Boolean.TRUE);
    }

    public Postcard g(String str, String str2, Boolean bool) {
        PathReplaceService pathReplaceService;
        if (TextUtils.c(str) || TextUtils.c(str2)) {
            throw new HandlerException("ARouter::Parameter is invalid!");
        }
        if (!bool.booleanValue() && (pathReplaceService = (PathReplaceService) ARouter.d().h(PathReplaceService.class)) != null) {
            str = pathReplaceService.i2(str);
        }
        return new Postcard(str, str2);
    }

    public final String j(String str) {
        if (TextUtils.c(str) || !str.startsWith("/")) {
            throw new HandlerException("ARouter::Extract the default group failed, the path must be start with '/' and contain more than 2 '/'!");
        }
        try {
            String strSubstring = str.substring(1, str.indexOf("/", 1));
            if (TextUtils.c(strSubstring)) {
                throw new HandlerException("ARouter::Extract the default group failed! There's nothing between 2 '/'!");
            }
            return strSubstring;
        } catch (Exception e2) {
            a.u0("ARouter::", "Failed to extract default group! " + e2.getMessage());
            return null;
        }
    }

    public Object n(Context context, final Postcard postcard, final int i2, final NavigationCallback navigationCallback) {
        PretreatmentService pretreatmentService = (PretreatmentService) ARouter.d().h(PretreatmentService.class);
        if (pretreatmentService != null && !pretreatmentService.z1(context, postcard)) {
            return null;
        }
        postcard.G(context == null ? h : context);
        try {
            LogisticsCenter.c(postcard);
            if (navigationCallback != null) {
                navigationCallback.c(postcard);
            }
            if (postcard.A()) {
                return a(postcard, i2, navigationCallback);
            }
            i.d2(postcard, new InterceptorCallback() { // from class: com.alibaba.android.arouter.launcher._ARouter.2
                @Override // com.alibaba.android.arouter.facade.callback.InterceptorCallback
                public void a(Postcard postcard2) {
                    _ARouter.this.a(postcard2, i2, navigationCallback);
                }

                @Override // com.alibaba.android.arouter.facade.callback.InterceptorCallback
                public void b(Throwable th) {
                    NavigationCallback navigationCallback2 = navigationCallback;
                    if (navigationCallback2 != null) {
                        navigationCallback2.b(postcard);
                    }
                    _ARouter.a.info("ARouter::", "Navigation failed, termination by interceptor : " + th.getMessage());
                }
            });
            return null;
        } catch (NoRouteFoundException e2) {
            a.u0("ARouter::", e2.getMessage());
            if (i()) {
                r(new Runnable(this) { // from class: com.alibaba.android.arouter.launcher._ARouter.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Toast.makeText(_ARouter.h, "There's no route matched!\n Path = [" + postcard.f() + "]\n Group = [" + postcard.d() + "]", 1).show();
                    }
                });
            }
            if (navigationCallback != null) {
                navigationCallback.a(postcard);
            } else {
                DegradeService degradeService = (DegradeService) ARouter.d().h(DegradeService.class);
                if (degradeService != null) {
                    degradeService.H(context, postcard);
                }
            }
            return null;
        }
    }

    public <T> T o(Class<? extends T> cls) {
        try {
            Postcard postcardB = LogisticsCenter.b(cls.getName());
            if (postcardB == null) {
                postcardB = LogisticsCenter.b(cls.getSimpleName());
            }
            if (postcardB == null) {
                return null;
            }
            postcardB.G(h);
            LogisticsCenter.c(postcardB);
            return (T) postcardB.v();
        } catch (NoRouteFoundException e2) {
            a.u0("ARouter::", e2.getMessage());
            return null;
        }
    }

    public final void r(Runnable runnable) {
        if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
            g.post(runnable);
        } else {
            runnable.run();
        }
    }

    public final void t(int i2, Context context, Intent intent, Postcard postcard, NavigationCallback navigationCallback) {
        if (i2 < 0) {
            ContextCompat.startActivity(context, intent, postcard.u());
        } else if (context instanceof Activity) {
            ActivityCompat.startActivityForResult((Activity) context, intent, i2, postcard.u());
        } else {
            a.u0("ARouter::", "Must use [navigation(activity, ...)] to support [startActivityForResult]");
        }
        if (-1 != postcard.q() && -1 != postcard.r() && (context instanceof Activity)) {
            ((Activity) context).overridePendingTransition(postcard.q(), postcard.r());
        }
        if (navigationCallback != null) {
            navigationCallback.d(postcard);
        }
    }
}
