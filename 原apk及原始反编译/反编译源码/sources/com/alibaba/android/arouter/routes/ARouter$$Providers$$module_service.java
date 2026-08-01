package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IProviderGroup;
import com.zhenai.service.provider.ServiceProvider;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ARouter$$Providers$$module_service implements IProviderGroup {
    @Override // com.alibaba.android.arouter.facade.template.IProviderGroup
    public void loadInto(Map<String, RouteMeta> map) {
        map.put("com.zhenai.business.business.service.IServiceProvider", RouteMeta.a(RouteType.PROVIDER, ServiceProvider.class, "/module_service/provider/ServiceProvider", "module_service", null, -1, Integer.MIN_VALUE));
    }
}
