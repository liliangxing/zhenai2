package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IRouteGroup;
import com.zhenai.moments.confessionwall.ui.aty.ConfessionListActivity;
import com.zhenai.moments.hot.html.HongNiangKeFuHtmlActivity;
import com.zhenai.moments.media.view.activity.MomentsMediaPreviewActivity;
import com.zhenai.moments.personal.PersonalNewMomentsActivity;
import com.zhenai.moments.provider.MemoryPublishProvider;
import com.zhenai.moments.provider.MomentProvider;
import com.zhenai.moments.publish.PublishActivityV2;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ARouter$$Group$$module_moments implements IRouteGroup {
    @Override // com.alibaba.android.arouter.facade.template.IRouteGroup
    public void loadInto(Map<String, RouteMeta> map) {
        RouteType routeType = RouteType.ACTIVITY;
        map.put("/module_moments/ad/HongNiangKeFuHtmlActivity", RouteMeta.a(routeType, HongNiangKeFuHtmlActivity.class, "/module_moments/ad/hongniangkefuhtmlactivity", "module_moments", null, -1, Integer.MIN_VALUE));
        map.put("/module_moments/confession/ConfessionListActivity", RouteMeta.a(routeType, ConfessionListActivity.class, "/module_moments/confession/confessionlistactivity", "module_moments", null, -1, Integer.MIN_VALUE));
        map.put("/module_moments/moments/PersonalNewMomentsActivity", RouteMeta.a(routeType, PersonalNewMomentsActivity.class, "/module_moments/moments/personalnewmomentsactivity", "module_moments", null, -1, Integer.MIN_VALUE));
        map.put("/module_moments/photo/MomentsMediaPreviewActivity", RouteMeta.a(routeType, MomentsMediaPreviewActivity.class, "/module_moments/photo/momentsmediapreviewactivity", "module_moments", null, -1, Integer.MIN_VALUE));
        RouteType routeType2 = RouteType.PROVIDER;
        map.put("/module_moments/provider/MemoryPublishProvider", RouteMeta.a(routeType2, MemoryPublishProvider.class, "/module_moments/provider/memorypublishprovider", "module_moments", null, -1, Integer.MIN_VALUE));
        map.put("/module_moments/provider/MomentProvider", RouteMeta.a(routeType2, MomentProvider.class, "/module_moments/provider/momentprovider", "module_moments", null, -1, Integer.MIN_VALUE));
        map.put("/module_moments/publish/PublishActivityV2", RouteMeta.a(routeType, PublishActivityV2.class, "/module_moments/publish/publishactivityv2", "module_moments", null, -1, Integer.MIN_VALUE));
    }
}
