package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.template.IRouteGroup;
import com.alibaba.android.arouter.facade.template.IRouteRoot;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ARouter$$Root$$module_message implements IRouteRoot {
    @Override // com.alibaba.android.arouter.facade.template.IRouteRoot
    public void loadInto(Map<String, Class<? extends IRouteGroup>> map) {
        map.put("module_message", ARouter$$Group$$module_message.class);
    }
}
