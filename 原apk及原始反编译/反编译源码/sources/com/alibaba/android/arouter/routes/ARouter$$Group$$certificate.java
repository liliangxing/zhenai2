package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IRouteGroup;
import com.zhenai.certification.senseid.UploadIDCardActivity;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ARouter$$Group$$certificate implements IRouteGroup {
    @Override // com.alibaba.android.arouter.facade.template.IRouteGroup
    public void loadInto(Map<String, RouteMeta> map) {
        map.put("/certificate/UploadIDCardActivity", RouteMeta.a(RouteType.ACTIVITY, UploadIDCardActivity.class, "/certificate/uploadidcardactivity", "certificate", null, -1, Integer.MIN_VALUE));
    }
}
