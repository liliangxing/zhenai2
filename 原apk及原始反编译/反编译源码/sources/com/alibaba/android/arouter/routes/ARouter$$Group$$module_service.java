package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IRouteGroup;
import com.zhenai.service.provider.ServiceProvider;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ARouter$$Group$$module_service implements IRouteGroup {
    @Override // com.alibaba.android.arouter.facade.template.IRouteGroup
    public void loadInto(Map<String, RouteMeta> map) {
        map.put("/module_service/provider/ServiceProvider", RouteMeta.a(RouteType.PROVIDER, ServiceProvider.class, "/module_service/provider/serviceprovider", "module_service", null, -1, Integer.MIN_VALUE));
    }
}
