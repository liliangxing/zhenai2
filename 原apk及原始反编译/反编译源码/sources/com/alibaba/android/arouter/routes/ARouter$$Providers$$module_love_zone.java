package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IProviderGroup;
import com.zhenai.love_zone.LoveZoneProvider;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ARouter$$Providers$$module_love_zone implements IProviderGroup {
    @Override // com.alibaba.android.arouter.facade.template.IProviderGroup
    public void loadInto(Map<String, RouteMeta> map) {
        map.put("com.zhenai.business.love_zone.provider.ILoveZoneProvider", RouteMeta.a(RouteType.PROVIDER, LoveZoneProvider.class, "/module_love_zone/provider/LoveZoneProvider", "module_love_zone", null, -1, Integer.MIN_VALUE));
    }
}
