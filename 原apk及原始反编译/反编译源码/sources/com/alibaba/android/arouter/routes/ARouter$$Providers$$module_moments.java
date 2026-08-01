package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IProviderGroup;
import com.zhenai.moments.provider.MemoryPublishProvider;
import com.zhenai.moments.provider.MomentProvider;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ARouter$$Providers$$module_moments implements IProviderGroup {
    @Override // com.alibaba.android.arouter.facade.template.IProviderGroup
    public void loadInto(Map<String, RouteMeta> map) {
        RouteType routeType = RouteType.PROVIDER;
        map.put("com.zhenai.business.provider.IMemoryPublishProvider", RouteMeta.a(routeType, MemoryPublishProvider.class, "/module_moments/provider/MemoryPublishProvider", "module_moments", null, -1, Integer.MIN_VALUE));
        map.put("com.zhenai.business.moments.provider.IMomentProvider", RouteMeta.a(routeType, MomentProvider.class, "/module_moments/provider/MomentProvider", "module_moments", null, -1, Integer.MIN_VALUE));
    }
}
