package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IRouteGroup;
import com.zhenai.android.framework.provider.ActivityCallbackProvider;
import com.zhenai.android.framework.provider.ActivityProvider;
import com.zhenai.android.framework.provider.ActivityStartProvider;
import com.zhenai.android.framework.provider.AppCommonProvider;
import com.zhenai.android.framework.provider.MainProvider;
import com.zhenai.android.framework.router.RouterProvider;
import com.zhenai.android.ui.main.MainActivity;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ARouter$$Group$$main implements IRouteGroup {
    @Override // com.alibaba.android.arouter.facade.template.IRouteGroup
    public void loadInto(Map<String, RouteMeta> map) {
        map.put("/main/main/MainActivity", RouteMeta.a(RouteType.ACTIVITY, MainActivity.class, "/main/main/mainactivity", "main", null, -1, Integer.MIN_VALUE));
        RouteType routeType = RouteType.PROVIDER;
        map.put("/main/provider/ActivityCallbackProvider", RouteMeta.a(routeType, ActivityCallbackProvider.class, "/main/provider/activitycallbackprovider", "main", null, -1, Integer.MIN_VALUE));
        map.put("/main/provider/ActivityProvider", RouteMeta.a(routeType, ActivityProvider.class, "/main/provider/activityprovider", "main", null, -1, Integer.MIN_VALUE));
        map.put("/main/provider/ActivityStartProvider", RouteMeta.a(routeType, ActivityStartProvider.class, "/main/provider/activitystartprovider", "main", null, -1, Integer.MIN_VALUE));
        map.put("/main/provider/CommonProvider", RouteMeta.a(routeType, AppCommonProvider.class, "/main/provider/commonprovider", "main", null, -1, Integer.MIN_VALUE));
        map.put("/main/provider/MainProvider", RouteMeta.a(routeType, MainProvider.class, "/main/provider/mainprovider", "main", null, -1, Integer.MIN_VALUE));
        map.put("/main/provider/RouterProvider", RouteMeta.a(routeType, RouterProvider.class, "/main/provider/routerprovider", "main", null, -1, Integer.MIN_VALUE));
    }
}
