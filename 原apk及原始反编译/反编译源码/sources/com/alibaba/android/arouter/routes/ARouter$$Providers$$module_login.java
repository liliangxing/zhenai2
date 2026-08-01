package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IProviderGroup;
import com.zhenai.login.provider.LoginProvider;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ARouter$$Providers$$module_login implements IProviderGroup {
    @Override // com.alibaba.android.arouter.facade.template.IProviderGroup
    public void loadInto(Map<String, RouteMeta> map) {
        map.put("com.zhenai.common.iprovider.ILoginProvider", RouteMeta.a(RouteType.PROVIDER, LoginProvider.class, "/module_login/provider/LoginProvider", "module_login", null, -1, Integer.MIN_VALUE));
    }
}
