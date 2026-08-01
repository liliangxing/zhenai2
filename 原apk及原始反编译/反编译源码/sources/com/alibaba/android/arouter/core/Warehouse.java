package com.alibaba.android.arouter.core;

import com.alibaba.android.arouter.base.UniqueKeyTreeMap;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.alibaba.android.arouter.facade.template.IProvider;
import com.alibaba.android.arouter.facade.template.IRouteGroup;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class Warehouse {
    public static Map<String, Class<? extends IRouteGroup>> groupsIndex = new HashMap();
    public static Map<String, RouteMeta> routes = new HashMap();
    public static Map<Class, IProvider> providers = new HashMap();
    public static Map<String, RouteMeta> providersIndex = new HashMap();
    public static Map<Integer, Class<? extends IInterceptor>> interceptorsIndex = new UniqueKeyTreeMap("More than one interceptors use same priority [%s]");
    public static List<IInterceptor> interceptors = new ArrayList();

    public static void clear() {
        routes.clear();
        groupsIndex.clear();
        providers.clear();
        providersIndex.clear();
        interceptors.clear();
        interceptorsIndex.clear();
    }
}
