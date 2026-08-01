package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IProviderGroup;
import com.zhenai.pay.provider.PayProvider;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ARouter$$Providers$$module_pay implements IProviderGroup {
    @Override // com.alibaba.android.arouter.facade.template.IProviderGroup
    public void loadInto(Map<String, RouteMeta> map) {
        map.put("com.zhenai.business.pay.IPayProvider", RouteMeta.a(RouteType.PROVIDER, PayProvider.class, "/module_pay/provider/PayProvider", "module_pay", null, -1, Integer.MIN_VALUE));
    }
}
