package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IRouteGroup;
import com.tencent.cloud.face.TencentCloudFaceProvider;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ARouter$$Group$$lib_senseid_motion implements IRouteGroup {
    @Override // com.alibaba.android.arouter.facade.template.IRouteGroup
    public void loadInto(Map<String, RouteMeta> map) {
        map.put("/lib_senseid_motion/TencentCloudFaceRouter", RouteMeta.a(RouteType.PROVIDER, TencentCloudFaceProvider.class, "/lib_senseid_motion/tencentcloudfacerouter", "lib_senseid_motion", null, -1, Integer.MIN_VALUE));
    }
}
