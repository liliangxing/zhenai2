package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IRouteGroup;
import com.zhenai.search.v8ui.SearchActivityV8;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ARouter$$Group$$module_recommend implements IRouteGroup {
    @Override // com.alibaba.android.arouter.facade.template.IRouteGroup
    public void loadInto(Map<String, RouteMeta> map) {
        map.put("/module_recommend/search/SearchActivityV8", RouteMeta.a(RouteType.ACTIVITY, SearchActivityV8.class, "/module_recommend/search/searchactivityv8", "module_recommend", null, -1, Integer.MIN_VALUE));
    }
}
