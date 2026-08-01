package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IProviderGroup;
import com.zhenai.business.account.provider.AccountProvider;
import com.zhenai.business.im.provider.IMProvider;
import com.zhenai.business.media.provider.MediaProvider;
import com.zhenai.business.profile.cache.MyBasicProfileCacheProvider;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ARouter$$Providers$$business implements IProviderGroup {
    @Override // com.alibaba.android.arouter.facade.template.IProviderGroup
    public void loadInto(Map<String, RouteMeta> map) {
        RouteType routeType = RouteType.PROVIDER;
        map.put("com.zhenai.common.iprovider.account.IAccountProvider", RouteMeta.a(routeType, AccountProvider.class, "/business/provider/AccountProvider", "business", null, -1, Integer.MIN_VALUE));
        map.put("com.zhenai.common.iprovider.IIMProvider", RouteMeta.a(routeType, IMProvider.class, "/business/provider/IMProvider", "business", null, -1, Integer.MIN_VALUE));
        map.put("com.zhenai.business.media.IMediaProvider", RouteMeta.a(routeType, MediaProvider.class, "/business/provider/MediaProvider", "business", null, -1, Integer.MIN_VALUE));
        map.put("com.zhenai.common.iprovider.profile.IMyBasicProfileCacheProvider", RouteMeta.a(routeType, MyBasicProfileCacheProvider.class, "/business/provider/MyBasicProfileCacheProvider", "business", null, -1, Integer.MIN_VALUE));
    }
}
