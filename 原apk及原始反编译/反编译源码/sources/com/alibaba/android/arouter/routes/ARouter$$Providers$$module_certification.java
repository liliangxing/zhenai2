package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IProviderGroup;
import com.zhenai.certification.provider.CertificationProvider;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ARouter$$Providers$$module_certification implements IProviderGroup {
    @Override // com.alibaba.android.arouter.facade.template.IProviderGroup
    public void loadInto(Map<String, RouteMeta> map) {
        map.put("com.zhenai.business.certificate.provider.ICertificationProvider", RouteMeta.a(RouteType.PROVIDER, CertificationProvider.class, "/module_certification/certification/CertificationProvider", "module_certification", null, -1, Integer.MIN_VALUE));
    }
}
