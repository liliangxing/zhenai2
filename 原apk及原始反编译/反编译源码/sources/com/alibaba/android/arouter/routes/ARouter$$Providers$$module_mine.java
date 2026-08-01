package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IProviderGroup;
import com.zhenai.mine.provider.MineProvider;
import com.zhenai.mine.setting.update_app.provider.UpgradeAppProvider;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ARouter$$Providers$$module_mine implements IProviderGroup {
    @Override // com.alibaba.android.arouter.facade.template.IProviderGroup
    public void loadInto(Map<String, RouteMeta> map) {
        RouteType routeType = RouteType.PROVIDER;
        map.put("com.zhenai.business.mine.provider.IMineProvider", RouteMeta.a(routeType, MineProvider.class, "/module_mine/provider/MineProvider", "module_mine", null, -1, Integer.MIN_VALUE));
        map.put("com.zhenai.business.upgrade.provider.IUpgradeAppProvider", RouteMeta.a(routeType, UpgradeAppProvider.class, "/module_mine/provider/UpgradeAppProvider", "module_mine", null, -1, Integer.MIN_VALUE));
    }
}
