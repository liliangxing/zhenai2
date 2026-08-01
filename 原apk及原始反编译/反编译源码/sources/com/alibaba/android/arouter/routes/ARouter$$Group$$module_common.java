package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IRouteGroup;
import com.zhenai.common.web.h5.BaseHtmlActivity;
import com.zhenai.common.web.h5.BaseHtmlActivityV8;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ARouter$$Group$$module_common implements IRouteGroup {
    @Override // com.alibaba.android.arouter.facade.template.IRouteGroup
    public void loadInto(Map<String, RouteMeta> map) {
        RouteType routeType = RouteType.ACTIVITY;
        map.put("/module_common/web/BaseHtmlActivity", RouteMeta.a(routeType, BaseHtmlActivity.class, "/module_common/web/basehtmlactivity", "module_common", null, -1, Integer.MIN_VALUE));
        map.put("/module_common/web/BaseHtmlActivityV8", RouteMeta.a(routeType, BaseHtmlActivityV8.class, "/module_common/web/basehtmlactivityv8", "module_common", null, -1, Integer.MIN_VALUE));
    }
}
