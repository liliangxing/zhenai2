package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IProviderGroup;
import com.zhenai.short_video.provider.ShortVideoProvider;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ARouter$$Providers$$module_short_video implements IProviderGroup {
    @Override // com.alibaba.android.arouter.facade.template.IProviderGroup
    public void loadInto(Map<String, RouteMeta> map) {
        map.put("com.zhenai.business.short_video.provider.IShortVideoProvider", RouteMeta.a(RouteType.PROVIDER, ShortVideoProvider.class, "/module_short_video/provider/ShortVideoProvider", "module_short_video", null, -1, Integer.MIN_VALUE));
    }
}
