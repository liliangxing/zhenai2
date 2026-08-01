package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IProviderGroup;
import com.tencent.cloud.face.TencentCloudFaceProvider;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ARouter$$Providers$$lib_senseid_motion implements IProviderGroup {
    @Override // com.alibaba.android.arouter.facade.template.IProviderGroup
    public void loadInto(Map<String, RouteMeta> map) {
        map.put("com.tencent.cloud.face.TencentCloudFaceProvider", RouteMeta.a(RouteType.PROVIDER, TencentCloudFaceProvider.class, "/lib_senseid_motion/TencentCloudFaceRouter", "lib_senseid_motion", null, -1, Integer.MIN_VALUE));
    }
}
