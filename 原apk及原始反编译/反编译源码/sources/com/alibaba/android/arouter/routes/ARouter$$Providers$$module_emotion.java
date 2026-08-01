package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IProviderGroup;
import com.zhenai.module_emotion.provider.EmotionProvider;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ARouter$$Providers$$module_emotion implements IProviderGroup {
    @Override // com.alibaba.android.arouter.facade.template.IProviderGroup
    public void loadInto(Map<String, RouteMeta> map) {
        map.put("com.zhenai.business.emotion.provider.IEmotionProvider", RouteMeta.a(RouteType.PROVIDER, EmotionProvider.class, "/module_emotion/provider/EmotionProvider", "module_emotion", null, -1, Integer.MIN_VALUE));
    }
}
