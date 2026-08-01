package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IRouteGroup;
import com.zhenai.pay.view.PurePayEmptyActivity;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ARouter$$Group$$module_paypay implements IRouteGroup {
    @Override // com.alibaba.android.arouter.facade.template.IRouteGroup
    public void loadInto(Map<String, RouteMeta> map) {
        map.put("/module_paypay/PurePayEmptyActivity", RouteMeta.a(RouteType.ACTIVITY, PurePayEmptyActivity.class, "/module_paypay/purepayemptyactivity", "module_paypay", null, -1, Integer.MIN_VALUE));
    }
}
