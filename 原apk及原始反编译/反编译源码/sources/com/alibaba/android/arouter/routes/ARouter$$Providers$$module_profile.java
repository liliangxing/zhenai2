package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IProviderGroup;
import com.zhenai.profile.ads.AdsProvider;
import com.zhenai.profile.provider.ProfileProvider;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ARouter$$Providers$$module_profile implements IProviderGroup {
    @Override // com.alibaba.android.arouter.facade.template.IProviderGroup
    public void loadInto(Map<String, RouteMeta> map) {
        RouteType routeType = RouteType.PROVIDER;
        map.put("com.zhenai.business.recommend.ads.IAdsProvider", RouteMeta.a(routeType, AdsProvider.class, "/module_profile/provider/AdsProvider", "module_profile", null, -1, Integer.MIN_VALUE));
        map.put("com.zhenai.business.profile.IProfileProvider", RouteMeta.a(routeType, ProfileProvider.class, "/module_profile/provider/ProfileProvider", "module_profile", null, -1, Integer.MIN_VALUE));
    }
}
