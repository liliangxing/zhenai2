package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IProviderGroup;
import com.zhenai.android.framework.provider.ActivityCallbackProvider;
import com.zhenai.android.framework.provider.ActivityProvider;
import com.zhenai.android.framework.provider.ActivityStartProvider;
import com.zhenai.android.framework.provider.AppCommonProvider;
import com.zhenai.android.framework.provider.MainProvider;
import com.zhenai.android.framework.router.RouterProvider;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ARouter$$Providers$$main implements IProviderGroup {
    @Override // com.alibaba.android.arouter.facade.template.IProviderGroup
    public void loadInto(Map<String, RouteMeta> map) {
        RouteType routeType = RouteType.PROVIDER;
        map.put("com.zhenai.common.iprovider.IActivityCallbackProvider", RouteMeta.a(routeType, ActivityCallbackProvider.class, "/main/provider/ActivityCallbackProvider", "main", null, -1, Integer.MIN_VALUE));
        map.put("com.zhenai.business.framework.listener.activity.IActivityProvider", RouteMeta.a(routeType, ActivityProvider.class, "/main/provider/ActivityProvider", "main", null, -1, Integer.MIN_VALUE));
        map.put("com.zhenai.business.provider.IActivityStartProvider", RouteMeta.a(routeType, ActivityStartProvider.class, "/main/provider/ActivityStartProvider", "main", null, -1, Integer.MIN_VALUE));
        map.put("com.zhenai.common.iprovider.ICommonProvider", RouteMeta.a(routeType, AppCommonProvider.class, "/main/provider/CommonProvider", "main", null, -1, Integer.MIN_VALUE));
        map.put("com.zhenai.business.main.provider.IMainProvider", RouteMeta.a(routeType, MainProvider.class, "/main/provider/MainProvider", "main", null, -1, Integer.MIN_VALUE));
        map.put("com.zhenai.business.router.za.IRouterProvider", RouteMeta.a(routeType, RouterProvider.class, "/main/provider/RouterProvider", "main", null, -1, Integer.MIN_VALUE));
    }
}
