package com.alibaba.android.arouter.core;

import android.content.Context;
import android.net.Uri;
import com.alibaba.android.arouter.exception.HandlerException;
import com.alibaba.android.arouter.exception.NoRouteFoundException;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.enums.TypeKind;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IInterceptorGroup;
import com.alibaba.android.arouter.facade.template.IProvider;
import com.alibaba.android.arouter.facade.template.IProviderGroup;
import com.alibaba.android.arouter.facade.template.IRouteGroup;
import com.alibaba.android.arouter.facade.template.IRouteRoot;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.android.arouter.utils.ClassUtils;
import com.alibaba.android.arouter.utils.MapUtils;
import com.alibaba.android.arouter.utils.PackageUtils;
import com.alibaba.android.arouter.utils.TextUtils;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

/* JADX INFO: loaded from: classes.dex */
public class LogisticsCenter {
    public static Context a;
    public static ThreadPoolExecutor b;
    public static boolean c;

    /* JADX INFO: renamed from: com.alibaba.android.arouter.core.LogisticsCenter$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[RouteType.values().length];
            a = iArr;
            try {
                iArr[RouteType.PROVIDER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[RouteType.FRAGMENT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public static synchronized void a(String str, IRouteGroup iRouteGroup) throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        if (Warehouse.groupsIndex.containsKey(str)) {
            Warehouse.groupsIndex.get(str).getConstructor(new Class[0]).newInstance(new Object[0]).loadInto(Warehouse.routes);
            Warehouse.groupsIndex.remove(str);
        }
        if (iRouteGroup != null) {
            iRouteGroup.loadInto(Warehouse.routes);
        }
    }

    public static Postcard b(String str) {
        RouteMeta routeMeta = Warehouse.providersIndex.get(str);
        if (routeMeta == null) {
            return null;
        }
        return new Postcard(routeMeta.f(), routeMeta.d());
    }

    public static synchronized void c(Postcard postcard) {
        if (postcard == null) {
            throw new NoRouteFoundException("ARouter::No postcard!");
        }
        RouteMeta routeMeta = Warehouse.routes.get(postcard.f());
        if (routeMeta != null) {
            postcard.i(routeMeta.b());
            postcard.n(routeMeta.h());
            postcard.m(routeMeta.g());
            postcard.j(routeMeta.c());
            Uri uriY = postcard.y();
            if (uriY != null) {
                Map<String, String> mapD = TextUtils.d(uriY);
                Map<String, Integer> mapE = routeMeta.e();
                if (MapUtils.b(mapE)) {
                    for (Map.Entry<String, Integer> entry : mapE.entrySet()) {
                        k(postcard, entry.getValue(), entry.getKey(), mapD.get(entry.getKey()));
                    }
                    postcard.s().putStringArray("wmHzgD4lOj5o4241", (String[]) mapE.keySet().toArray(new String[0]));
                }
                postcard.V("NTeRQWvye18AkPd6G", uriY.toString());
            }
            int i = AnonymousClass1.a[routeMeta.h().ordinal()];
            if (i == 1) {
                Class<?> clsB = routeMeta.b();
                IProvider iProvider = Warehouse.providers.get(clsB);
                if (iProvider == null) {
                    try {
                        iProvider = (IProvider) clsB.getConstructor(new Class[0]).newInstance(new Object[0]);
                        iProvider.init(a);
                        Warehouse.providers.put(clsB, iProvider);
                    } catch (Exception e) {
                        ARouter.c.s0("ARouter::", "Init provider failed!", e);
                        throw new HandlerException("Init provider failed!");
                    }
                }
                postcard.H(iProvider);
                postcard.z();
            } else if (i == 2) {
                postcard.z();
            }
        } else {
            if (!Warehouse.groupsIndex.containsKey(postcard.d())) {
                throw new NoRouteFoundException("ARouter::There is no route match the path [" + postcard.f() + "], in group [" + postcard.d() + "]");
            }
            try {
                if (ARouter.c()) {
                    ARouter.c.debug("ARouter::", String.format(Locale.getDefault(), "The group [%s] starts loading, trigger by [%s]", postcard.d(), postcard.f()));
                }
                a(postcard.d(), null);
                if (ARouter.c()) {
                    ARouter.c.debug("ARouter::", String.format(Locale.getDefault(), "The group [%s] has already been loaded, trigger by [%s]", postcard.d(), postcard.f()));
                }
                c(postcard);
            } catch (Exception e2) {
                throw new HandlerException("ARouter::Fatal exception when loading group meta. [" + e2.getMessage() + "]");
            }
        }
    }

    public static synchronized void d(Context context, ThreadPoolExecutor threadPoolExecutor) throws HandlerException {
        Set<String> setA;
        a = context;
        b = threadPoolExecutor;
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            e();
            if (c) {
                ARouter.c.info("ARouter::", "Load router map by arouter-auto-register plugin.");
            } else {
                if (ARouter.c() || PackageUtils.b(context)) {
                    ARouter.c.info("ARouter::", "Run with debug mode or new install, rebuild router map.");
                    setA = ClassUtils.a(a, "com.alibaba.android.arouter.routes");
                    if (!setA.isEmpty()) {
                        context.getSharedPreferences("SP_AROUTER_CACHE", 0).edit().putStringSet("ROUTER_MAP", setA).apply();
                    }
                    PackageUtils.c(context);
                } else {
                    ARouter.c.info("ARouter::", "Load router map from cache.");
                    setA = new HashSet<>(context.getSharedPreferences("SP_AROUTER_CACHE", 0).getStringSet("ROUTER_MAP", new HashSet()));
                }
                ARouter.c.info("ARouter::", "Find router map finished, map size = " + setA.size() + ", cost " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms.");
                jCurrentTimeMillis = System.currentTimeMillis();
                for (String str : setA) {
                    if (str.startsWith("com.alibaba.android.arouter.routes.ARouter$$Root")) {
                        ((IRouteRoot) Class.forName(str).getConstructor(new Class[0]).newInstance(new Object[0])).loadInto(Warehouse.groupsIndex);
                    } else if (str.startsWith("com.alibaba.android.arouter.routes.ARouter$$Interceptors")) {
                        ((IInterceptorGroup) Class.forName(str).getConstructor(new Class[0]).newInstance(new Object[0])).loadInto(Warehouse.interceptorsIndex);
                    } else if (str.startsWith("com.alibaba.android.arouter.routes.ARouter$$Providers")) {
                        ((IProviderGroup) Class.forName(str).getConstructor(new Class[0]).newInstance(new Object[0])).loadInto(Warehouse.providersIndex);
                    }
                }
            }
            ARouter.c.info("ARouter::", "Load root element finished, cost " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms.");
            if (Warehouse.groupsIndex.size() == 0) {
                ARouter.c.r0("ARouter::", "No mapping files were found, check your configuration please!");
            }
            if (ARouter.c()) {
                ARouter.c.debug("ARouter::", String.format(Locale.getDefault(), "LogisticsCenter has already been loaded, GroupIndex[%d], InterceptorIndex[%d], ProviderIndex[%d]", Integer.valueOf(Warehouse.groupsIndex.size()), Integer.valueOf(Warehouse.interceptorsIndex.size()), Integer.valueOf(Warehouse.providersIndex.size())));
            }
        } catch (Exception e) {
            throw new HandlerException("ARouter::ARouter init logistics center exception! [" + e.getMessage() + "]");
        }
    }

    public static void e() {
        c = false;
        g("com.alibaba.android.arouter.routes.ARouter$$Root$$arouterapi");
        g("com.alibaba.android.arouter.routes.ARouter$$Root$$main");
        g("com.alibaba.android.arouter.routes.ARouter$$Root$$module_love_zone");
        g("com.alibaba.android.arouter.routes.ARouter$$Root$$module_moments");
        g("com.alibaba.android.arouter.routes.ARouter$$Root$$module_short_video");
        g("com.alibaba.android.arouter.routes.ARouter$$Root$$module_recommend");
        g("com.alibaba.android.arouter.routes.ARouter$$Root$$module_message");
        g("com.alibaba.android.arouter.routes.ARouter$$Root$$module_pay");
        g("com.alibaba.android.arouter.routes.ARouter$$Root$$module_certification");
        g("com.alibaba.android.arouter.routes.ARouter$$Root$$module_emotion");
        g("com.alibaba.android.arouter.routes.ARouter$$Root$$module_mine");
        g("com.alibaba.android.arouter.routes.ARouter$$Root$$module_service");
        g("com.alibaba.android.arouter.routes.ARouter$$Root$$module_live");
        g("com.alibaba.android.arouter.routes.ARouter$$Root$$module_profile");
        g("com.alibaba.android.arouter.routes.ARouter$$Root$$module_psychology_test");
        g("com.alibaba.android.arouter.routes.ARouter$$Root$$module_login");
        g("com.alibaba.android.arouter.routes.ARouter$$Root$$lib_senseid_motion");
        g("com.alibaba.android.arouter.routes.ARouter$$Root$$business");
        g("com.alibaba.android.arouter.routes.ARouter$$Root$$module_common");
        g("com.alibaba.android.arouter.routes.ARouter$$Root$$module_call");
        g("com.alibaba.android.arouter.routes.ARouter$$Providers$$arouterapi");
        g("com.alibaba.android.arouter.routes.ARouter$$Providers$$main");
        g("com.alibaba.android.arouter.routes.ARouter$$Providers$$module_love_zone");
        g("com.alibaba.android.arouter.routes.ARouter$$Providers$$module_moments");
        g("com.alibaba.android.arouter.routes.ARouter$$Providers$$module_short_video");
        g("com.alibaba.android.arouter.routes.ARouter$$Providers$$module_recommend");
        g("com.alibaba.android.arouter.routes.ARouter$$Providers$$module_message");
        g("com.alibaba.android.arouter.routes.ARouter$$Providers$$module_pay");
        g("com.alibaba.android.arouter.routes.ARouter$$Providers$$module_certification");
        g("com.alibaba.android.arouter.routes.ARouter$$Providers$$module_emotion");
        g("com.alibaba.android.arouter.routes.ARouter$$Providers$$module_mine");
        g("com.alibaba.android.arouter.routes.ARouter$$Providers$$module_service");
        g("com.alibaba.android.arouter.routes.ARouter$$Providers$$module_live");
        g("com.alibaba.android.arouter.routes.ARouter$$Providers$$module_profile");
        g("com.alibaba.android.arouter.routes.ARouter$$Providers$$module_psychology_test");
        g("com.alibaba.android.arouter.routes.ARouter$$Providers$$module_login");
        g("com.alibaba.android.arouter.routes.ARouter$$Providers$$lib_senseid_motion");
        g("com.alibaba.android.arouter.routes.ARouter$$Providers$$business");
        g("com.alibaba.android.arouter.routes.ARouter$$Providers$$module_common");
        g("com.alibaba.android.arouter.routes.ARouter$$Providers$$module_call");
    }

    public static void f() {
        if (c) {
            return;
        }
        c = true;
    }

    public static void g(String str) {
        if (TextUtils.c(str)) {
            return;
        }
        try {
            Object objNewInstance = Class.forName(str).getConstructor(new Class[0]).newInstance(new Object[0]);
            if (objNewInstance instanceof IRouteRoot) {
                j((IRouteRoot) objNewInstance);
            } else if (objNewInstance instanceof IProviderGroup) {
                i((IProviderGroup) objNewInstance);
            } else if (objNewInstance instanceof IInterceptorGroup) {
                h((IInterceptorGroup) objNewInstance);
            } else {
                ARouter.c.info("ARouter::", "register failed, class name: " + str + " should implements one of IRouteRoot/IProviderGroup/IInterceptorGroup.");
            }
        } catch (Exception e) {
            ARouter.c.s0("ARouter::", "register class error:" + str, e);
        }
    }

    public static void h(IInterceptorGroup iInterceptorGroup) {
        f();
        if (iInterceptorGroup != null) {
            iInterceptorGroup.loadInto(Warehouse.interceptorsIndex);
        }
    }

    public static void i(IProviderGroup iProviderGroup) {
        f();
        if (iProviderGroup != null) {
            iProviderGroup.loadInto(Warehouse.providersIndex);
        }
    }

    public static void j(IRouteRoot iRouteRoot) {
        f();
        if (iRouteRoot != null) {
            iRouteRoot.loadInto(Warehouse.groupsIndex);
        }
    }

    public static void k(Postcard postcard, Integer num, String str, String str2) {
        if (TextUtils.c(str) || TextUtils.c(str2)) {
            return;
        }
        try {
            if (num == null) {
                postcard.V(str, str2);
            } else if (num.intValue() == TypeKind.BOOLEAN.ordinal()) {
                postcard.K(str, Boolean.parseBoolean(str2));
            } else if (num.intValue() == TypeKind.BYTE.ordinal()) {
                postcard.M(str, Byte.parseByte(str2));
            } else if (num.intValue() == TypeKind.SHORT.ordinal()) {
                postcard.U(str, Short.parseShort(str2));
            } else if (num.intValue() == TypeKind.INT.ordinal()) {
                postcard.Q(str, Integer.parseInt(str2));
            } else if (num.intValue() == TypeKind.LONG.ordinal()) {
                postcard.S(str, Long.parseLong(str2));
            } else if (num.intValue() == TypeKind.FLOAT.ordinal()) {
                postcard.P(str, Float.parseFloat(str2));
            } else if (num.intValue() == TypeKind.DOUBLE.ordinal()) {
                postcard.N(str, Double.parseDouble(str2));
            } else if (num.intValue() == TypeKind.STRING.ordinal()) {
                postcard.V(str, str2);
            } else if (num.intValue() != TypeKind.PARCELABLE.ordinal()) {
                if (num.intValue() == TypeKind.OBJECT.ordinal()) {
                    postcard.V(str, str2);
                } else {
                    postcard.V(str, str2);
                }
            }
        } catch (Throwable th) {
            ARouter.c.u0("ARouter::", "LogisticsCenter setValue failed! " + th.getMessage());
        }
    }
}
