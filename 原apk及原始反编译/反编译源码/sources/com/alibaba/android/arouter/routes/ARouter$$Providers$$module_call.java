package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IProviderGroup;
import com.zhenai.call.provider.CallProvider;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ARouter$$Providers$$module_call implements IProviderGroup {
    @Override // com.alibaba.android.arouter.facade.template.IProviderGroup
    public void loadInto(Map<String, RouteMeta> map) {
        map.put("com.zhenai.business.business.call.ICallProvider", RouteMeta.a(RouteType.PROVIDER, CallProvider.class, "/module_call/provider/CallProvider", "module_call", null, -1, Integer.MIN_VALUE));
    }
}
